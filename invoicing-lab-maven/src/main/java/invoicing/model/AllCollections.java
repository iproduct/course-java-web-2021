package invoicing.model;

import invoicing.dao.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class AllCollections implements Serializable {
    private List<Product> products;
    private List<User> users;
    private List<Customer> customers;
    private List<Supplier> suppliers;

    public AllCollections() {
    }

    public AllCollections(List<Product> products, List<User> users, List<Customer> customers, List<Supplier> suppliers) {
        this.products = products;
        this.users = users;
        this.customers = customers;
        this.suppliers = suppliers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}
