package invoicing.commands;

import invoicing.dao.ProductRepository;
import invoicing.entity.Product;
import invoicing.util.PrintUtil;

import java.util.List;

import static invoicing.Main.PRODUCT_COLUMNS;

public class PrintAllProductsCommand implements Command{
    private ProductRepository productRepo;
    public PrintAllProductsCommand(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public String execute() {
        List<Product> products = productRepo.findAll();
        String productReport = PrintUtil.formatTable(PRODUCT_COLUMNS, productRepo.findAll(), "Products List:");
        System.out.println(productReport);
        return String.format("Printed %d products.", products.size());
    }
}
