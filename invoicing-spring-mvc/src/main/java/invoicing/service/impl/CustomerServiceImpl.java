package invoicing.service.impl;

import invoicing.dao.ContragentRepository;
import invoicing.entity.Contragent;
import invoicing.entity.Customer;
import invoicing.entity.Supplier;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private ContragentRepository contragentRepo;

    @Override
    @Transactional(readOnly = true)
    public Collection<Customer> getAllCustomers() {
        return contragentRepo.findByType("CUSTOMER", Customer.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        Contragent found = contragentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Customer with ID='%d' not found", id)));
        if(found instanceof Supplier){
            return (Customer) found;
        } else {
            throw( new EntityNotFoundException(
                    String.format("Customer with ID='%d' not found", id)));
        }    }

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
        return contragentRepo.saveAll(customers);
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
        return contragentRepo.save(customer);
    }

    @Override
    public Customer deleteCustomerById(Long id) {
        Customer old = getCustomerById(id);
        contragentRepo.deleteById(id);
        return old;
    }

    @Override
    public long getCount() {
        return contragentRepo.count();
    }
}
