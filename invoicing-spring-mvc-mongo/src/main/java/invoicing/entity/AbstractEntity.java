package invoicing.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
public abstract class AbstractEntity<K extends Comparable<K> & Serializable, V extends Identifiable<K>>
        implements Identifiable<K>, Comparable<V>, Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified = new Date();

    public AbstractEntity() {
    }

    public AbstractEntity(K id, Date created, Date modified) {
        this.created = created;
        this.modified = modified;
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
    public int compareTo(V other) {
        return getId().compareTo(other.getId());
    }
}
