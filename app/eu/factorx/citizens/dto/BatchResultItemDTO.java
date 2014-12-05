package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

/**
 * Created by florian on 4/12/14.
 */
public class BatchResultItemDTO extends DTO {

    private String dayKey;

    private String periodKey;

    private Double powerReduction;

    public BatchResultItemDTO() {
    }

    public String getDayKey() {
        return dayKey;
    }

    public void setDayKey(String reductionDayKey) {
        this.dayKey = reductionDayKey;
    }

    public String getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public Double getPowerReduction() {
        return powerReduction;
    }

    public void setPowerReduction(Double powerReduction) {
        this.powerReduction = powerReduction;
    }

    @Override
    public String toString() {
        return "BatchResultItem{" +
                "dayKey='" + dayKey + '\'' +
                ", periodKey='" + periodKey + '\'' +
                ", powerReduction=" + powerReduction +
                '}';
    }
}
