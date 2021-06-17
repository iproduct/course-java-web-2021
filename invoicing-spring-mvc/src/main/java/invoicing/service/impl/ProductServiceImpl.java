package invoicing.service.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityNotFoundException;
import invoicing.entity.Product;
import invoicing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepo;

    @Override
    @Transactional(readOnly = true)
    public Collection<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Product with ID='%d' not found", id)));
    }

    @Override
    public Product addProduct(Product product) {
        product.setId(null);
        Date now = new Date();
        product.setCreated(now);
        product.setModified(now);
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
        return productRepo.update(product);
    }

    @Override
    public Product deleteProductById(Long id) {
        return productRepo.deleteById(id);
    }

    @Override
    public long getCount() {
        return productRepo.count();
    }
}
