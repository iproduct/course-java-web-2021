package invoicing.dao;

import invoicing.dao.impl.LongKeyGenerator;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.exception.EntityNotFoundException;
import invoicing.model.Identifiable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Repository<K, V extends Identifiable<K>> {
    static final Map<String, String> ENTITY_TO_REPOSITORY_MAP =
            Map.of(
                    "invoicing.model.Product", "invoicing.dao.impl.ProductRepositoryMemoryImpl",
                    "invoicing.model.Supplier", "invoicing.dao.impl.SupplierRepositoryMemoryImpl",
                    "invoicing.model.Customer", "invoicing.dao.impl.CustomerRepositoryMemoryImpl"
            );
    static final Map<String, String> KEY_TO_GENERATOR_MAP =
            Map.of(
                    "java.lang.Long", "invoicing.dao.impl.LongKeyGenerator"
            );
    List<V> findAll();
    Optional<V> findById(K id);
    V create(V entity) throws EntityAlreadyExistsException;
    V update(V entity) throws EntityNotFoundException;
    V deleteById(K id) throws EntityNotFoundException;
    long count();

    static <K, V extends Identifiable<K>> Repository<K, V > createRepository(Class<K> keyClass, Class<V> entityClass) {
       String implClassName =  ENTITY_TO_REPOSITORY_MAP.get(entityClass.getName());
       try {
            Class<V> implClass = (Class<V>) Class.forName(implClassName);
            Constructor constructor;
            try {
                constructor = implClass.getDeclaredConstructor(KeyGenerator.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                constructor = implClass.getDeclaredConstructor();
            }
            String genClassName = KEY_TO_GENERATOR_MAP.get(keyClass.getName());
            Class<KeyGenerator<K>> genClass = (Class<KeyGenerator<K>>) Class.forName(genClassName);
            Constructor<KeyGenerator<K>> genConstructor = genClass.getConstructor();
//            String interfaceClasName = ENTITY_TO_REPOSITORY_INTERFACE_MAP.get(entityClass.getName());
//            Class interfaceClass = Class.forName(interfaceClasName);
           System.out.println(genConstructor);
            return (Repository<K, V>) constructor.newInstance(genConstructor.newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
