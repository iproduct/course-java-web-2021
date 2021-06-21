package invoicing.service;

import invoicing.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ProductService {
    Collection<Product> getAllProducts();
    Page<Product> getAllProductsPaged(Pageable pageable);
    Collection<Product> getProductsByKeywords(Collection<String> keywords);
    Product getProductById(Long id);
    Product addProduct(Product product);
    List<Product> addProductsBatch(List<Product> products);
    long dropAllProducts();
    Product updateProduct(Product product);
    Product deleteProductById(Long id);
    long getCount();
}
