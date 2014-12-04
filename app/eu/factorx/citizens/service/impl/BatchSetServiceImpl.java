package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.batch.BatchResult;
import eu.factorx.citizens.model.batch.BatchResultSet;
import eu.factorx.citizens.service.BatchSetService;

import java.util.List;

public class BatchSetServiceImpl implements BatchSetService {

    @Override
    public List<BatchResultSet> findBatchToDisplayForSuperAdmin() {

        return Ebean.find(BatchResultSet.class).findList();


    }
}
