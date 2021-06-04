package invoicing.model;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractEntity<K extends Comparable<K>, V extends Identifiable<K>>
        implements Identifiable<K>, Comparable<V>, Serializable {
    private K id;
    private Date created = new Date();
    private Date modified = new Date();

    public AbstractEntity() {
    }

    public AbstractEntity(K id) {
        this.id = id;
    }

    public AbstractEntity(K id, Date created, Date modified) {
        this.id = id;
        this.created = created;
        this.modified = modified;
    }

    @Override
    public K getId() {
        return id;
    }

    @Override
    public void setId(K id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        AbstractEntity<?, ?> that = (AbstractEntity<?, ?>) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int compareTo(V other) {
        return getId().compareTo(other.getId());
    }
}
