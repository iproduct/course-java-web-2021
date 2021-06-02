package invoicing;

import invoicing.dao.ProductRepository;
import invoicing.dao.impl.ProductRepositoryMemoryImpl;
import invoicing.dao.impl.RepositoryMemoryImpl;
import invoicing.model.Product;
import invoicing.util.PrintUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static invoicing.util.Alignment.*;
import static invoicing.util.Alignment.CENTER;

public class Main {
    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Product p1 = new Product("Thinking in Java", 35.5);
        Class<Product> productClass = Product.class;
        System.out.printf("Class name: %s%n", productClass.getSimpleName());
        Method[] methods = productClass.getMethods();
        for(Method m: methods) {
            System.out.println(m.getName());
        }
        Method setNameMethod = productClass.getMethod("setName", String.class);
        setNameMethod.invoke(p1,"New Product 123");
        Field[] fields = productClass.getDeclaredFields();
        System.out.println("\nFields");
        for(Field f: fields) {
            System.out.printf("%s : %s%n", f.getName(), f.getType());
        }
        Field descrField = productClass.getDeclaredField("description");
        descrField.setAccessible(true);
        descrField.set(p1, "Good intro to Java!!!");
        System.out.printf("Product 1: %s%n", p1);
        ProductRepository productRepository = new ProductRepositoryMemoryImpl();

        // Common entity metadata column descriptors
        List<PrintUtil.ColumnDescriptor> metadataColumns = List.of(
                new PrintUtil.ColumnDescriptor("created", "Ctreated", 19, CENTER),
                new PrintUtil.ColumnDescriptor("updated", "Updated", 19, CENTER)
        );

        ProductRepository productRepo = new ProductRepositoryMemoryImpl();

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
    }
}
