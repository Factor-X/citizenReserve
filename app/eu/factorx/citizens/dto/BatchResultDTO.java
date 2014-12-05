package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 4/12/14.
 */
public class BatchResultDTO extends DTO {

    private String reductionTypeKey;

    private Integer nbSurveys;

    private Integer nbErrors;

    private Integer nbParticipants;

    private List<BatchResultItemDTO> batchResultItemList;

    public BatchResultDTO() {
    }

    public Integer getNbParticipants() {
        return nbParticipants;
    }

    public void setNbParticipants(Integer nbParticipants) {
        this.nbParticipants = nbParticipants;
    }

    public String getReductionTypeKey() {
        return reductionTypeKey;
    }

    public void setReductionTypeKey(String reductionTypeKey) {
        this.reductionTypeKey = reductionTypeKey;
    }

    public Integer getNbSurveys() {
        return nbSurveys;
    }

    public void setNbSurveys(Integer nbSurveys) {
        this.nbSurveys = nbSurveys;
    }

    public Integer getNbErrors() {
        return nbErrors;
    }

    public void setNbErrors(Integer nbErrors) {
        this.nbErrors = nbErrors;
    }

    public List<BatchResultItemDTO> getBatchResultItemList() {
        return batchResultItemList;
    }

    public void setBatchResultItemList(List<BatchResultItemDTO> batchResultItemList) {
        this.batchResultItemList = batchResultItemList;
    }

    @Override
    public String toString() {
        return "BatchResultDTO{" +
                "reductionTypeKey='" + reductionTypeKey + '\'' +
                ", nbSurveys=" + nbSurveys +
                ", nbErrors=" + nbErrors +
                ", batchResultItemList=" + batchResultItemList +
                '}';
    }

    public void addBatchResultItem(BatchResultItemDTO convert) {
        if (batchResultItemList == null) {
            batchResultItemList = new ArrayList<>();
        }
        this.batchResultItemList.add(convert);
    }
}
