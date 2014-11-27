package eu.factorx.citizens.model.survey;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.technical.AbstractEntity;
import eu.factorx.citizens.model.type.Period;
import eu.factorx.citizens.model.type.QuestionCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "surveys")
public class Survey extends AbstractEntity {

	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Account account;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Answer> answers = new ArrayList<>();

	public Survey() {
	}

	public Survey(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public void addAnswer(QuestionCode questionCode, Period period, double value) {
		Answer answer = new Answer(this, questionCode, period);
		answer.addDoubleValue(value, period);
		this.answers.add(answer);
	}

	public void addAnswer(QuestionCode questionCode, Period period, String value) {
		Answer answer = new Answer(this, questionCode, period);
		answer.addStringValue(value, period);
		this.answers.add(answer);
	}

	public void addAnswer(QuestionCode questionCode, Period period, boolean value) {
		Answer answer = new Answer(this, questionCode, period);
		answer.addBooleanValue(value, period);
		this.answers.add(answer);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Survey survey = (Survey) o;

		if (!account.equals(survey.account)) return false;
		if (!answers.equals(survey.answers)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + account.hashCode();
		result = 31 * result + answers.hashCode();
		return result;
	}
}
