package invoicing.dao;

import invoicing.entity.Contragent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface ContragentRepository extends MongoRepository<Contragent, Long> {
    <T> Collection<T> findByType(String type, Class<T> typeClass);
}
