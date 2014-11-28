package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;
import play.data.validation.Constraints;

import javax.validation.constraints.Size;

/**
 * Created by florian on 28/11/14.
 */
public class LoginDTO extends DTO {

    @Constraints.Email
    private String email;

    @Size(min = 6,max = 18)
    private String password;

    public LoginDTO() {
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
        return "LoginDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
