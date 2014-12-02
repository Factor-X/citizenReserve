package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

/**
 * Created by florian on 2/12/14.
 */
public class ChangeEmailDTO extends DTO {

    private String oldPassword;
    private String email;

    public ChangeEmailDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ChangeEmailDTO{" +
                "oldPassword='" + oldPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
