package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityNotFoundException;
import invoicing.exception.PersistenceException;
import invoicing.model.Product;
import invoicing.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Override
    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product AS p")
                .getResultList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
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

    protected Product createInternal(Product p) throws SQLException {
        return null;
    }

    @Override
    public List<Product> createBatch(Collection<Product> entities) throws EntityAlreadyExistsException {
        List<Product> results = new ArrayList<>();
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
    public Product update(Product p) {
        return null;
    }

    @Override
    public Product deleteById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public long count() {
        return -1;
    }

    @Override
    public void drop() {

    }

}
