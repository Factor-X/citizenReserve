package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;
import play.data.validation.Constraints;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class AccountDTO extends DTO {

    private Long id;

    @Size(min = 2,max = 100)
    private String firstName;

    @Size(min = 2,max = 100)
    private String lastName;

    @Constraints.Email
    private String email;

    @Size(min = 6,max = 18)
    private String password;

    @Size(min = 4,max = 20)
    private String zipCode;

    @NotNull
    private String powerProvider;

    @NotNull
    private String powerComsumerCategory;

    private List<String> otherEmailAddresses;

    @NotNull
    private String sensitizationKit;

    @NotNull
    private String accountType;

    public AccountDTO() {
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

    public String getSensitizationKit() {
        return sensitizationKit;
    }

    public void setSensitizationKit(String sensitizationKit) {
        this.sensitizationKit = sensitizationKit;
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
