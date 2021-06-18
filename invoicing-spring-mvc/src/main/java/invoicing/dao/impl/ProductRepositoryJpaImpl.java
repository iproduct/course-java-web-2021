package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityNotFoundException;
import invoicing.entity.Product;
import invoicing.entity.Unit;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

//import static net.sf.ehcache.CacheManager.ALL_CACHE_MANAGERS;

@Slf4j
@Repository
@Transactional
public class ProductRepositoryJpaImpl implements ProductRepository {
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

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate template;

    @Autowired
    private CacheManager cacheManager;

//    @Autowired
//    ConfigurableApplicationContext ctx;

//    @PostConstruct
//    @EventListener(ApplicationStartedEvent.class)
    public void init() {
//        ctx.addApplicationListener((event) -> {
//            if(event.getClass() == ApplicationStartedEvent.class) {
//                List<Product> products = template.execute(status -> {
//                    System.out.printf("%n!!!!!! APP EVENT: %s%n%n", event);
                    drop();
                    log.info("Initializing DB with sample products.");
                    List<Product> created = new ArrayList<>();
                    try {
                        created = createBatch(SAMPLE_PRODUCTS);
                    } catch (EntityAlreadyExistsException e) {
                        log.error("Error initializing products", e);
//                        status.setRollbackOnly();
                    }
//                    return created;
//                });
                System.out.printf("Product created: %s%n", created);
//            }
//        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product AS p")
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    @Override
    public Product create(Product p) {
        em.persist(p);
        return p;
    }

    @Override
    @Transactional(timeout = 2)
    public List<Product> createBatch(Collection<Product> entities) throws EntityAlreadyExistsException {
        List<Product> results = new ArrayList<>();
        for (Product p : entities) {
            em.unwrap(Session.class).persist(p);
            results.add(p);
        }
        em.close();
        return results;
    }

    @Override
    public Product update(Product p) throws EntityNotFoundException {
        Optional<Product> old = findById(p.getId());
        if (old.isEmpty()) {
            throw new EntityNotFoundException(String.format("Entity with ID='%s' does not exist.", p.getId()));
        }
        Product result = em.merge(p);
        return result;
    }

    @Override
    public Product deleteById(Long id) throws EntityNotFoundException {
        var old = em.find(Product.class, id);
        if (old == null) {
            throw new EntityNotFoundException("Product with ID = '" + id + "' not found");
        }
        em.remove(old);
        return old;
    }

    @Override
    public long count() {
        return (Long) em.createQuery("SELECT COUNT(p) FROM Product p").getSingleResult();
    }

    @Override
    public long drop() {
        long count = em.createQuery("DELETE FROM Product p").executeUpdate();
        log.info("Dropping {} products.", count);
        return count;
    }

    @Transactional(propagation = NOT_SUPPORTED)
    public Statistics getStatistics(){
        return em.unwrap(Session.class).getSessionFactory().getStatistics();
    }

}
