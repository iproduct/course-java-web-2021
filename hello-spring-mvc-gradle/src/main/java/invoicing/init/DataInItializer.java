package invoicing.init;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.model.Product;
import invoicing.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
//@Component
public class DataInItializer implements CommandLineRunner {
    List<Product> SAMPLE_PRODUCTS = List.of(
            new Product("AC017", "Monitor", "AlphaView", 750.99),

            new Product("AC019", "Tablet", "5 colors set", 43.6),

            new Product("SV001", "Mobile Internet", "On-demand mobile internet package",
                    10.99, Unit.GB),
            new Product("DV001", "2 Band Router", "Supports 2.4 GHz and 5.7 GHz bands",
                    45.5),
            new Product("CB001", "Network Cable Cat. 6E", "Gbit Eternet cable UTP",
                    0.72, Unit.M)
    );


    @Autowired
    ProductRepository productRepo;

    @Override
    public void run(String... args) throws Exception {
//        if(productRepo.count() == 0)
        productRepo.drop();
        log.info("Initializing DB with sample products.");
        try {
            productRepo.createBatch(SAMPLE_PRODUCTS);
        } catch (EntityAlreadyExistsException e) {
            log.error("Error initializing products", e);
        }
    }
}
