package invoicing.dao.impl;

import invoicing.dao.CustomerRepository;
import invoicing.model.Customer;

public class SupplierRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, Customer>
    implements CustomerRepository {
}
