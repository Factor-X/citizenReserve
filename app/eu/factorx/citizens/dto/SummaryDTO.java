package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

/**
 * Created by florian on 27/11/14.
 */
public class SummaryDTO extends DTO {

    private AccountDTO account;

    public SummaryDTO() {
    }

    public SummaryDTO(AccountDTO account) {
        this.account = account;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "SummaryDTO{" +
                "account=" + account +
                '}';
    }
}
