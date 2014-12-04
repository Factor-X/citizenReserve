package eu.factorx.citizens.service;

import eu.factorx.citizens.model.batch.BatchResult;

import java.util.List;

/**
 * Created by florian on 4/12/14.
 */
public interface BatchSetService {

    public List<eu.factorx.citizens.model.batch.BatchResultSet> findBatchToDisplayForSuperAdmin();
}
