package invoicing.dao.impl;

import invoicing.dao.CustomerRepository;
import invoicing.model.Customer;

public class CustomerRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, Customer>
    implements CustomerRepository {
}
