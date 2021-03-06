package invoicing.entity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Table(name="contragents")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=STRING, length=10)
@DiscriminatorValue("USER")
public class Contragent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // string 2 to 80 characters long - the name of the physical person or legal entity;
    private String address; // string 5 to 120 characters long;
    private String idNumber; // string 9 to 15 digits, representing SSN for physical persons, EIK number for legal entities;
    private String countryCode; // should be provided only if the Contragent is a legal entity, and is VAT registered in the specific country;
    private String phone; // string 2 to 20 characters long, should contain only digits, white-spaces, '(', ')' and '+';
    private boolean corporate = true; // boolean value;
    @OneToMany(mappedBy = "supplier", targetEntity = Invoice.class)
    private List<Invoice> issuedInvoices = new ArrayList<>();
    @OneToMany(mappedBy = "customer", targetEntity = Invoice.class)
    private List<Invoice> receivedInvoices = new ArrayList<>();
    @Column(name = "type", insertable = false, updatable = false)
    private String type;

    public Contragent() {
    }

    public Contragent(Long id) {
        this.id = id;
    }

    // Physical person constructor
    public Contragent(String name, String address, String idNumber) {
        this.name = name;
        this.address = address;
        this.idNumber = idNumber;
        corporate = false;
    }

    // Physical legal entity
    public Contragent(String name, String address, String idNumber, boolean vatRegistered) {
        this.name = name;
        this.address = address;
        this.idNumber = idNumber;
        if(vatRegistered) {
            countryCode = "BG";
        }
    }

    // Physical legal entity with country code
    public Contragent(String name, String address, String idNumber, String countryCode) {
        this.name = name;
        this.address = address;
        this.idNumber = idNumber;
        this.countryCode = countryCode;
    }

    public Contragent(String name, String address, String idNumber, String countryCode, String phone, boolean corporate) {
        this.name = name;
        this.address = address;
        this.idNumber = idNumber;
        this.countryCode = countryCode;
        this.phone = phone;
        this.corporate = corporate;
    }

    public String getDicriminator() {
        return type;
    }

    public void setDicriminator(String dicriminator) {
        this.type = dicriminator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCorporate() {
        return corporate;
    }

    public void setCorporate(boolean corporate) {
        this.corporate = corporate;
    }

    public boolean isVatRegistered() {
        return countryCode != null && countryCode.trim().length() > 0;
    }

    public List<Invoice> getIssuedInvoices() {
        return issuedInvoices;
    }

    public void setIssuedInvoices(List<Invoice> issuedInvoices) {
        this.issuedInvoices = issuedInvoices;
    }

    public List<Invoice> getReceivedInvoices() {
        return receivedInvoices;
    }

    public void setReceivedInvoices(List<Invoice> receivedInvoices) {
        this.receivedInvoices = receivedInvoices;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contragent{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", countryCode='").append(countryCode).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", corporate=").append(corporate);
        sb.append(", vatRegistered=").append(isVatRegistered());
        sb.append('}');
        return sb.toString();
    }
}
