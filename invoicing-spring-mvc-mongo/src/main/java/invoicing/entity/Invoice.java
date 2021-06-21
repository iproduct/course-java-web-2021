package invoicing.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.TemporalType.DATE;

@Document
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    private String id;
    @NonNull
    private Supplier supplier; // the Supplier legal entity that is issuing the Invoice;
    @NonNull
    private Customer customer; // the Customer that is receiving the Invoice;
    private Date date = new Date(); // date of Invoice;
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

}
