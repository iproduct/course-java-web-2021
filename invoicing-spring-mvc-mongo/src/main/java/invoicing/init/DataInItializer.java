package invoicing.init;

import invoicing.entity.*;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.service.CustomerService;
import invoicing.service.ProductService;
import invoicing.service.SupplierService;
import invoicing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static invoicing.entity.Role.ADMIN;
import static invoicing.entity.Role.USER;

@Slf4j
@Component
public class DataInItializer implements CommandLineRunner {
    List<Product> SAMPLE_PRODUCTS = List.of(
            new Product("AC017", "Monitor", "AlphaView", 750.99,
                    Set.of("computer", "component", "monitor")),

            new Product("AC019", "Tablet", "5 colors set", 43.6,
                    Set.of("tablet", "accessory", "computer")),

            new Product("SV001", "Mobile Internet", "On-demand mobile internet package",
                    10.99, Unit.GB),
            new Product("DV001", "2 Band Router", "Supports 2.4 GHz and 5.7 GHz bands",
                    45.5),
            new Product("CB001", "Network Cable Cat. 6E", "Gbit Eternet cable UTP",
                    0.72, Unit.M)
    );
    List<Supplier> SAMPLE_SUPPLIERS = List.of(
            new Supplier("Software AD", "Plovdiv 2000", "123456789", "RBBGSF98765432223", "RBBGSF"),
            new Supplier("IPT Ltd.", "Sofia 1000", "123456789", "UNCRGSF12346678954", "UNCRGSF")
    );
    List<Customer> SAMPLE_CUSTOMERS = List.of(
            new Customer("Ivan Petrov", "Plovdiv 2000", "1234567890", "ivan@gmail.com"),
            new Customer("WebFactory Ltd.", "Sofia 1000", "345678789", "BG", "office@webfactory.com"),
            new Customer("ABC Ltd.", "Tzar Samuil 15", "999999999", "BG", "abc@abv.bg")
    );

    @Autowired
    ProductService productService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if(userService.getCount() == 0) {
            log.info("Initializing DB with default users.");
            List.of(
                    new User("Default", "Admin", "admin", "admin123", ADMIN, true),
                    new User("Default", "User", "user", "user1234", USER, true),
                    new User("Ivan", "Petrov", "ivan", "ivan1234")
            ).forEach(userService::addUser);
        }

        if (productService.getCount() == 0) {
//            productRepo.drop();
            log.info("Initializing DB with sample products.");
            try {
                productService.addProductsBatch(SAMPLE_PRODUCTS);
            } catch (EntityAlreadyExistsException e) {
                log.error("Error initializing products", e);
            }
        }
        if (supplierService.getCount() == 0) {
            log.info("Initializing DB with sample suppliers.");
            try {
                supplierService.addSuppliersBatch(SAMPLE_SUPPLIERS);
            } catch (EntityAlreadyExistsException e) {
                log.error("Error initializing contragents", e);
            }
        }

        if (customerService.getCount() == 0) {
            log.info("Initializing DB with sample customers.");
            try {
                customerService.addCustomersBatch(SAMPLE_CUSTOMERS);
            } catch (EntityAlreadyExistsException e) {
                log.error("Error initializing contragents", e);
            }
        }
    }
}
