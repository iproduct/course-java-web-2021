package invoicing.service.impl;

import invoicing.dao.SupplierRepository;
import invoicing.entity.Supplier;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository contragentRepo;

    @Override
    public Collection<Supplier> getAllSuppliers() {
        return contragentRepo.findAll();
    }

    @Override
    public Supplier getSupplierById(String id) {
        return contragentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Supplier with ID='%d' not found", id)));
    }

    @Override
    public Supplier addSupplier(Supplier supplier) {
        supplier.setId(null);
        Date now = new Date();
//        supplier.setCreated(now);
//        supplier.setModified(now);
        return contragentRepo.insert(supplier);
    }

    @Override
    public List<Supplier> addSuppliersBatch(List<Supplier> suppliers) {
        return suppliers.stream().map(p -> contragentRepo.insert(p)).collect(Collectors.toList());
    }

    @Override
    public long dropAllSuppliers() {
        long count = getCount();
        contragentRepo.deleteAll();
        return count;
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {
        getSupplierById(supplier.getId());
//        supplier.setModified(new Date());
        return contragentRepo.save(supplier);
    }

    @Override
    public Supplier deleteSupplierById(String id) {
        Supplier old = getSupplierById(id);
        contragentRepo.deleteById(id);
        return old;
    }

    @Override
    public long getCount() {
        return contragentRepo.count();
    }
}
