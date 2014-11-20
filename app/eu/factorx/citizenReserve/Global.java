package eu.factorx.citizenReserve;

import eu.factorx.citizenReserve.dto.technical.ExceptionsDTO;
import play.GlobalSettings;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Results;
import play.mvc.SimpleResult;

/**
 * Created by florian on 18/11/14.
 */
public class Global  extends GlobalSettings {

    @Override
    public F.Promise<SimpleResult> onError(Http.RequestHeader request, Throwable t) {
        ExceptionsDTO exceptionsDTO = new ExceptionsDTO(t.getCause().getMessage());

        Logger.error("ERROR into global : " + exceptionsDTO.getMessage());

        return F.Promise.<SimpleResult>pure(Results.internalServerError(exceptionsDTO
        ));
    }
}
