package eu.factorx.citizens.model.survey;

import eu.factorx.citizens.model.technical.AbstractEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "answers")
public class Answer extends AbstractEntity {

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Survey survey;

    @Enumerated(value = EnumType.STRING)
    private QuestionCode questionCode;

    @Enumerated(value = EnumType.STRING)
    private Period period;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AnswerValue> answerValues = new HashSet<>();

    public Answer() {
    }

    public Answer(Survey survey, QuestionCode questionCode) {
        this.survey = survey;
        this.questionCode = questionCode;
    }

    public Answer(Survey survey, QuestionCode questionCode, Period period) {
        this.survey = survey;
        this.questionCode = questionCode;
        this.period = period;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
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

    public boolean addStringValue(String value) {
        return answerValues.add(new AnswerValue(this, value));
    }

    public boolean addDoubleValue(Double value) {
        return answerValues.add(new AnswerValue(this, value));
    }

    public boolean addBooleanValue(Boolean value) {
        return answerValues.add(new AnswerValue(this, value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Answer answer = (Answer) o;

        if (period != answer.period) return false;
        if (questionCode != answer.questionCode) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + questionCode.hashCode();
        result = 31 * result + (period != null ? period.hashCode() : 0);
        return result;
    }
}
