package eu.factorx.citizenReserve.dto;

import java.util.List;

public class SurveyDTO {

    private AccountDTO account;

    private List<AnswerDTO> answers;

    public SurveyDTO() {
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
