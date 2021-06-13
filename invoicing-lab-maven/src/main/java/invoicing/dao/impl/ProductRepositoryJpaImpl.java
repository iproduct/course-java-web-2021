package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityCreationException;
import invoicing.exception.EntityNotFoundException;
import invoicing.exception.EntityUpdateException;
import invoicing.model.Product;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.*;

@Slf4j
public class ProductRepositoryJpaImpl implements ProductRepository {
    EntityManagerFactory emf;
    EntityManager em;

    public ProductRepositoryJpaImpl() {
    }

    public void init() {
        emf = Persistence.createEntityManagerFactory("invoicingPU");
        em  = emf.createEntityManager();
    }

    public void clean() {
        em.close();
    }

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
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(p);
//        em.flush();
        transaction.commit();
        return p;
    }

    @Override
    public List<Product> createBatch(Collection<Product> entities) throws EntityAlreadyExistsException {
        List<Product> results = new ArrayList<>();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            for (Product p : entities) {
                em.persist(p);
                results.add(p);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            throw new EntityCreationException("Error creating products batch ", e);
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
        if(old.isEmpty()){
            throw new EntityNotFoundException(String.format("Entity with ID='%s' does not exist.", p.getId()));
        }

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            Product result = em.merge(p);
            transaction.commit();
            return result;
        } catch (IllegalArgumentException | PersistenceException e) {
            transaction.rollback();
            throw new EntityUpdateException("Error updating entity:" + p, e);
        }
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
