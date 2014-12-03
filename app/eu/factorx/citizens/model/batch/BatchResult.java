package eu.factorx.citizens.model.batch;

import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.type.ReductionDay;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "batchresult")
public class BatchResult {

    @Enumerated(value = EnumType.STRING)
    private PowerReductionType reductionType;

    private Integer nbSurveys = 0;

    private Integer nbErrors = 0;

    @OneToMany(mappedBy = "batchResult")
    private List<BatchResultItem> resultItems = new ArrayList<>();

    public BatchResult() {
    }

    public BatchResult(PowerReductionType reductionType, Integer nbSurveys, Integer nbErrors) {
        this.reductionType = reductionType;
        this.nbSurveys = nbSurveys;
        this.nbErrors = nbErrors;
    }

    public PowerReductionType getReductionType() {
        return reductionType;
    }

    public void setReductionType(PowerReductionType reductionType) {
        this.reductionType = reductionType;
    }

    public List<BatchResultItem> getResultItems() {
        return resultItems;
    }

    public void setResultItems(List<BatchResultItem> resultItems) {
        this.resultItems = resultItems;
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

    public void incrementNbSurveys() {
        this.nbSurveys++;
    }

    public void incrementNbErrors() {
        this.nbErrors++;
    }

    public void addResultItem(ReductionDay day, Period period, Double powerReduction) {
        this.resultItems.add(new BatchResultItem(day, period, powerReduction, this));
    }

}
