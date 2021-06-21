package invoicing.dao;

import invoicing.entity.Contragent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ContragentRepository extends JpaRepository<Contragent, Long> {
}
