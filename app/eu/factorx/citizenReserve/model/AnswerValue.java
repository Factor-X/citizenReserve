package eu.factorx.citizenReserve.model;

import eu.factorx.citizenReserve.model.technical.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="answervalues")
public class AnswerValue extends AbstractEntity {

    @ManyToOne(optional = false)
    private Answer answer;

    private String stringValue;

    private Double doubleValue;

    public AnswerValue() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AnswerValue that = (AnswerValue) o;

        if (!answer.equals(that.answer)) return false;
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
        return result;
    }
}
