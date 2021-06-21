package invoicing.service;

import invoicing.entity.Customer;

import java.util.Collection;
import java.util.List;

public interface CustomerService {
    Collection<Customer> getAllCustomers();
    Customer getCustomerById(String id);
    Customer addCustomer(Customer customer);
    List<Customer> addCustomersBatch(List<Customer> customers);
    long dropAllCustomers();
    Customer updateCustomer(Customer customer);
    Customer deleteCustomerById(String id);
    long getCount();
}
