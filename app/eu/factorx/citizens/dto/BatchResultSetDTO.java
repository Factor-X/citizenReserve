package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

import java.util.Date;

/**
 * Created by florian on 4/12/14.
 */
public class BatchResultSetDTO extends DTO {

    private Date date;

    private BatchResultDTO potentialBatch;
    private BatchResultDTO effectiveBatch;

    public BatchResultSetDTO() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BatchResultDTO getPotentialBatch() {
        return potentialBatch;
    }

    public void setPotentialBatch(BatchResultDTO potentialBatch) {
        this.potentialBatch = potentialBatch;
    }

    public BatchResultDTO getEffectiveBatch() {
        return effectiveBatch;
    }

    public void setEffectiveBatch(BatchResultDTO effectiveBatch) {
        this.effectiveBatch = effectiveBatch;
    }

    @Override
    public String toString() {
        return "BatchResultSetDTO{" +
                "potentialBatch=" + potentialBatch +
                ", effectiveBatch=" + effectiveBatch +
                '}';
    }
}
