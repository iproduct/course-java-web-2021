package invoicing.model;

public class Customer extends Contragent {
    private String email; // string between 6 and 80 characters long, a valid e-mail;

    public Customer(String email) {
        this.email = email;
    }

    public Customer(Long id, String email) {
        super(id);
        this.email = email;
    }

    public Customer(String name, String address, String idNumber, String email) {
        super(name, address, idNumber);
        this.email = email;
    }

    public Customer(String name, String address, String idNumber, boolean vatRegistered, String email) {
        super(name, address, idNumber, vatRegistered);
        this.email = email;
    }

    public Customer(String name, String address, String idNumber, String countryCode, String email) {
        super(name, address, idNumber, countryCode);
        this.email = email;
    }

    public Customer(String name, String address, String idNumber, String countryCode, String phone, boolean corporate, String email) {
        super(name, address, idNumber, countryCode, phone, corporate);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
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
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
