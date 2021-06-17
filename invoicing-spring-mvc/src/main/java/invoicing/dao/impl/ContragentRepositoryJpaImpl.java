package invoicing.dao.impl;

import invoicing.dao.ContragentRepository;
import invoicing.entity.Contragent;
import invoicing.entity.Unit;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

//import static net.sf.ehcache.CacheManager.ALL_CACHE_MANAGERS;

@Slf4j
@Repository
@Transactional
public class ContragentRepositoryJpaImpl implements ContragentRepository {
   

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate template;

    @Autowired
    private CacheManager cacheManager;

//    @Autowired
//    ConfigurableApplicationContext ctx;



    @Override
    @Transactional(readOnly = true)
    public List<Contragent> findAll() {
        return em.createQuery("SELECT p FROM Contragent AS p")
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contragent> findById(Long id) {
        return Optional.ofNullable(em.find(Contragent.class, id));
    }

    @Override
    public Contragent create(Contragent p) {
        em.persist(p);
        return p;
    }

    @Override
    @Transactional(timeout = 2)
    public List<Contragent> createBatch(Collection<Contragent> entities) throws EntityAlreadyExistsException {
        List<Contragent> results = new ArrayList<>();
        for (Contragent p : entities) {
            em.persist(p);
            results.add(p);
        }
        em.close();
        return results;
    }

    @Override
    public Contragent update(Contragent p) throws EntityNotFoundException {
        Optional<Contragent> old = findById(p.getId());
        if (old.isEmpty()) {
            throw new EntityNotFoundException(String.format("Entity with ID='%s' does not exist.", p.getId()));
        }
        Contragent result = em.merge(p);
        return result;
    }

    @Override
    public Contragent deleteById(Long id) throws EntityNotFoundException {
        var old = em.find(Contragent.class, id);
        if (old == null) {
            throw new EntityNotFoundException("Contragent with ID = '" + id + "' not found");
        }
        em.remove(old);
        return old;
    }

    @Override
    public long count() {
        return (Long) em.createQuery("SELECT COUNT(p) FROM Contragent p").getSingleResult();
    }

    @Override
    public long drop() {
        long count = em.createQuery("DELETE FROM Contragent p").executeUpdate();
        log.info("Dropping {} contragents.", count);
        return count;
    }

    @Transactional(propagation = NOT_SUPPORTED)
    public Statistics getStatistics(){
        return em.unwrap(Session.class).getSessionFactory().getStatistics();
    }

}
