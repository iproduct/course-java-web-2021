package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityNotFoundException;
import invoicing.exception.EntityCreationException;
import invoicing.model.Product;
import invoicing.model.Unit;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductRepositoryJdbcImpl implements ProductRepository {
    static final String TABLE_NAME = "products";
    private static final Logger LOG = Logger.getLogger("i.d.i.RepositoryJdbcImpl");
    private Connection connection;

    public ProductRepositoryJdbcImpl() {
    }

    public void init(Properties properties) throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("driver"));
        System.out.println("PostgreSQL DB driver loaded successfully.");
        connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        Statement statement = connection.createStatement();
        int numExecutedStatements = statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS `products` ( " +
                        "`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "`code` CHAR(5) NOT NULL UNIQUE, " +
                        "`name` VARCHAR(50) NOT NULL, " +
                        "`description` VARCHAR(512), " +
                        "`price` DECIMAL(8,2) NOT NULL, " +
                        "`is_promoted` TINYINT(1) DEFAULT 0, " +
                        "`promotion_percentage` DECIMAL(5,2), " +
                        "`unit` TINYINT DEFAULT 0 );"
        );
        // PostgreSQL
//                "CREATE TABLE IF NOT EXISTS products (" +
//                " id SERIAL PRIMARY KEY," +
//                " code varchar(10) NOT NULL," +
//                " name varchar(45) NOT NULL," +
//                " description varchar(450) NOT NULL," +
//                " price real," +
//                " unit integer NOT NULL DEFAULT '0'" +
////                " PRIMARY KEY (username)\n" +
//                ")");
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(parseProduct(rs));
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "findAll: can not execute prepared statement.", e);
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Product p = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ? ;");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = parseProduct(rs);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "findById: can not execute prepared statement.", e);
        }
        return Optional.ofNullable(p);
    }

    @Override
    public Product create(Product p) {
        try {
            return createInternal(p);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error creating product: " + p.toString(), e);
            return null;
        }
    }

    protected Product createInternal(Product p) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO " + TABLE_NAME +
                        " (code, name, description, price, unit)" +
                        " VALUES (?, ?, ?, ?, ?); ", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, p.getCode());
        ps.setString(2, p.getName());
        ps.setString(3, p.getDescription());
        ps.setDouble(4, p.getPrice());
        ps.setInt(5, p.getUnit().ordinal());
        int numInserted = ps.executeUpdate();
        if (numInserted > 0) {
            ResultSet keys = ps.getGeneratedKeys();
            try {
                keys.next();
                p.setId(keys.getLong(1));
            } catch (Exception e) {
                LOG.log(Level.WARNING,
                        "Error extracting auto-generated key when creating product '" + p.getName() + "'", e);
            }
            LOG.info(String.format(
                    "New product %d: %s added successfully.", p.getId(), p.getName()));
        }
        return p;
    }

    @Override
    public List<Product> createBatch(Collection<Product> entities) throws EntityAlreadyExistsException {
        List<Product> results = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            for (Product p : entities) {
                results.add(createInternal(p));
            }
            connection.commit();
            return results;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error batch creating entities in a transaction: ", e);
            try {
                connection.rollback();
            } catch (SQLException e2) {
                LOG.log(Level.SEVERE, "Error during transaction rollback: ", e2);
            }
            return Collections.emptyList();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error batch creating entities in a transaction: ", e);
            }
        }
    }

//    @Override
//    public List<Product> createBatch(Collection<Product> entities) throws EntityAlreadyExistsException {
//        List<Product> results = new ArrayList<>();
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.addBatch("INSERT ...");
//            stmt.addBatch("INSERT ...");
//            stmt.addBatch("INSERT ...");
//            stmt.executeBatch();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return results;
//    }

    @Override
    public Product update(Product p) {
        if (p.getId() == null) { // product Id should be present
            return null;
        }
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE " + TABLE_NAME + " SET code = ?, name = ?, description = ?, price = ?, unit = ? " +
                            " WHERE id = ?;");
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getUnit().ordinal());
            ps.setLong(6, p.getId());
            int numUpdated = ps.executeUpdate();
            if (numUpdated > 0) {
                LOG.info(String.format(
                        "Product %d: %s updated successfully.", p.getId(), p.getName()));
                return p;
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error updating product: " + p.toString(), e);

        }
        return null;
    }

    @Override
    public Product deleteById(Long id) throws EntityNotFoundException {
        Optional<Product> p = findById(id);
        if (p.isEmpty()) {
            throw new EntityNotFoundException(String.format("Entity with ID='%s' does not exist.", id));
        }
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ? ;");
            ps.setLong(1, id);
            int numExecutedStatements = ps.executeUpdate();
            if (numExecutedStatements == 0) {
                throw new EntityNotFoundException(String.format("Error deleting entity with ID='%s'.", id));
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "deleteById: can not execute prepared statement.", e);
            throw new EntityCreationException(String.format("Error deleting entity with ID='%s': ", id, e.getMessage()));
        }
        return p.get();
    }

    @Override
    public long count() {
        Product p = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM " + TABLE_NAME + ";");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "count: can not execute prepared statement.", e);
        }
        return -1;
    }

    @Override
    public void drop() {
        try {
            Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,  ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            ResultSet rs = stmt.executeQuery("SELECT * FROM `products`;");
            rs.last();
            System.out.printf("Last record ID: %d%n", rs.getInt("id"));
            rs.beforeFirst();
            while(rs.next()) {
                System.out.printf("%d: %s%n", rs.getLong(1), rs.getString(2));
                rs.deleteRow();
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error deleting all product records", e);
        }

    }

    private Product parseProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong("id"));
        p.setCode(rs.getString("code"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setUnit(Unit.values()[rs.getInt("unit")]);
        return p;
    }
}
