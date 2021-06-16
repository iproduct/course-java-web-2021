package invoicing.dao.impl;

import invoicing.dao.CustomerRepository;
import invoicing.dao.KeyGenerator;
import invoicing.model.Customer;

public class CustomerRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, Customer>
    implements CustomerRepository {
    public CustomerRepositoryMemoryImpl() {
    }

    public CustomerRepositoryMemoryImpl(KeyGenerator<Long> keyGenerator) {
        super(keyGenerator);
    }
}
