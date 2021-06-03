package invoicing.model;

import static invoicing.model.Unit.PCS;

public class Product extends AbstractEntity<Long, Product> {
    private String code; // string 5 characters - two letters and three digits
    private String name; // the name of the 'Product, string 2 to 50 characters long;
    private String description; // (optional) string up to 512 characters long;
    private double price; // real number with double precision;
    private boolean isPromoted = false; // boolean, true if product is currently in promotion campaign, false by default;
    private double promotionPercentage; // (optional) - real number with double precision, the percentage of promotion price discount;
    private Unit unit = PCS; // enumeration of PCS (pieces), KG (kilograms), M (meters), L (liters), MB (megabytes), GB (gigabytes), PCS by default;

    public Product() {
    }

    public Product(Long id) {
        super(id);
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
        sb.append(", created=").append(getCreated());
        sb.append(", modified=").append(getModified());
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
