package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

import java.util.ArrayList;
import java.util.List;


public class AnswerDTO extends DTO {

    private String questionKey;

    private String periodKey;

    private List<AnswerValueDTO> answerValues = new ArrayList<>();

    public AnswerDTO() {
    }

    public AnswerDTO(String questionKey, String periodKey) {
        this.questionKey = questionKey;
        this.periodKey = periodKey;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public String getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public List<AnswerValueDTO> getAnswerValues() {
        return answerValues;
    }

    public void setAnswerValues(List<AnswerValueDTO> answerValues) {
        this.answerValues = answerValues;
    }

    public boolean addStringValue(String value) {
        return answerValues.add(new AnswerValueDTO(value));
    }

    public boolean addDoubleValue(Double value) {
        return answerValues.add(new AnswerValueDTO(value));
    }

    public boolean addBooleanValue(Boolean value) {
        return answerValues.add(new AnswerValueDTO(value));
    }

}
