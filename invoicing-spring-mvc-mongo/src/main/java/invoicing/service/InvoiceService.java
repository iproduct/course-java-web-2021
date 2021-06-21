package invoicing.service;

import invoicing.entity.Invoice;

import java.util.Collection;
import java.util.List;

public interface InvoiceService {
    Collection<Invoice> getAllInvoices();
    Invoice getInvoiceById(String id);
    Invoice addInvoice(Invoice invoice);
    List<Invoice> addInvoicesBatch(List<Invoice> invoices);
    long dropAllInvoices();
    Invoice updateInvoice(Invoice invoice);
    Invoice deleteInvoiceById(String id);
    long getCount();
}
