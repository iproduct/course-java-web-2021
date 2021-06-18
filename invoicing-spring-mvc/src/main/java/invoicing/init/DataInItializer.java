package invoicing.init;

import invoicing.dao.ContragentRepository;
import invoicing.dao.ProductRepository;
import invoicing.entity.*;
import invoicing.exception.EntityAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cache.cfg.spi.DomainDataCachingConfig;
import org.hibernate.cache.internal.EnabledCaching;
import org.hibernate.cache.jcache.internal.JCacheDomainDataRegionImpl;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.cache.Caching;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Set;

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
    List<Contragent> SAMPLE_CONTRAGENTS = List.of(
            new Contragent("Ivan Petrov", "Plovdiv 2000", "1234567890"),
            new Supplier("IPT Ltd.", "Sofia 1000", "123456789", "UNCRGSF12346678954", "UNCRGSF"),
            new Customer("ABC Ltd.", "Tzar Samuil 15", "999999999", "BG", "abc@abv.bg")
    );

    @PersistenceUnit
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    ContragentRepository contragentRepo;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(String... args) throws Exception {
        if (productRepo.count() == 0) {
//            productRepo.drop();
            log.info("Initializing DB with sample products.");
            try {
                productRepo.createBatch(SAMPLE_PRODUCTS);
            } catch (EntityAlreadyExistsException e) {
                log.error("Error initializing products", e);
            }
        }

        log.info("Initializing DB with sample contragents.");
        try {
            contragentRepo.createBatch(SAMPLE_CONTRAGENTS);
        } catch (EntityAlreadyExistsException e) {
            log.error("Error initializing contragents", e);
        }

        template.execute(status -> {
            Product p1 = productRepo.findById(1L).get();
//            em.unwrap(Session.class).evict(p1);
            p1.setName("UPDATED PRODUCT");
            p1.setPrice(1000);
//            Product newP1 = new Product("CD001", "NAME", "Description ...", 100);
//            newP1.setId(1L);
//            newP1.setName("NEW PRODUCT UPDATE");
//            newP1.setPrice(230);
            System.out.println("!!!! About to start UPDATE:");
//            em.unwrap(Session.class).update(p1);
            System.out.println("!!!! About to commit transaction");
            return p1;
        });

        System.out.println("\n!!!!!!!!!  SECOND FIND PRODUCT:");
        Product p2 = productRepo.findById(1L).get();
        sessionFactory.getStatistics().logSummary();
        System.out.println("Second level caches:");
        List<String> secondLevelCaches = List.of(sessionFactory.getStatistics().getSecondLevelCacheRegionNames());
        System.out.println(secondLevelCaches);
        secondLevelCaches.forEach(name -> {
            System.out.printf("%s -> %s%n", name,
                    sessionFactory.getStatistics().getSecondLevelCacheStatistics(name));
//            try {
//                System.out.println("Cached entities:" +
//                        ((JCacheDomainDataRegionImpl)sessionFactory.getCache().unwrap(EnabledCaching.class).toString());
//            } catch(Exception e){}
        });
        System.out.println(Caching.getCachingProvider().getCacheManager().getURI()); //getCache("products"));
    }
}
