package eu.factorx.citizens.controllers;

import eu.factorx.citizens.converter.BatchSetToBatchSetDTOConverter;
import eu.factorx.citizens.dto.DoubleDTO;
import eu.factorx.citizens.model.batch.BatchResultItem;
import eu.factorx.citizens.model.batch.BatchResultSet;
import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.type.ReductionDay;
import eu.factorx.citizens.service.BatchSetService;
import eu.factorx.citizens.service.impl.BatchSetServiceImpl;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import play.db.ebean.Transactional;
import play.mvc.Result;

/**
 * Created by florian on 5/12/14.
 */
public class BatchResultController extends AbstractController {

    //service
    private BatchSetService batchSetService = new BatchSetServiceImpl();

    //converter
    private BatchSetToBatchSetDTOConverter batchSetToBatchSetDTOConverter = new BatchSetToBatchSetDTOConverter();

    @Transactional
    public Result getLastBatchResultSet() {

        BatchResultSet batchResultSet = batchSetService.findLast();

        return ok(batchSetToBatchSetDTOConverter.convert(batchResultSet));

    }

    @Transactional
    public Result getEffectiveReductionSummary() {
        BatchResultSet batchResultSet = batchSetService.findLast();

        for (BatchResultItem batchResultItem : batchResultSet.getPotentialBach().getResultItems()) {
            if (batchResultItem.getDay().equals(ReductionDay.DAY1) && batchResultItem.getPeriod().equals(Period.SECOND)) {
                return ok(new DoubleDTO(batchResultItem.getPowerReduction()));
            }
        }
        throw new MyRuntimeException(BusinessErrorType.DATA_NOT_FOUND);
    }

}
