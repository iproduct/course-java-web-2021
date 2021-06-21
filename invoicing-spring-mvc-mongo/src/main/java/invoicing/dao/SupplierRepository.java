package invoicing.dao;

import invoicing.entity.Contragent;
import invoicing.entity.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
}
