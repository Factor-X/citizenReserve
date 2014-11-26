package eu.factorx.citizenReserve.dto;

import eu.factorx.citizenReserve.dto.technical.DTO;

import java.util.List;


public class AnswerDTO extends DTO {

    private String questionKey;

    private String periodKey;

    private List<AnswerValueDTO> answerValues;

    public AnswerDTO() {
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
}
