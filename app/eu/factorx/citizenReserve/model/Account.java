package eu.factorx.citizenReserve.model;

import eu.factorx.citizenReserve.model.technical.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name="accounts")
public class Account extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String provider;

    private String powerConsumptionCategory;

    private String emailsHouseholdMembers;

    private String sensitizationKit;

    public Account() {
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPowerConsumptionCategory() {
        return powerConsumptionCategory;
    }

    public void setPowerConsumptionCategory(String powerConsumptionCategory) {
        this.powerConsumptionCategory = powerConsumptionCategory;
    }

    public String getEmailsHouseholdMembers() {
        return emailsHouseholdMembers;
    }

    public void setEmailsHouseholdMembers(String emailsHouseholdMembers) {
        this.emailsHouseholdMembers = emailsHouseholdMembers;
    }

    public String getSensitizationKit() {
        return sensitizationKit;
    }

    public void setSensitizationKit(String sensitizationKit) {
        this.sensitizationKit = sensitizationKit;
    }
}
