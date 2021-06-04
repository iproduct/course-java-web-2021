package invoicing;

import invoicing.commands.LoadEntitiesCommand;
import invoicing.commands.SaveEntitiesCommand;
import invoicing.dao.*;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.model.*;
import invoicing.util.PrintUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static invoicing.util.Alignment.*;

public class Main {
    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Product p1 = new Product("BK001", "Thinking in Java",
                "Good introduction to Java ...", 35.99);
        Product p2 = new Product("BK002", "UML Distilled",
                "UML described briefly ...", 25.50);
        Product[] products = {p1, p2,
                new Product("AC001", "Whiteboard Markers", "5 colors set", 7.8),
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
        descrField.set(p1, "Good intro to Java!!!");
        System.out.printf("Product 1: %s%n", p1);

        // Common entity metadata column descriptors
        List<PrintUtil.ColumnDescriptor> metadataColumns = List.of(
                new PrintUtil.ColumnDescriptor("created", "Ctreated", 19, CENTER),
                new PrintUtil.ColumnDescriptor("updated", "Updated", 19, CENTER)
        );
        // Product repo demo
//        ProductRepository productRepository = new ProductRepositoryMemoryImpl(new LongKeyGenerator());
        ProductRepository productRepo =
                (ProductRepository) Repository.createRepository(Long.class, Product.class);
        UserRepository userRepo =
                (UserRepository) Repository.createRepository(Long.class, User.class);
        CustomerRepository customerRepo =
                (CustomerRepository) Repository.createRepository(Long.class, Customer.class);
        SupplierRepository supplierRepo =
                (SupplierRepository) Repository.createRepository(Long.class, Supplier.class);

        Arrays.asList(products).stream().forEach(product -> {
            try {
                productRepo.create(product);
            } catch (EntityAlreadyExistsException e) {
                e.printStackTrace();
            }
        });

        // Print formatted report as table
        List<PrintUtil.ColumnDescriptor> productColumns = new ArrayList<>(List.of(
                new PrintUtil.ColumnDescriptor("id", "ID", 5, RIGHT),
                new PrintUtil.ColumnDescriptor("code", "Code", 5, LEFT),
                new PrintUtil.ColumnDescriptor("name", "Name", 12, LEFT),
                new PrintUtil.ColumnDescriptor("description", "Description", 12, LEFT),
                new PrintUtil.ColumnDescriptor("price", "Price", 8, RIGHT, 2),
                new PrintUtil.ColumnDescriptor("unit", "Unit", 5, CENTER)
        ));
        productColumns.addAll(metadataColumns);
        String productReport = PrintUtil.formatTable(productColumns, productRepo.findAll(), "Products List:");
        System.out.println(productReport);

        List<Product> toBeSorted =  productRepo.findAll();
//        toBeSorted.sort(Comparator.comparing(Product::getPrice));
        toBeSorted.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });
        String productReport2 = PrintUtil.formatTable(productColumns, toBeSorted, "Products List - Sorted:");
        System.out.println(productReport2);

        // Testing serialization/deserialization to /from file
        try {
            SaveEntitiesCommand saveCommand = new SaveEntitiesCommand(new FileOutputStream("invoicing.db"),
                    productRepo, userRepo, customerRepo, supplierRepo);
            System.out.println(saveCommand.execute());
            productRepo.drop();
            userRepo.drop();
            customerRepo.drop();
            supplierRepo.drop();
            LoadEntitiesCommand loadCommand = new LoadEntitiesCommand(new FileInputStream("invoicing.db"),
                    productRepo, userRepo, customerRepo, supplierRepo);
            System.out.println(loadCommand.execute());

            productReport = PrintUtil.formatTable(productColumns, productRepo.findAll(), "Products List:");
            System.out.println(productReport);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
