package invoicing;

import demos.JdbcDemo;
import invoicing.commands.LoadEntitiesCommand;
import invoicing.commands.PrintAllProductsCommand;
import invoicing.commands.SaveEntitiesCommand;
import invoicing.dao.*;
import invoicing.dao.impl.ProductRepositoryJdbcImpl;
import invoicing.dao.impl.ProductRepositoryJpaImpl;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityNotFoundException;
import invoicing.model.*;
import invoicing.util.PrintUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

import static invoicing.util.Alignment.*;

@Slf4j
public class Main {
    public static final List<PrintUtil.ColumnDescriptor> PRODUCT_COLUMNS = List.of(
            new PrintUtil.ColumnDescriptor("id", "ID", 5, RIGHT),
            new PrintUtil.ColumnDescriptor("code", "Code", 5, LEFT),
            new PrintUtil.ColumnDescriptor("name", "Name", 12, LEFT),
            new PrintUtil.ColumnDescriptor("description", "Description", 12, LEFT),
            new PrintUtil.ColumnDescriptor("price", "Price", 8, RIGHT, 2),
            new PrintUtil.ColumnDescriptor("unit", "Unit", 5, CENTER),
            new PrintUtil.ColumnDescriptor("created", "Ctreated", 19, CENTER),
            new PrintUtil.ColumnDescriptor("updated", "Updated", 19, CENTER)
    );

    public void demo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, EntityNotFoundException {
        Product p1 = new Product("BK001", "Thinking in Java",
                "Good introduction to Java ...", 35.99);
        Product p2 = new Product("BK002", "UML Distilled",
                "UML described briefly ...", 25.50);
        Product[] products = {p1, p2,

                new Product("AC017", "Monitor", "AlphaView", 750.99),

                new Product("AC019", "Tablet", "5 colors set", 43.6),

                new Product("SV001", "Mobile Internet", "On-demand mobile internet package",
                        10.99, Unit.GB),
                new Product("DV001", "2 Band Router", "Supports 2.4 GHz and 5.7 GHz bands",
                        45.5),
                new Product("CB001", "Network Cable Cat. 6E", "Gbit Eternet cable UTP",
                        0.72, Unit.M),
        };
        Class<Product> productClass = Product.class;
        System.out.printf("Class name: %s%n", productClass.getSimpleName());
        Method[] methods = productClass.getMethods();
//        for (Method m : methods) {
//            System.out.println(m.getName());
//        }
        Method setNameMethod = productClass.getMethod("setName", String.class);
        setNameMethod.invoke(p1, "New Product 123");
        Field[] fields = productClass.getDeclaredFields();
        System.out.println("\nFields");
//        for (Field f : fields) {
//            System.out.printf("%s : %s%n", f.getName(), f.getType());
//        }
        Field descrField = productClass.getDeclaredField("description");
        descrField.setAccessible(true);
        descrField.set(p1, "This book is a good introduction to Java");
        System.out.printf("Product 1: %s%n", p1);


        // Product repo demo
//        ProductRepository productRepository = new ProductRepositoryMemoryImpl(new LongKeyGenerator());
//        ProductRepository productRepo =
//                (ProductRepository) Repository.createRepository(Long.class, Product.class);
        UserRepository userRepo =
                (UserRepository) Repository.createRepository(Long.class, User.class);
        CustomerRepository customerRepo =
                (CustomerRepository) Repository.createRepository(Long.class, Customer.class);
        SupplierRepository supplierRepo =
                (SupplierRepository) Repository.createRepository(Long.class, Supplier.class);

//        Properties props = new Properties();
//        ProductRepository productRepo = new ProductRepositoryJdbcImpl();
//        try {
//            // load db props from file
//            String propsParh = Main.class.getClassLoader().getResource("db.properties").getPath();
//            props.load(new FileInputStream(propsParh));
//            // initialize product repository
//            ((ProductRepositoryJdbcImpl)productRepo).init(props);
//        } catch (ClassNotFoundException e) {
//            log.error("Can not load DB driver class.", e);
//        } catch (SQLException e) {
//            log.error("Can not open DB connection for URL: " + props.getProperty("url"), e);
//        } catch (FileNotFoundException e) {
//            log.error("Can not load DB properties file.", e);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ProductRepository productRepo = new ProductRepositoryJpaImpl();
        ((ProductRepositoryJpaImpl) productRepo).init();

//        if (productRepo.count() == 0) {
//            List<Product> created = new ArrayList<>();
//            try {
//                created = productRepo.createBatch(Arrays.asList(products));
//            } catch (EntityAlreadyExistsException e) {
//                e.printStackTrace();
//            }
//            String productReport2 = PrintUtil.formatTable(PRODUCT_COLUMNS, created, "Created Products List:");
//            System.out.println(productReport2);
            Arrays.asList(products).stream().forEach(product -> {
                try {
                    productRepo.create(product);
                } catch (EntityAlreadyExistsException e) {
                    log.error("Can not create product: " + product, e);
                }
            });
//        }

        Optional<Product> opt4 = productRepo.findById(4L);
        if (opt4.isPresent()) {
            Product p4 = opt4.get();
            p4.setName("Graphical Tablet");
            p4.setPrice(35.99);
            productRepo.update(p4);
            System.out.println(productRepo.findById(4L));
        } else {
            System.out.printf("No product found with ID=%d.%n", 4L);
        }

        // drop all products
//        productRepo.drop();

        System.out.println(new PrintAllProductsCommand(productRepo).execute());

        List<Product> toBeSorted = productRepo.findAll();
//        toBeSorted.sort(Comparator.comparing(Product::getPrice));
        toBeSorted.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });
        String productReport2 = PrintUtil.formatTable(PRODUCT_COLUMNS, toBeSorted, "Products List - Sorted:");
        System.out.println(productReport2);

        // Testing serialization/deserialization to /from file
//        try {
//            SaveEntitiesCommand saveCommand = new SaveEntitiesCommand(new FileOutputStream("invoicing.db"),
//                    productRepo, userRepo, customerRepo, supplierRepo);
//            System.out.println(saveCommand.execute());
//            productRepo.drop();
//            userRepo.drop();
//            customerRepo.drop();
//            supplierRepo.drop();
//            LoadEntitiesCommand loadCommand = new LoadEntitiesCommand(new FileInputStream("invoicing.db"),
//                    productRepo, userRepo, customerRepo, supplierRepo);
//            System.out.println(loadCommand.execute());
//
//            String productReport = PrintUtil.formatTable(PRODUCT_COLUMNS, productRepo.findAll(), "Products List:");
//            System.out.println(productReport);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, EntityNotFoundException {
        new Main().demo();
    }
}
