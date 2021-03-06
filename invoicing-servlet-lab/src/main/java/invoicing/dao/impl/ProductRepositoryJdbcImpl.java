package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityNotFoundException;
import invoicing.exception.PersistenceException;
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

    public ProductRepositoryJdbcImpl(){
    }

    public void init(Properties properties) throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("driver"));
        System.out.println("PostgreSQL DB driver loaded successfully.");
        connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        PreparedStatement ps = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS products (" +
                " id SERIAL PRIMARY KEY," +
                " code varchar(10) NOT NULL," +
                " name varchar(45) NOT NULL," +
                " description varchar(450) NOT NULL," +
                " price real," +
                " unit integer NOT NULL DEFAULT '0'" +
//                " PRIMARY KEY (username)\n" +
                ")");
        int numExecutedStatements = ps.executeUpdate();
        if(numExecutedStatements > 0) {
            LOG.warning("Table 'products' was successfully created.");
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
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
            if(rs.next()){
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
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO " + TABLE_NAME +
                    " (code, name, description, price, unit)" +
                    " VALUES (?, ?, ?, ?, ?); ", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getUnit().ordinal());
            int numExecutedStatements = ps.executeUpdate();
            if(numExecutedStatements > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                try {
                    keys.next();
                    p.setId(keys.getLong(1));
                } catch(Exception e) {
                    LOG.log(Level.WARNING,
                        "Error extracting auto-generated key when creating product '" + p.getName() + "'", e);
                }
                LOG.info(String.format(
                        "New product %d: %s added successfully.", p.getId(), p.getName()));
            }
            return p;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error creating product: " + p.toString(), e);
            return null;
        }
    }

    @Override
    public int createBatch(Collection<Product> entities) throws EntityAlreadyExistsException {
        return 0;
    }

    @Override
    public Product update(Product p) {
        if(p.getId() == null) { // product Id should be present
            return null;
        }
        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE " + TABLE_NAME + " SET code = ?, name = ?, description = ?, price = ?, unit = ? " +
                        " WHERE id = ?;",
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getUnit().ordinal());
            ps.setLong(6, p.getId());
            int numExecutedStatements = ps.executeUpdate();
            if(numExecutedStatements > 0) {
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
        if(p.isEmpty()){
            throw new EntityNotFoundException(String.format("Entity with ID='%s' does not exist.", id));
        }
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ? ;");
            ps.setLong(1, id);
            int numExecutedStatements = ps.executeUpdate();
            if(numExecutedStatements == 0){
                throw new EntityNotFoundException(String.format("Error deleting entity with ID='%s'.", id));
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "deleteById: can not execute prepared statement.", e);
            throw new PersistenceException(String.format("Error deleting entity with ID='%s': ", id, e.getMessage()));
        }
        return p.get();
    }

    @Override
    public long count() {
        Product p = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM " + TABLE_NAME + ";");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "count: can not execute prepared statement.", e);
        }
        return -1;
    }

    @Override
    public void drop() {

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
