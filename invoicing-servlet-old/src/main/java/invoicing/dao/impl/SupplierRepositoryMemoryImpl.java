package invoicing.dao.impl;

import invoicing.dao.CustomerRepository;
import invoicing.dao.KeyGenerator;
import invoicing.dao.SupplierRepository;
import invoicing.model.Customer;
import invoicing.model.Supplier;

public class SupplierRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, Supplier>
    implements SupplierRepository {
    public SupplierRepositoryMemoryImpl() {
    }

    public SupplierRepositoryMemoryImpl(KeyGenerator<Long> keyGenerator) {
        super(keyGenerator);
    }
}
