package invoicing.service;

import invoicing.entity.Product;

import java.util.Collection;
import java.util.List;

public interface ProductService {
    Collection<Product> getAllProducts();
    Product getProductById(Long id);
    Product addProduct(Product product);
    List<Product> addProductsBatch(List<Product> products);
    long dropAllProducts();
    Product updateProduct(Product product);
    Product deleteProductById(Long id);
    long getCount();
}
