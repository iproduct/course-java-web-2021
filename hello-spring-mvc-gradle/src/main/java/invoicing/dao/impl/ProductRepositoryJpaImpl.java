package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityCreationException;
import invoicing.exception.EntityNotFoundException;
import invoicing.exception.EntityUpdateException;
import invoicing.model.Product;
import invoicing.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product AS p")
                .getResultList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    @Override
    public Product create(Product p) {
        em.persist(p);
        return p;
    }

    @Override
    public List<Product> createBatch(Collection<Product> entities) throws EntityAlreadyExistsException {
        List<Product> results = new ArrayList<>();
        for (Product p : entities) {
            em.persist(p);
            results.add(p);
        }
        return results;
    }

//    @Override
//    public List<Product> createBatch(Collection<Product> entities) throws EntityAlreadyExistsException {
//        List<Product> results = new ArrayList<>();
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.addBatch("INSERT ...");
//            stmt.addBatch("INSERT ...");
//            stmt.addBatch("INSERT ...");
//            stmt.executeBatch();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return results;
//    }

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
        return null;
    }

    @Override
    public long count() {
        return (Long) em.createQuery("SELECT COUNT(p) FROM Product p").getSingleResult();
    }

    @Override
    public void drop() {

    }

}
