package eu.factorx.citizens.model.batch;

import eu.factorx.citizens.model.technical.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by florian on 4/12/14.
 */
@Entity
public class BatchResultSet extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    private BatchResult potentialBach;

    @ManyToOne(cascade = CascadeType.ALL)
    private BatchResult effectiveBach;

    public BatchResultSet() {
    }

    public BatchResult getPotentialBach() {
        return potentialBach;
    }

    public void setPotentialBach(BatchResult potentialBach) {
        this.potentialBach = potentialBach;
    }

    public BatchResult getEffectiveBach() {
        return effectiveBach;
    }

    public void setEffectiveBach(BatchResult effectiveBach) {
        this.effectiveBach = effectiveBach;
    }

    @Override
    public String toString() {
        return "BatchResultSet{" +
                "potentialBach=" + potentialBach +
                ", effectiveBach=" + effectiveBach +
                '}';
    }
}
