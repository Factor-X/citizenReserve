package eu.factorx.citizens.controllers;

import eu.factorx.citizens.converter.AccountToAccountDTOConverter;
import eu.factorx.citizens.dto.AccountDTO;
import eu.factorx.citizens.dto.SummaryDTO;
import eu.factorx.citizens.dto.SurveyDTO;
import eu.factorx.citizens.dto.technical.ListDTO;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.service.AccountService;
import eu.factorx.citizens.service.impl.AccountServiceImpl;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Result;

/**
 * Created by florian on 20/11/14.
 */
public class AccountController extends AbstractController {

	//service
	private AccountService accountService = new AccountServiceImpl();
	//converter
	private AccountToAccountDTOConverter accountToAccountDTOConverter = new AccountToAccountDTOConverter();
    //controller
    private SurveyController surveyController = new SurveyController();

	public Result get() {

		ListDTO<AccountDTO> result = new ListDTO<>();
		for (Account account : accountService.findAll()) {
			result.add(accountToAccountDTOConverter.convert(account));
		}

		return ok(result);
	}

    public Result createAccountAndSaveData(){

        SurveyDTO dto = extractDTOFromRequest(SurveyDTO.class);

        //control email
        Account account = accountService.findByEmail(dto.getAccount().getEmail());

        if(account!=null){
            throw new MyRuntimeException(BusinessErrorType.EMAIL_ALREADY_USED, account.getEmail());
        }

        //create account
        account = new Account();

        //current data
        account.setEmail(dto.getAccount().getEmail());
        account.setFirstName(dto.getAccount().getFirstName());
        account.setLastName(dto.getAccount().getLastName());
        account.setPassword(dto.getAccount().getPassword());
        account.setZipCode(dto.getAccount().getZipCode());
        account.setAccountType(getAccountTypeByString(dto.getAccount().getAccoutType()));
        account.setOtherEmailAdresses(StringUtils.join(dto.getAccount().getOtherEmailAddresses(),";"));

        //power data
        account.setPowerComsumerCategory(dto.getAccount().getPowerComsumerCategory());
        account.setPowerProvider(dto.getAccount().getPowerProvider());
        account.setSensitizationKit(dto.getAccount().getSensitizationKit());

        //save
        accountService.saveAccount(account);

        //save data
        surveyController.saveSurvey(dto,account);


        //TODO send email


        //TODO return summary
        return ok(new SummaryDTO());

    }
}
