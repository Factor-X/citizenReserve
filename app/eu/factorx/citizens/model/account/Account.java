package eu.factorx.citizens.model.account;

import eu.factorx.citizens.model.technical.AbstractEntity;
import eu.factorx.citizens.model.type.AccountType;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@NamedQueries({
    @NamedQuery(name = Account.FIND_BY_EMAIL, query = "where email = :" + Account.COL_EMAIL),
    @NamedQuery(name = Account.FIND_BY_ID, query = "where id = :id"),
})
public class Account extends AbstractEntity {

    //request
    public static final String FIND_BY_EMAIL = "Account_FIND_BY_EMAIL";
    public static final String FIND_BY_ID    = "Account_FIND_BY_ID";


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

    private String zipCode;

    private String powerProvider;

    private String powerComsumerCategory;

    private String otherEmailAdresses;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean sensitizationKit;

    private LanguageEnum language;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean superAdmin = false;

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


    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
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

    public boolean isSensitizationKit() {
        return sensitizationKit;
    }

    public void setSensitizationKit(boolean sensitizationKit) {
        this.sensitizationKit = sensitizationKit;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Account account = (Account) o;

        if (sensitizationKit != account.sensitizationKit) {
            return false;
        }
        if (superAdmin != account.superAdmin) {
            return false;
        }
        if (accountType != account.accountType) {
            return false;
        }
        if (email != null ? !email.equals(account.email) : account.email != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(account.firstName) : account.firstName != null) {
            return false;
        }
        if (language != account.language) {
            return false;
        }
        if (lastName != null ? !lastName.equals(account.lastName) : account.lastName != null) {
            return false;
        }
        if (otherEmailAdresses != null ? !otherEmailAdresses.equals(account.otherEmailAdresses) : account.otherEmailAdresses != null) {
            return false;
        }
        if (password != null ? !password.equals(account.password) : account.password != null) {
            return false;
        }
        if (powerComsumerCategory != null ? !powerComsumerCategory.equals(account.powerComsumerCategory) : account.powerComsumerCategory != null) {
            return false;
        }
        if (powerProvider != null ? !powerProvider.equals(account.powerProvider) : account.powerProvider != null) {
            return false;
        }
        if (zipCode != null ? !zipCode.equals(account.zipCode) : account.zipCode != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (powerProvider != null ? powerProvider.hashCode() : 0);
        result = 31 * result + (powerComsumerCategory != null ? powerComsumerCategory.hashCode() : 0);
        result = 31 * result + (otherEmailAdresses != null ? otherEmailAdresses.hashCode() : 0);
        result = 31 * result + (sensitizationKit ? 1 : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (superAdmin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" + super.toString() +
            "accountType=" + accountType +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", zipCode='" + zipCode + '\'' +
            ", powerProvider='" + powerProvider + '\'' +
            ", powerComsumerCategory='" + powerComsumerCategory + '\'' +
            ", otherEmailAdresses='" + otherEmailAdresses + '\'' +
            ", sensitizationKit='" + sensitizationKit + '\'' +
            '}';
    }
}
