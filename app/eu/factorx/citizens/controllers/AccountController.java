package eu.factorx.citizens.controllers;

import eu.factorx.citizens.controllers.technical.SecuredController;
import eu.factorx.citizens.converter.AccountToAccountDTOConverter;
import eu.factorx.citizens.converter.SurveyToSurveyDTOConverter;
import eu.factorx.citizens.dto.*;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.service.AccountService;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.service.impl.AccountServiceImpl;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.KeyGenerator;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.db.ebean.Transactional;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by florian on 20/11/14.
 */
public class AccountController extends AbstractController {

    //service
    private AccountService accountService = new AccountServiceImpl();
    private SurveyService surveyService = new SurveyServiceImpl();
    //converter
    private AccountToAccountDTOConverter accountToAccountDTOConverter = new AccountToAccountDTOConverter();
    private SurveyToSurveyDTOConverter surveyToSurveyDTOConverter = new SurveyToSurveyDTOConverter();
    //controller
    private SurveyController surveyController = new SurveyController();
    private SecuredController securedController = new SecuredController();



    /**
     * return survey if the user is connected
     *
     * @return
     */
    @Transactional
    @Security.Authenticated(SecuredController.class)
    public Result testAuthentication() {

        Survey survey = surveyService.findValidSurveyByAccount(securedController.getCurrentUser());

        if (survey == null) {
            throw new MyRuntimeException("there is no not deleted survey for account " + securedController.getCurrentUser().getId());
        }

        //build dto
        return ok(surveyToSurveyDTOConverter.convert(survey));
    }

    @Transactional
    public Result login() {
        LoginDTO loginDTO = extractDTOFromRequest(LoginDTO.class);

        //login
        Account account = accountService.findByEmail(loginDTO.getEmail());

        if (account == null || !accountService.controlPassword(loginDTO.getPassword(), account)) {
            throw new MyRuntimeException(BusinessErrorType.WRONG_CREDENTIALS);
        }

        //build and return result
        Survey survey = surveyService.findValidSurveyByAccount(account);

        if (survey == null) {
            throw new MyRuntimeException("there is no not deleted survey for account " + account.getId());
        }

        //save account into context
        securedController.storeIdentifier(account);

        //build dto
        return ok(surveyToSurveyDTOConverter.convert(survey));
    }


    @Transactional
    @Security.Authenticated(SecuredController.class)
    public Result logout() {
        session().clear();
        return ok(new ResultDTO());
    }

    @Transactional
    public Result forgotPassword(){

        ForgotPasswordDTO dto = extractDTOFromRequest(ForgotPasswordDTO.class);


        //load account by email
        Account account = accountService.findByEmail(dto.getEmail());

        if(account==null){
            throw new MyRuntimeException(BusinessErrorType.EMAIL_DOESNT_EXIT);
        }

        //change password
        String password = KeyGenerator.generateRandomPassword(12);

        account.setPassword(password);

        //send email
        /* TODO
        Map<String, Object> values = new HashMap<>();
        values.put("request", calculatorInstance.getVerificationRequest());
        values.put("user", securedController.getCurrentUser());

        String velocityContent = velocityGeneratorService.generate("verification/" + emailToSend, values);

        EmailMessage email = new EmailMessage(emailTargets, emailTitle, velocityContent);
        emailService.send(email);
        */

        return ok(new ResultDTO());
    }

    @Transactional
    public Result createAccountAndSaveData() {

        SurveyDTO dto = extractDTOFromRequest(SurveyDTO.class);

        if(dto.getAccount().getId()!=null){
            return updateAccountAndSaveData();
        }

        //test if the account is already create
        Account account = null;

        //create new account
        //control email
        account = accountService.findByEmail(dto.getAccount().getEmail());

        if (account != null) {
            throw new MyRuntimeException(BusinessErrorType.EMAIL_ALREADY_USED, account.getEmail());
        }

        account = new Account();

        //build account
        account.setPassword(dto.getAccount().getPassword());
        account.setEmail(dto.getAccount().getEmail());
        account.setFirstName(dto.getAccount().getFirstName());
        account.setLastName(dto.getAccount().getLastName());
        account.setZipCode(dto.getAccount().getZipCode());
        account.setAccountType(getAccountTypeByString(dto.getAccount().getAccountType()));
        account.setOtherEmailAdresses(StringUtils.join(dto.getAccount().getOtherEmailAddresses(), ";"));

        //power data
        account.setPowerComsumerCategory(dto.getAccount().getPowerComsumerCategory());
        account.setPowerProvider(dto.getAccount().getPowerProvider());
        account.setSensitizationKit(dto.getAccount().getSensitizationKit());

        //save
        accountService.saveOrUpdate(account);

        //save data
        surveyController.saveSurvey(dto, account);


        //TODO send email

        //save account into context
        securedController.storeIdentifier(account);

        //TODO return summary
        return ok(new SummaryDTO());

    }

    private Result updateAccountAndSaveData() {

        if(!securedController.isAuthenticated()){
            return securedController.onUnauthorized(ctx());
        }

        SurveyDTO dto = extractDTOFromRequest(SurveyDTO.class);

        //load user
        Account account = securedController.getCurrentUser();

        //current data
        account.setFirstName(dto.getAccount().getFirstName());
        account.setLastName(dto.getAccount().getLastName());
        account.setZipCode(dto.getAccount().getZipCode());
        account.setAccountType(getAccountTypeByString(dto.getAccount().getAccountType()));
        account.setOtherEmailAdresses(StringUtils.join(dto.getAccount().getOtherEmailAddresses(), ";"));

        //power data
        account.setPowerComsumerCategory(dto.getAccount().getPowerComsumerCategory());
        account.setPowerProvider(dto.getAccount().getPowerProvider());
        account.setSensitizationKit(dto.getAccount().getSensitizationKit());

        //save
        accountService.saveOrUpdate(account);

        //save data
        surveyController.saveSurvey(dto, account);

        //TODO return summary
        return ok(new SummaryDTO());

    }

}
