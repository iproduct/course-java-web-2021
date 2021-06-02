package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.dao.UserRepository;
import invoicing.model.Product;
import invoicing.model.User;

import java.util.Optional;

public class ProductRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, Product>
    implements ProductRepository {
}
