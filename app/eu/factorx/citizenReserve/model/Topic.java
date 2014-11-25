package eu.factorx.citizenReserve.model;

import eu.factorx.citizenReserve.model.technical.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="topics")
public class Topic extends AbstractEntity {

	@Id
	@GeneratedValue
	public long id;

	@Version
	public long version;

	@ManyToOne(optional = false)
	@JoinColumn(name = "topics", referencedColumnName = "id")
	private Profile profile;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
	Set<Action> actions;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
	Set<Question> questions;


	public Topic() {
    }




}
