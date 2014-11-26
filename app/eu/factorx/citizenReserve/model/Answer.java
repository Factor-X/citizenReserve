package eu.factorx.citizenReserve.model;

import eu.factorx.citizenReserve.model.technical.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "answers")
public class Answer extends AbstractEntity {

    @ManyToOne(optional = false)
    private Account account;

    @Enumerated(value = EnumType.STRING)
    private QuestionCode questionCode;

    @Enumerated(value = EnumType.STRING)
    private Period period;

    @OneToMany(mappedBy = "answer")
    private Set<AnswerValue> answerValues;

    public Answer() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public QuestionCode getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(QuestionCode questionCode) {
        this.questionCode = questionCode;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Set<AnswerValue> getAnswerValues() {
        return answerValues;
    }

    public void setAnswerValues(Set<AnswerValue> answerValues) {
        this.answerValues = answerValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Answer answer = (Answer) o;

        if (!account.equals(answer.account)) return false;
        if (period != answer.period) return false;
        if (questionCode != answer.questionCode) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + account.hashCode();
        result = 31 * result + questionCode.hashCode();
        result = 31 * result + (period != null ? period.hashCode() : 0);
        return result;
    }
}
