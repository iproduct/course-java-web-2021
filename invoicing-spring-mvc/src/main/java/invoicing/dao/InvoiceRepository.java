package invoicing.dao;

import invoicing.entity.Invoice;
import invoicing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
