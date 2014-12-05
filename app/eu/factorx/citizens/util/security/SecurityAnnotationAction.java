package eu.factorx.citizens.util.security;

import eu.factorx.citizens.controllers.technical.SecuredController;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import play.Logger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;

/**
 * Created by florian on 4/12/14.
 */
public class SecurityAnnotationAction extends Action<SecurityAnnotation> {

    private SecuredController securedController = new SecuredController();

    @Override
    public F.Promise<SimpleResult> call(Http.Context context) throws Throwable {
        Logger.error("securedController.getCurrentUser() : "+securedController.getCurrentUser());
        if(securedController.getCurrentUser()!=null) {
            Logger.error(configuration.isSuperAdmin()+" / "+securedController.getCurrentUser().isSuperAdmin());
            if (configuration.isSuperAdmin() && securedController.getCurrentUser().isSuperAdmin()){
                return delegate.call(context);
            }
        }
        throw new MyRuntimeException(BusinessErrorType.WRONG_RIGHT);
    }
}
