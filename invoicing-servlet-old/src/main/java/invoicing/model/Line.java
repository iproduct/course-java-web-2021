package invoicing.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Line extends AbstractEntity {
    @NonNull
    private Invoice invoice; // the Invoice this InvoiceLine belongs to;
    @NonNull
    private Product product; // the Product ordered;
    @NonNull
    private double quantity; // real number, the quantity of the Product purchased;
    private double price = -1; // (optional) real number with double precision, should be taken from Product entity, if missing;

    public double getPrice(){
        return price >= 0 ? price : product.getPrice();
    }

    public double getTotalPrice(){
        return getPrice() * getQuantity();
    }
}
