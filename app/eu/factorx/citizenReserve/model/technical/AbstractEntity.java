package eu.factorx.citizenReserve.model.technical;

import play.db.ebean.Model;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Comparator;

/**
 * Created by florian on 20/11/14.
 */
@MappedSuperclass
public abstract class AbstractEntity extends Model implements Comparator<AbstractEntity> {

    @Id
    protected Long id;

    @Embedded
    protected TechnicalSegment technicalSegment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TechnicalSegment getTechnicalSegment() {
        return technicalSegment;
    }

    public void setTechnicalSegment(TechnicalSegment technicalSegment) {
        this.technicalSegment = technicalSegment;
    }

    @Override
    public int compare(AbstractEntity o1, AbstractEntity o2) {
        return Long.compare(o1.getId(), o2.getId());
    }

    /**
     * equals : based on id
     *
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            if (((AbstractEntity) o).getId().equals(this.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "id=" + id +
                ", technicalSegment=" + technicalSegment +
                '}';
    }
}


