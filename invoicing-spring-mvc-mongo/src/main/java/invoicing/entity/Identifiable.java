package invoicing.entity;

public interface Identifiable<K> {
    K getId();
    void setId(K id);
}
