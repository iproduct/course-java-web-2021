package invoicing.service.impl;

import invoicing.dao.InvoiceRepository;
import invoicing.entity.Invoice;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepo;

    @Override
    public Collection<Invoice> getAllInvoices() {
        return invoiceRepo.findAll();
    }

    @Override
    public Invoice getInvoiceById(String id) {
        return invoiceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Invoice with ID='%d' not found", id)));
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {
        invoice.setId(null);
        Date now = new Date();
//        invoice.setCreated(now);
//        invoice.setModified(now);
        return invoiceRepo.insert(invoice);
    }

    @Override
    public List<Invoice> addInvoicesBatch(List<Invoice> invoices) {
        return invoiceRepo.saveAll(invoices);
    }

    @Override
    public long dropAllInvoices() {
        long count = getCount();
        invoiceRepo.deleteAll();
        return count;
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        getInvoiceById(invoice.getId());
//        invoice.setModified(new Date());
        return invoiceRepo.save(invoice);
    }

    @Override
    public Invoice deleteInvoiceById(String id) {
        Invoice old = getInvoiceById(id);
        invoiceRepo.deleteById(id);
        return old;
    }

    @Override
    public long getCount() {
        return invoiceRepo.count();
    }
}
