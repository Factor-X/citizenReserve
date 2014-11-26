package eu.factorx.citizenReserve.model.survey;

import eu.factorx.citizenReserve.model.technical.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "answervalues")
public class AnswerValue extends AbstractEntity {

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Answer answer;

    private String stringValue;

    private Double doubleValue;

    private Boolean booleanValue;

    public AnswerValue() {
    }

    public AnswerValue(Answer answer, String stringValue) {
        this.answer = answer;
        this.stringValue = stringValue;
    }

    public AnswerValue(Answer answer, Double doubleValue) {
        this.answer = answer;
        this.doubleValue = doubleValue;
    }

    public AnswerValue(Answer answer, Boolean booleanValue) {
        this.answer = answer;
        this.booleanValue = booleanValue;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AnswerValue that = (AnswerValue) o;

        if (!answer.equals(that.answer)) return false;
        if (booleanValue != null ? !booleanValue.equals(that.booleanValue) : that.booleanValue != null) return false;
        if (doubleValue != null ? !doubleValue.equals(that.doubleValue) : that.doubleValue != null) return false;
        if (stringValue != null ? !stringValue.equals(that.stringValue) : that.stringValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + answer.hashCode();
        result = 31 * result + (stringValue != null ? stringValue.hashCode() : 0);
        result = 31 * result + (doubleValue != null ? doubleValue.hashCode() : 0);
        result = 31 * result + (booleanValue != null ? booleanValue.hashCode() : 0);
        return result;
    }
}
