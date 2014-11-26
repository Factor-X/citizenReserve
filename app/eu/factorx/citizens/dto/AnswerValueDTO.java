package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

public class AnswerValueDTO extends DTO {

    private String stringValue;

    private Double doubleValue;

    private Boolean booleanValue;

    public AnswerValueDTO() {
    }

    public AnswerValueDTO(String stringValue) {
        this.stringValue = stringValue;
    }

    public AnswerValueDTO(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public AnswerValueDTO(Boolean booleanValue) {
        this.booleanValue = booleanValue;
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
}
