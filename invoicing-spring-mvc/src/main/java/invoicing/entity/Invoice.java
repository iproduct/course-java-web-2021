package invoicing.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Supplier.class)
    @NonNull
    private Supplier supplier; // the Supplier legal entity that is issuing the Invoice;
    @ManyToOne(targetEntity = Customer.class)
    @NonNull
    private Customer customer; // the Customer that is receiving the Invoice;
    @Temporal(DATE)
    private Date date = new Date(); // date of Invoice;
    @OneToMany(mappedBy = "invoice", cascade = ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Line> lines = new ArrayList<>(); //  list of all invoice Line positions included in the Invoice, read further for details;
    private double vat = 0.2; //  read only property to be calculated as fixed VAT coefficient from totalWithoutVat;
    private String description = ""; // (optional) - string up to 255 characters long allowing further elaboration about the invoicing details;

    //read only property to be calculated as sum of position costs without VAT
    public double getTotalWithoutVat() {
        return lines.stream().mapToDouble(line -> line.getTotalPrice()).sum();
    }

    // read only property to be calculated as sum of totalWithoutVat and vat properties
    public double getTotal() {
        return getTotalWithoutVat() * getVat();
    }

    public Invoice(@NonNull Supplier supplier, @NonNull Customer customer) {
        addCustomer(customer);
        addSupplier(supplier);
    }

    public Invoice(@NonNull Supplier supplier, @NonNull Customer customer, Date date, List<Line> lines, double vat, String description) {
        this(supplier, customer);
        this.date = date;
        this.lines = lines;
        this.vat = vat;
        this.description = description;
    }

    public void addSupplier(Supplier supplier) {
        setSupplier(supplier);
        if (!supplier.getIssuedInvoices().contains(this)) {
            supplier.getIssuedInvoices().add(this);
        }
    }

    public void addCustomer(Customer customer) {
        setCustomer(customer);
        if (!customer.getReceivedInvoices().contains(this)) {
            customer.getReceivedInvoices().add(this);
        }
    }

    public void removeSupplier() {
        setSupplier(null);
        if (supplier.getIssuedInvoices().contains(this)) {
            supplier.getIssuedInvoices().remove(this);
        }
    }

    public void removeCustomer() {
        setCustomer(null);
        if (supplier.getReceivedInvoices().contains(this)) {
            supplier.getReceivedInvoices().remove(this);
        }
    }

    public void addLine(Line line) {
        getLines().add(line);
        line.setInvoice(this);
    }

    public void removeLine(Line line) {
        getLines().remove(line);
        line.setInvoice(null);
    }

}
