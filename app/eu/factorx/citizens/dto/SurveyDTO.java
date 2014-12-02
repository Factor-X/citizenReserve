package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

import java.util.List;

public class SurveyDTO extends DTO {

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

    @Override
    public String toString() {
        return "SurveyDTO{" +
                "account=" + account +
                ", answers=" + answers +
                '}';
    }
}
