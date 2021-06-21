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
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepo;

    @Override
    public Collection<Product> getAllProducts() {
        return productRepo.findAll();
    }
    @Override
    public Page<Product> getAllProductsPaged(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public Collection<Product> getProductsByKeywords(Collection<String> keywords) {
        return productRepo.getProductsByKeywordsInOrderByPrice(keywords);
    }

    @Override
    public Product getProductById(String id) {
        return productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Product with ID='%d' not found", id)));
    }

    @Override
    public Product addProduct(Product product) {
        product.setId(null);
        Date now = new Date();
//        product.setCreated(now);
//        product.setModified(now);
        return productRepo.insert(product);
    }

    @Override
    public List<Product> addProductsBatch(List<Product> products) {
        return products.stream().map(p -> productRepo.insert(p)).collect(Collectors.toList());
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
    public Product deleteProductById(String id) {
        Product old = getProductById(id);
        productRepo.deleteById(id);
        return old;
    }

    @Override
    public long getCount() {
        return productRepo.count();
    }
}
