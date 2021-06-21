package invoicing.service.impl;

import invoicing.dao.ContragentRepository;
import invoicing.entity.Contragent;
import invoicing.entity.Supplier;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private ContragentRepository contragentRepo;

    @Override
    @Transactional(readOnly = true)
    public Collection<Supplier> getAllSuppliers() {
        return contragentRepo.findByType("SUPPLIER", Supplier.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Supplier getSupplierById(Long id) {
        Contragent found = contragentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Supplier with ID='%d' not found", id)));
        if(found instanceof Supplier){
            return (Supplier) found;
        } else {
            throw( new EntityNotFoundException(
                    String.format("Supplier with ID='%d' not found", id)));
        }
    }

    @Override
    public Supplier addSupplier(Supplier supplier) {
        supplier.setId(null);
        Date now = new Date();
//        supplier.setCreated(now);
//        supplier.setModified(now);
        return contragentRepo.save(supplier);
    }

    @Override
    public List<Supplier> addSuppliersBatch(List<Supplier> suppliers) {
        return contragentRepo.saveAll(suppliers);
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
    public Supplier deleteSupplierById(Long id) {
        Supplier old = getSupplierById(id);
        contragentRepo.deleteById(id);
        return old;
    }

    @Override
    public long getCount() {
        return contragentRepo.count();
    }
}
