package invoicing.dao.impl;

import invoicing.dao.KeyGenerator;
import invoicing.dao.ProductRepository;
import invoicing.dao.UserRepository;
import invoicing.model.Product;
import invoicing.model.User;

import java.util.Optional;

public class ProductRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, Product>
    implements ProductRepository {
    public ProductRepositoryMemoryImpl() {
    }

    public ProductRepositoryMemoryImpl(KeyGenerator<Long> keyGenerator) {
        super(keyGenerator);
    }
}
