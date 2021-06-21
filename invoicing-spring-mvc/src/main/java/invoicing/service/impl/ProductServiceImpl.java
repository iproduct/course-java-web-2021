package invoicing.service.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityNotFoundException;
import invoicing.entity.Product;
import invoicing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public Page<Product> getAllProductsPaged(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public Collection<Product> getProductsByKeywords(Collection<String> keywords) {
        return productRepo.getProductsByKeywordsInOrderByPrice(keywords);
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
//        product.setCreated(now);
//        product.setModified(now);
        return productRepo.save(product);
    }

    @Override
    public List<Product> addProductsBatch(List<Product> products) {
        return productRepo.saveAll(products);
    }

    @Override
    public long dropAllProducts() {
        long count = getCount();
        productRepo.deleteAll();
        return count;
    }

    @Override
    public Product updateProduct(Product product) {
        getProductById(product.getId());
//        product.setModified(new Date());
        return productRepo.save(product);
    }

    @Override
    public Product deleteProductById(Long id) {
        Product old = getProductById(id);
        productRepo.deleteById(id);
        return old;
    }

    @Override
    public long getCount() {
        return productRepo.count();
    }
}
