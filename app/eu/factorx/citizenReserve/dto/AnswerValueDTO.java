package eu.factorx.citizenReserve.dto;

import eu.factorx.citizenReserve.dto.technical.DTO;

public class AnswerValueDTO extends DTO {

    private String stringValue;

    private Double doubleValue;

    private Boolean booleanValue;

    public AnswerValueDTO() {
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
