package eu.factorx.citizenReserve.controllers;

import eu.factorx.citizenReserve.converter.AccountToAccountDTOConverter;
import eu.factorx.citizenReserve.dto.AccountDTO;
import eu.factorx.citizenReserve.dto.technical.ListDTO;
import eu.factorx.citizenReserve.model.Account;
import eu.factorx.citizenReserve.service.AccountService;
import eu.factorx.citizenReserve.service.impl.AccountServiceImpl;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by florian on 20/11/14.
 */
public class AccountController extends Controller {

    //service
    private AccountService accountService = new AccountServiceImpl();
    //converter
    private AccountToAccountDTOConverter accountToAccountDTOConverter = new AccountToAccountDTOConverter();

    public Result get() {

        ListDTO<AccountDTO> result = new ListDTO<>();
        for (Account account : accountService.findAll()) {
            result.add(accountToAccountDTOConverter.convert(account));
        }

        return ok(result);
    }
}
