package eu.factorx.citizens.service;

import eu.factorx.citizens.model.batch.BatchResult;

public interface BatchService {

    void run();

    BatchResult findLastBatchResult();

    java.util.List<BatchResult> findBatchToDisplayForSuperAdmin();
}
