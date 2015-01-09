package eu.factorx.citizens.model.survey;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.technical.AbstractEntity;
import eu.factorx.citizens.model.type.QuestionCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "surveys")
@NamedQueries({
        @NamedQuery(name = Survey.FIND_VALID_BY_ACCOUNT, query = "where account_id = :" + Survey.COL_ACCOUNT +" and "+AbstractEntity.DELETION_DATE +" is null"),
})
public class Survey extends AbstractEntity {

    //request
    public static final String FIND_VALID_BY_ACCOUNT = "Survey_FIND_VALID_BY_ACCOUNT";
    public static final String COL_ACCOUNT = "account";
    //column name


    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Account account;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Answer> answers = new ArrayList<>();

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ActionAnswer> actionAnswers = new ArrayList<>();

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

	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}
	public void addAnswer(QuestionCode questionCode, Period period, Double value) {
		Answer answer = new Answer(this, questionCode, period);
		answer.addDoubleValue(value, period);
		this.answers.add(answer);
	}

	public void addAnswer(QuestionCode questionCode, Period period, String value) {
		Answer answer = new Answer(this, questionCode, period);
		answer.addStringValue(value, period);
		this.answers.add(answer);
	}

	public void addAnswer(QuestionCode questionCode, Period period, Boolean value) {
		Answer answer = new Answer(this, questionCode, period);
		answer.addBooleanValue(value, period);
		this.answers.add(answer);
	}

	public List<ActionAnswer> getActionAnswers() {
		return actionAnswers;
	}
	public void setActionAnswers(List<ActionAnswer> actionAnswers) {
		this.actionAnswers = actionAnswers;
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
