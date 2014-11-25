package eu.factorx.citizenReserve.model;

import eu.factorx.citizenReserve.model.technical.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name="actions")
public class Question extends AbstractEntity {

	@Id
	@GeneratedValue
	public long id;

	@Version
	public long version;

	@ManyToOne(optional = false)
	@JoinColumn(name = "questions", referencedColumnName = "id")
	private Topic topic;

	public Question() {
    }

}
