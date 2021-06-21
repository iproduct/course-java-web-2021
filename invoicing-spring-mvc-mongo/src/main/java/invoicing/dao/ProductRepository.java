package invoicing.dao;

import invoicing.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> getProductsByKeywordsInOrderByPrice(Collection<String> keywords);
}
