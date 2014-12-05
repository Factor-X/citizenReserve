package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.batch.BatchResultSet;
import eu.factorx.citizens.service.BatchSetService;

import java.util.List;

public class BatchSetServiceImpl implements BatchSetService {

    @Override
    public List<BatchResultSet> findBatchToDisplayForSuperAdmin() {

        return Ebean.find(BatchResultSet.class).findList();


    }

    @Override
    public BatchResultSet findLast() {

        return Ebean.find(BatchResultSet.class)
                .orderBy("creationDate")
                .setMaxRows(1)
                .findUnique();
    }
}
