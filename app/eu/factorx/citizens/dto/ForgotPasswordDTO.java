package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

/**
 * Created by florian on 1/12/14.
 */
public class ForgotPasswordDTO extends DTO {

    private String email;

    public ForgotPasswordDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ForgotPasswordDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
