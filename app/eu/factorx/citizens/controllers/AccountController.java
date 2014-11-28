package eu.factorx.citizens.controllers;

import eu.factorx.citizens.converter.AccountToAccountDTOConverter;
import eu.factorx.citizens.converter.SurveyToSurveyDTOConverter;
import eu.factorx.citizens.dto.AccountDTO;
import eu.factorx.citizens.dto.LoginDTO;
import eu.factorx.citizens.dto.SummaryDTO;
import eu.factorx.citizens.dto.SurveyDTO;
import eu.factorx.citizens.dto.technical.ListDTO;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.service.AccountService;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.service.impl.AccountServiceImpl;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.db.ebean.Transactional;
import play.mvc.Result;

/**
 * Created by florian on 20/11/14.
 */
public class AccountController extends AbstractController {

	//service
	private AccountService accountService = new AccountServiceImpl();
    private SurveyService surveyService = new SurveyServiceImpl();
	//converter
	private AccountToAccountDTOConverter accountToAccountDTOConverter = new AccountToAccountDTOConverter();
    private SurveyToSurveyDTOConverter surveyToSurveyDTOConverter =new SurveyToSurveyDTOConverter();
    //controller
    private SurveyController surveyController = new SurveyController();

    @Transactional
	public Result login() {
        LoginDTO loginDTO  = extractDTOFromRequest(LoginDTO.class);

        //login
        Account account = accountService.findByEmail(loginDTO.getEmail());

        if(account==null || !account.getPassword().equals(loginDTO.getPassword())){
            throw new MyRuntimeException(BusinessErrorType.WRONG_CREDENTIALS);
        }

        //build and return result
        Survey survey = surveyService.findValidSurveyByAccount(account);

        if(survey == null){
            throw new MyRuntimeException("there is no not deleted survey for account "+account.getId());
        }

        //build dto
        return ok(surveyToSurveyDTOConverter.convert(survey));
    }

    @Transactional
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
