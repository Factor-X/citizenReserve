package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

/**
 * Created by florian on 5/12/14.
 */
public class SummaryResultDTO extends DTO {

    private Integer nbSurvey;

    private Integer nbParticipants;

    private Double effectiveReduction;

    public SummaryResultDTO(Integer nbSurvey, Integer nbParticipants, Double effectiveReduction) {
        this.nbSurvey = nbSurvey;
        this.nbParticipants = nbParticipants;
        this.effectiveReduction = effectiveReduction;
    }

    public Integer getNbSurvey() {
        return nbSurvey;
    }

    public void setNbSurvey(Integer nbSurvey) {
        this.nbSurvey = nbSurvey;
    }

    public Integer getNbParticipants() {
        return nbParticipants;
    }

    public void setNbParticipants(Integer nbParticipants) {
        this.nbParticipants = nbParticipants;
    }

    public Double getEffectiveReduction() {
        return effectiveReduction;
    }

    public void setEffectiveReduction(Double effectiveReduction) {
        this.effectiveReduction = effectiveReduction;
    }

    @Override
    public String toString() {
        return "SummaryResultDTO{" +
                "nbSurvey=" + nbSurvey +
                ", nbParticipants=" + nbParticipants +
                ", effectiveReduction=" + effectiveReduction +
                '}';
    }
}
