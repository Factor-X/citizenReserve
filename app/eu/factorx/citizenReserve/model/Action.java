package eu.factorx.citizenReserve.model;

import eu.factorx.citizenReserve.model.Profile;
import eu.factorx.citizenReserve.model.technical.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name="actions")
public class Action extends AbstractEntity {

	@Id
	@GeneratedValue
	public long id;

	@Version
	public long version;

	@ManyToOne(optional = false)
	@JoinColumn(name = "actions", referencedColumnName = "id")
	private Topic topic;

	public Action() {
    }

}
