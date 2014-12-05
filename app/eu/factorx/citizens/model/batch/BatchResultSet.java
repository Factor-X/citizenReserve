package eu.factorx.citizens.model.batch;

import eu.factorx.citizens.model.technical.AbstractEntity;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 4/12/14.
 */
@Entity
public class BatchResultSet extends AbstractEntity {

    @OneToMany(mappedBy = "batchResultSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatchResult> results = new ArrayList<>();


    public BatchResultSet() {
    }

    public void addBatchResult(BatchResult batchResult) {
        results.add(batchResult);
    }

    public List<BatchResult> getResults() {
        return results;
    }

    public void setResults(List<BatchResult> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "BatchResultSet{" +
                "results=" + results +
                '}';
    }
}
