package eu.factorx.citizenReserve.model.technical;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import javax.persistence.Embeddable;
import javax.persistence.Version;
import java.util.Date;

@Embeddable
public class TechnicalSegment {

    @Version
    private long version;

    @CreatedTimestamp
    private Date creationDate;

    @UpdatedTimestamp
    @Version
    private Date lastUpdateDate;

    public TechnicalSegment() {
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        return "TechnicalSegment{" +
                "version=" + version +
                ", creationDate=" + creationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
