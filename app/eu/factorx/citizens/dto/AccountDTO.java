package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;
import eu.factorx.citizens.dto.technical.verification.NotNull;
import eu.factorx.citizens.dto.technical.verification.Pattern;
import eu.factorx.citizens.dto.technical.verification.Size;

import java.util.ArrayList;
import java.util.List;

public class AccountDTO extends DTO {

	private Long id;

	@Size(min = 2, max = 100)
	private String firstName;

	@Size(min = 2, max = 100)
	private String lastName;

	@Pattern(regexp = Pattern.EMAIL)
	private String email;

	@Size(min = 6, max = 18)
	private String password;

	private String zipCode;

	private String powerProvider;

	private String powerComsumerCategory;

	private List<String> otherEmailAddresses = new ArrayList<>();

	private boolean sensitizationKit;

	@NotNull
	private String languageAbrv;

	@NotNull
	private String accountType;

	private boolean passwordToChange;

	private String organizationName;

	private Double powerConsumption;

	public AccountDTO() {
	}

	public Double getPowerConsumption() {
		return powerConsumption;
	}

	public void setPowerConsumption(Double powerConsumption) {
		this.powerConsumption = powerConsumption;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getLanguageAbrv() {
		return languageAbrv;
	}

	public void setLanguageAbrv(String languageAbrv) {
		this.languageAbrv = languageAbrv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public List<String> getOtherEmailAddresses() {
		return otherEmailAddresses;
	}

	public void setOtherEmailAddresses(List<String> otherEmailAddresses) {
		this.otherEmailAddresses = otherEmailAddresses;
	}

	public boolean isSensitizationKit() {
		return sensitizationKit;
	}

	public void setSensitizationKit(boolean sensitizationKit) {
		this.sensitizationKit = sensitizationKit;
	}

	public boolean isPasswordToChange() {
		return passwordToChange;
	}

	public void setPasswordToChange(boolean passwordToChange) {
		this.passwordToChange = passwordToChange;
	}

	@Override
	public String toString() {
		return "AccountDTO{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
