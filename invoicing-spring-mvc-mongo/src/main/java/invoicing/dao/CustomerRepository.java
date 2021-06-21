package invoicing.dao;

import invoicing.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    <T> Collection<T> findByType(String type, Class<T> typeClass);
}
