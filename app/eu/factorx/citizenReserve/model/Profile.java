package eu.factorx.citizenReserve.model;

import eu.factorx.citizenReserve.model.Account;
import eu.factorx.citizenReserve.model.technical.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="profiles")
public class Profile extends AbstractEntity {

	@Id
	@GeneratedValue
	public long id;

	@Version
	public long version;

	@OneToOne
	@JoinColumn(name = "id", unique = true)
	public Account account;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
	Set<Topic> topics;

	@Column(nullable = false)
    private String type;


	public Profile() {
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Profile{" +
				", type='" + type + '\'' +
				", account=" + account +
				'}';
	}
}
