package invoicing.model;

public class Supplier extends Contragent {
    private String iban; // string 22 characters long, containing only capital letters and digits;
    private String bic; // string 8 characters long, the bank identification code;

    public Supplier(String iban, String bic) {
        this.iban = iban;
        this.bic = bic;
    }

    public Supplier(Long id, String iban, String bic) {
        super(id);
        this.iban = iban;
        this.bic = bic;
    }

    public Supplier(String name, String address, String idNumber, String iban, String bic) {
        super(name, address, idNumber);
        this.iban = iban;
        this.bic = bic;
    }

    public Supplier(String name, String address, String idNumber, boolean vatRegistered, String iban, String bic) {
        super(name, address, idNumber, vatRegistered);
        this.iban = iban;
        this.bic = bic;
    }

    public Supplier(String name, String address, String idNumber, String countryCode, String iban, String bic) {
        super(name, address, idNumber, countryCode);
        this.iban = iban;
        this.bic = bic;
    }

    public Supplier(String name, String address, String idNumber, String countryCode, String phone, boolean corporate, String iban, String bic) {
        super(name, address, idNumber, countryCode, phone, corporate);
        this.iban = iban;
        this.bic = bic;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Supplier{");
        sb.append("id=").append(getId());
        sb.append(", created=").append(getCreated());
        sb.append(", modified=").append(getModified());
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", address='").append(getAddress()).append('\'');
        sb.append(", idNumber='").append(getIdNumber()).append('\'');
        sb.append(", countryCode='").append(getCountryCode()).append('\'');
        sb.append(", phone='").append(getPhone()).append('\'');
        sb.append(", corporate=").append(isCorporate());
        sb.append(", vatRegistered=").append(isVatRegistered());
        sb.append(", iban='").append(iban).append('\'');
        sb.append(", bic='").append(bic).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
