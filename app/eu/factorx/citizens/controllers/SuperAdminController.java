package eu.factorx.citizens.controllers;

import eu.factorx.citizens.controllers.technical.SecuredController;
import eu.factorx.citizens.converter.BatchResultToBatchResultDTOConverter;
import eu.factorx.citizens.dto.ListDTO;
import eu.factorx.citizens.dto.ResultDTO;
import eu.factorx.citizens.model.batch.BatchResult;
import eu.factorx.citizens.service.BatchService;
import eu.factorx.citizens.service.impl.BatchServiceImpl;
import eu.factorx.citizens.util.security.SecurityAnnotation;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

/**
 * Created by florian on 4/12/14.
 */
public class SuperAdminController extends AbstractController {

    //service
    private BatchService batchService = new BatchServiceImpl();

    //converter
    private BatchResultToBatchResultDTOConverter batchResultToBatchResultDTOConverter = new BatchResultToBatchResultDTOConverter();

    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isSuperAdmin = true)
    public Result getReductionValuesProgression() {

        List<BatchResult> batchToDisplayForSuperAdmin = batchService.findBatchToDisplayForSuperAdmin();

        ListDTO listDTO = new ListDTO();

        for (BatchResult batchResult : batchToDisplayForSuperAdmin) {
            listDTO.add(batchResultToBatchResultDTOConverter.convert(batchResult));

        }


        return ok(listDTO);
    }


}
