package eu.factorx.citizens.model.account;

import eu.factorx.citizens.model.technical.AbstractEntity;
import eu.factorx.citizens.model.type.AccountType;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@NamedQueries({
        @NamedQuery(name = Account.FIND_BY_EMAIL, query = "where " + Account.COL_EMAIL + " = :" + Account.COL_EMAIL),
})
public class Account extends AbstractEntity {

    //request
    public static final String FIND_BY_EMAIL = "Account_FIND_BY_EMAIL";


    //column
    public static final String COL_EMAIL = "email";


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(unique = true, nullable = false, name = COL_EMAIL)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String powerProvider;

    private String powerComsumerCategory;

    private String otherEmailAdresses;

    private String sensitizationKit;

    public Account() {
    }

    public Account(AccountType accountType, String email, String password, String firstName, String lastName, String zipCode, String powerProvider) {
        this.accountType = accountType;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.powerProvider = powerProvider;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPowerProvider() {
        return powerProvider;
    }

    public void setPowerProvider(String powerProvider) {
        this.powerProvider = powerProvider;
    }

    public String getPowerComsumerCategory() {
        return powerComsumerCategory;
    }

    public void setPowerComsumerCategory(String powerComsumerCategory) {
        this.powerComsumerCategory = powerComsumerCategory;
    }

    public String getOtherEmailAdresses() {
        return otherEmailAdresses;
    }

    public void setOtherEmailAdresses(String otherEmailAdresses) {
        this.otherEmailAdresses = otherEmailAdresses;
    }

    public String getSensitizationKit() {
        return sensitizationKit;
    }

    public void setSensitizationKit(String sensitizationKit) {
        this.sensitizationKit = sensitizationKit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Account account = (Account) o;

        if (accountType != account.accountType) return false;
        if (!email.equals(account.email)) return false;
        if (!firstName.equals(account.firstName)) return false;
        if (!lastName.equals(account.lastName)) return false;
        if (otherEmailAdresses != null ? !otherEmailAdresses.equals(account.otherEmailAdresses) : account.otherEmailAdresses != null)
            return false;
        if (!password.equals(account.password)) return false;
        if (powerComsumerCategory != null ? !powerComsumerCategory.equals(account.powerComsumerCategory) : account.powerComsumerCategory != null)
            return false;
        if (!powerProvider.equals(account.powerProvider)) return false;
        if (sensitizationKit != null ? !sensitizationKit.equals(account.sensitizationKit) : account.sensitizationKit != null)
            return false;
        if (!zipCode.equals(account.zipCode)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + accountType.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + zipCode.hashCode();
        result = 31 * result + powerProvider.hashCode();
        result = 31 * result + (powerComsumerCategory != null ? powerComsumerCategory.hashCode() : 0);
        result = 31 * result + (otherEmailAdresses != null ? otherEmailAdresses.hashCode() : 0);
        result = 31 * result + (sensitizationKit != null ? sensitizationKit.hashCode() : 0);
        return result;
    }
}
