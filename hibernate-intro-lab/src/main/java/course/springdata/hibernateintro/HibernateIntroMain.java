package course.springdata.hibernateintro;

import course.springdata.hibernateintro.entity.Student;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class HibernateIntroMain {
    public static void main(String[] args) {
        //Create Hibernate config
        Configuration cfg = new Configuration();
        cfg.configure();

        // Create SessionFactory
        SessionFactory sf = cfg.buildSessionFactory();

        // Create Session
        Session session = sf.openSession();

        // Persist an entity
        Student student = new Student("Hristo Iliev");
        session.beginTransaction();
        Long id = (Long)session.save(student);
        session.getTransaction().commit();

        // Read entity by Id
        session.beginTransaction();
        session.setHibernateFlushMode(FlushMode.MANUAL);
//        Student result = session.get(Student.class, 1L, LockMode.READ);
        long queryId = 1L;
//        Student result = session.byId(Student.class).load(queryId);
//        List<Student> result = session.byMultipleIds(Student.class).multiLoad(1L); //loadOptional(1L);
        Optional<Student> result = session.byId(Student.class).loadOptional(1L);
        session.getTransaction().commit();
        if(result.isPresent()) {
            System.out.printf("!!! Student: %s", result.get());
        } else {
            System.out.printf("Student with ID:%d does not exist.%n", queryId);
        }

        // List all students using HQL
        session.beginTransaction();
        session.setHibernateFlushMode(FlushMode.MANUAL);
        session.createQuery("FROM Student", Student.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .stream().forEach(System.out::println);
        session.getTransaction().commit();

        System.out.println("\n----------------------------------------------");
        session.createQuery("FROM Student WHERE name = ?1", Student.class)
                .setParameter(1,"Hristo Iliev")
                .stream().forEach(System.out::println);

        // Type-safe criteria quieries
        System.out.println("\n----------------------------------------------");
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> Student_ = query.from(Student.class);
        query.select(Student_).where(cb.like(Student_.get("name"), "D%"));
        session.createQuery(query).getResultStream()
                .forEach(System.out::println);

        // Close Session
        session.close();
    }
}
