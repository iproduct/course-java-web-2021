package invoicing.dao.impl;

import invoicing.dao.CustomerRepository;
import invoicing.dao.ProductRepository;
import invoicing.model.Customer;
import invoicing.model.Product;

public class CustomerRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, Customer>
    implements CustomerRepository {
}
