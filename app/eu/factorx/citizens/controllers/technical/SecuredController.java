package eu.factorx.citizens.controllers.technical;

import eu.factorx.citizens.dto.technical.ExceptionsDTO;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.service.AccountService;
import eu.factorx.citizens.service.impl.AccountServiceImpl;
import eu.factorx.citizens.util.BusinessErrorType;
import play.db.jpa.Transactional;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by florian on 1/12/14.
 */
public class SecuredController extends Security.Authenticator {

    public static final String SESSION_IDENTIFIER_STORE = "email";

    private AccountService accountService = new AccountServiceImpl();

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized(new ExceptionsDTO(BusinessErrorType.NOT_CONNECTED.getMessageReference()));
    }

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get(SESSION_IDENTIFIER_STORE);
    }

    @Transactional(readOnly = true)
    public Account getCurrentUser() {
        return accountService.findByEmail(Http.Context.current().session().get(SESSION_IDENTIFIER_STORE));
    }

    public void storeIdentifier(Account account) {
        //if the login and the password are ok, refresh the session
        Http.Context.current().session().clear();
        Http.Context.current().session().put(SESSION_IDENTIFIER_STORE, account.getEmail());
    }

    public boolean isAuthenticated() {
        return getCurrentUser() != null;
    }
}
