package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

/**
 * Created by florian on 2/12/14.
 */
public class ChangePasswordDTO extends DTO {

    private String oldPassword;
    private String newPassword;

    public ChangePasswordDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
