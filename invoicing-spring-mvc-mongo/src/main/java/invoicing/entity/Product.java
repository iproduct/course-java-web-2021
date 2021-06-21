package invoicing.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CollectionId;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

import static invoicing.entity.Unit.PCS;
import static javax.persistence.FetchType.EAGER;

@Document
@Cacheable
public class Product {
    @Id
    private Long id;
    @NotNull @Size(min=5, max=5)
    @Pattern(regexp = "^[A-Z]{2}\\d{3}$", message = "the product code should have two capital letters for category and three digits for number - e.g. 'BK005'")
    private String code; // string 5 characters - two letters and three digits
    @NotNull @Size(min=2, max=50)
    private String name; // the name of the 'Product, string 2 to 50 characters long;
    private String description; // (optional) string up to 512 characters long;
    @Min(0)
    private double price; // real number with double precision;
    private Set<String> keywords = new HashSet<>();
    private boolean isPromoted = false; // boolean, true if product is currently in promotion campaign, false by default;
    private double promotionPercentage; // (optional) - real number with double precision, the percentage of promotion price discount;
    private Unit unit = PCS; // enumeration of PCS (pieces), KG (kilograms), M (meters), L (liters), MB (megabytes), GB (gigabytes), PCS by default;

    public Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public Product(String code, String name, String description, double price) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String code, String name, String description, double price, Set<String> keywords) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.keywords = keywords;
    }

    public Product(String code, String name, String description, double price, Unit unit) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
    }

    public Product(String code, String name, String description, double price, boolean isPromoted, double promotionPercentage, Unit unit) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isPromoted = isPromoted;
        this.promotionPercentage = promotionPercentage;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public void setPromoted(boolean promoted) {
        isPromoted = promoted;
    }

    public double getPromotionPercentage() {
        return promotionPercentage;
    }

    public void setPromotionPercentage(double promotionPercentage) {
        this.promotionPercentage = promotionPercentage;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(getId());
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", isPromoted=").append(isPromoted);
        sb.append(", promotionPercentage=").append(promotionPercentage);
        sb.append(", unit=").append(unit);
        sb.append(", promoted=").append(isPromoted());
        sb.append('}');
        return sb.toString();
    }
}
