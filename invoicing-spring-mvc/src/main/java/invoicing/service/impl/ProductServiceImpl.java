package invoicing.service.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityNotFoundException;
import invoicing.entity.Product;
import invoicing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepo;

    @Override
    public Collection<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Product with ID='%d' not found", id)));
    }

    @Override
    public Product addProduct(Product product) {
        return productRepo.create(product);
    }

    @Override
    public List<Product> addProductsBatch(List<Product> products) {
        return productRepo.createBatch(products);
    }

    @Override
    public long dropAllProducts() {
        return productRepo.drop();
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public Product deleteProductById(Long id) {
        return null;
    }

    @Override
    public long getCount() {
        return 0;
    }
}
