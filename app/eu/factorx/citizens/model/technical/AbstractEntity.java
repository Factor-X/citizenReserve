package eu.factorx.citizens.model.technical;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import eu.factorx.citizens.model.batch.BatchResult;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity extends Model implements Comparator<AbstractEntity> {

    public static final String DELETION_DATE = "deletionDate";
    public static final String LAST_UPDATE_DATE = "lastUpdateDate";

    @Id
	@GeneratedValue
	protected Long id;

    @Version
    private long version;

    @CreatedTimestamp
    private Date creationDate;

    @UpdatedTimestamp
    @Version
    private Date lastUpdateDate;

	protected Date deletionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    public Date getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}

	public void markAsDeleted() {
		this.deletionDate = new Date();
	}

	public boolean isDeleted() {
		return (this.deletionDate != null);
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


}


