package invoicing.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Line  {
    @Id
    private String id;
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
