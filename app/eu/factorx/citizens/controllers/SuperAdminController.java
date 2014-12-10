package eu.factorx.citizens.controllers;

import com.typesafe.config.ConfigFactory;
import eu.factorx.citizens.controllers.technical.SecuredController;
import eu.factorx.citizens.converter.BatchResultToBatchResultDTOConverter;
import eu.factorx.citizens.converter.BatchSetToBatchSetDTOConverter;
import eu.factorx.citizens.dto.ListDTO;
import eu.factorx.citizens.dto.ResultDTO;
import eu.factorx.citizens.model.batch.BatchResult;
import eu.factorx.citizens.model.batch.BatchResultSet;
import eu.factorx.citizens.service.BatchService;
import eu.factorx.citizens.service.BatchSetService;
import eu.factorx.citizens.service.impl.BatchServiceImpl;
import eu.factorx.citizens.service.impl.BatchSetServiceImpl;
import eu.factorx.citizens.util.security.SecurityAnnotation;
import play.Configuration;
import play.api.Play;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

/**
 * Created by florian on 4/12/14.
 */
public class SuperAdminController extends AbstractController {

    //service
    private BatchSetService batchSetService= new BatchSetServiceImpl();

	//converter
    private BatchSetToBatchSetDTOConverter batchSetToBatchSetDTOConverter = new BatchSetToBatchSetDTOConverter();

    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isSuperAdmin = true)
    public Result getReductionValuesProgression() {

        List<BatchResultSet> batchToDisplayForSuperAdmin = batchSetService.findBatchToDisplayForSuperAdmin();

        ListDTO listDTO = new ListDTO();

        for (BatchResultSet batchResult : batchToDisplayForSuperAdmin) {
            listDTO.add(batchSetToBatchSetDTOConverter.convert(batchResult));

        }


        return ok(listDTO);
    }


}
