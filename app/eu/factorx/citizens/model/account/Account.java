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

	public static final String ACCOUNT_TYPE_PROPERTY = "accountType";

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

	@Enumerated(value = EnumType.ORDINAL)
    private LanguageEnum language;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean superAdmin = false;

	private Double legacyAccountPowerReduction;

	private Boolean passwordToChange;

	private String organizationName;

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

	public Account(AccountType accountType, String email, String password, String firstName, String lastName, String zipCode, String powerProvider, String organizationName) {
		this(accountType,email,password,firstName,lastName,zipCode,powerProvider);
		this.organizationName = organizationName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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

	public Double getLegacyAccountPowerReduction() {
		return legacyAccountPowerReduction;
	}

	public void setLegacyAccountPowerReduction(Double legacyAccountPowerReduction) {
		this.legacyAccountPowerReduction = legacyAccountPowerReduction;
	}

	public Boolean getPasswordToChange() {
		return passwordToChange;
	}

	public void setPasswordToChange(Boolean passwordToChange) {
		this.passwordToChange = passwordToChange;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Account account = (Account) o;

		if (sensitizationKit != account.sensitizationKit) return false;
		if (superAdmin != account.superAdmin) return false;
		if (accountType != account.accountType) return false;
		if (!email.equals(account.email)) return false;
		if (!firstName.equals(account.firstName)) return false;
		if (language != account.language) return false;
		if (!lastName.equals(account.lastName)) return false;
		if (legacyAccountPowerReduction != null ? !legacyAccountPowerReduction.equals(account.legacyAccountPowerReduction) : account.legacyAccountPowerReduction != null)
			return false;
		if (otherEmailAdresses != null ? !otherEmailAdresses.equals(account.otherEmailAdresses) : account.otherEmailAdresses != null) return false;
		if (!password.equals(account.password)) return false;
		if (powerComsumerCategory != null ? !powerComsumerCategory.equals(account.powerComsumerCategory) : account.powerComsumerCategory != null) return false;
		if (powerProvider != null ? !powerProvider.equals(account.powerProvider) : account.powerProvider != null) return false;
		if (zipCode != null ? !zipCode.equals(account.zipCode) : account.zipCode != null) return false;

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
		result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
		result = 31 * result + (powerProvider != null ? powerProvider.hashCode() : 0);
		result = 31 * result + (powerComsumerCategory != null ? powerComsumerCategory.hashCode() : 0);
		result = 31 * result + (otherEmailAdresses != null ? otherEmailAdresses.hashCode() : 0);
		result = 31 * result + (sensitizationKit ? 1 : 0);
		result = 31 * result + (language != null ? language.hashCode() : 0);
		result = 31 * result + (superAdmin ? 1 : 0);
		result = 31 * result + (legacyAccountPowerReduction != null ? legacyAccountPowerReduction.hashCode() : 0);
		return result;
	}

	public String toString() {
		return "Account{" +
			"id=" + id +
			", accountType=" + accountType +
			", email='" + email + '\'' +
			", password='" + password + '\'' +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			'}';
	}
}
