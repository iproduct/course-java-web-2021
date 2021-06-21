package invoicing.service.impl;

import invoicing.dao.CustomerRepository;
import invoicing.entity.Customer;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository contragentRepo;

    @Override
    public Collection<Customer> getAllCustomers() {
        return contragentRepo.findAll();
    }

    @Override
    public Customer getCustomerById(String id) {
        return contragentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Customer with ID='%d' not found", id)));
    }

    @Override
    public Customer addCustomer(Customer customer) {
        customer.setId(null);
        Date now = new Date();
//        customer.setCreated(now);
//        customer.setModified(now);
        return contragentRepo.save(customer);
    }

    @Override
    public List<Customer> addCustomersBatch(List<Customer> customers) {
        return customers.stream().map(p -> contragentRepo.insert(p)).collect(Collectors.toList());
    }

    @Override
    public long dropAllCustomers() {
        long count = getCount();
        contragentRepo.deleteAll();
        return count;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        getCustomerById(customer.getId());
//        customer.setModified(new Date());
        return contragentRepo.insert(customer);
    }

    @Override
    public Customer deleteCustomerById(String id) {
        Customer old = getCustomerById(id);
        contragentRepo.deleteById(id);
        return old;
    }

    @Override
    public long getCount() {
        return contragentRepo.count();
    }
}
