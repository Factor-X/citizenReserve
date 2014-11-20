package eu.factorx.citizenReserve.model.technical;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import javax.persistence.Embeddable;
import javax.persistence.Version;
import java.util.Date;

@Embeddable
public class TechnicalSegment {

    @CreatedTimestamp
    private Date creationDate;

    @UpdatedTimestamp
    @Version
    private Date lastUpdate;

    public TechnicalSegment() {
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    @Override
    public String toString() {
        return "TechnicalSegment{" +
                "creationDate=" + creationDate +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
