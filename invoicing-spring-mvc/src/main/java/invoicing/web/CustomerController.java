package invoicing.web;

import invoicing.entity.Customer;
import invoicing.exception.InvalidEntityDataException;
import invoicing.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/customers", produces = APPLICATION_JSON_VALUE)
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Collection<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer created = customerService.addCustomer(customer);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri())
                .body(created);
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public Customer updateCustomer(@PathVariable("id") Long id, @Valid @RequestBody Customer customer) {
        if (!id.equals(customer.getId())) {
            throw new InvalidEntityDataException(
                    String.format("ID in URL:'%s' is different from ID in request body ID:'%s'.",
                            id, customer.getId())
            );
        }
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping(path = "/{id}")
    public Customer deleteCustomer(@PathVariable("id") Long id) {
        return customerService.deleteCustomerById(id);
    }

}
