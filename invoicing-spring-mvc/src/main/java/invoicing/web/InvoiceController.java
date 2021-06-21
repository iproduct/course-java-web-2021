package invoicing.web;

import invoicing.entity.Invoice;
import invoicing.exception.InvalidEntityDataException;
import invoicing.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/invoices", produces = APPLICATION_JSON_VALUE)
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public Collection<Invoice> getInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable("id") Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice) {
        Invoice created = invoiceService.addInvoice(invoice);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri())
                .body(created);
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public Invoice updateInvoice(@PathVariable("id") Long id, @Valid @RequestBody Invoice invoice) {
        if (!id.equals(invoice.getId())) {
            throw new InvalidEntityDataException(
                    String.format("ID in URL:'%s' is different from ID in request body ID:'%s'.",
                            id, invoice.getId())
            );
        }
        return invoiceService.updateInvoice(invoice);
    }

    @DeleteMapping(path = "/{id}")
    public Invoice deleteInvoice(@PathVariable("id") Long id) {
        return invoiceService.deleteInvoiceById(id);
    }

}
