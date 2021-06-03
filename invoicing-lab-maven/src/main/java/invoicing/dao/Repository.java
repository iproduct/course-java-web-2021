package invoicing.dao;

import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityNotFoundException;
import invoicing.model.Identifiable;

import java.util.List;
import java.util.Optional;

public interface Repository<K, V extends Identifiable<K>> {
    List<V> findAll();
    Optional<V> findById(K id);
    V create(V entity) throws EntityAlreadyExistsException;
    V update(V entity) throws EntityNotFoundException;
    V deleteById(K id) throws EntityNotFoundException;
    long count();
}
