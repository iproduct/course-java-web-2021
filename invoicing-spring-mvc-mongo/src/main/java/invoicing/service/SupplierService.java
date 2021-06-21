package invoicing.service;

import invoicing.entity.Supplier;

import java.util.Collection;
import java.util.List;

public interface SupplierService {
    Collection<Supplier> getAllSuppliers();
    Supplier getSupplierById(Long id);
    Supplier addSupplier(Supplier supplier);
    List<Supplier> addSuppliersBatch(List<Supplier> suppliers);
    long dropAllSuppliers();
    Supplier updateSupplier(Supplier supplier);
    Supplier deleteSupplierById(Long id);
    long getCount();
}
