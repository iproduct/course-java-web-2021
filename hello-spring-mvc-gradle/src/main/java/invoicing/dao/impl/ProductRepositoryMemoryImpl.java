package invoicing.dao.impl;

import invoicing.dao.KeyGenerator;
import invoicing.dao.ProductRepository;
import invoicing.model.Product;

public class ProductRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, Product>
    implements ProductRepository {
    public ProductRepositoryMemoryImpl() {
    }

    public ProductRepositoryMemoryImpl(KeyGenerator<Long> keyGenerator) {
        super(keyGenerator);
    }
}
