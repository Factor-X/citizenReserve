package eu.factorx.citizenReserve.model;

import eu.factorx.citizenReserve.model.technical.AbstractEntity;

import javax.persistence.*;

/**
 * Created by florian on 20/11/14.
 */
@Entity
@Table(name="accounts")
public class Account extends AbstractEntity {


	@Id
	@GeneratedValue
	public long id;

	@Version
	public long version;

	@OneToOne (mappedBy = "account", cascade=CascadeType.ALL)
	public Profile profile;


	@Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


    public Account() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Account{" +
                super.toString()+
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
