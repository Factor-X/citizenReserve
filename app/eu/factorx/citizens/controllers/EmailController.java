package eu.factorx.citizens.controllers;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.account.LanguageEnum;
import eu.factorx.citizens.model.survey.TopicEnum;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.service.TranslationService;
import eu.factorx.citizens.service.VelocityGeneratorService;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import eu.factorx.citizens.service.impl.TranslationServiceImpl;
import eu.factorx.citizens.service.impl.VelocityGeneratorServiceImpl;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.email.EmailEnum;
import eu.factorx.citizens.util.email.EmailParams;
import eu.factorx.citizens.util.email.messages.EmailMessage;
import eu.factorx.citizens.util.email.service.EmailService;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import org.apache.commons.lang3.tuple.Pair;
import play.Configuration;
import play.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by florian on 3/12/14.
 */
public class EmailController extends AbstractController {


    private final String hostname = Configuration.root().getString("citizens-reserve.hostname");
	private final String citizensReserveHome = Configuration.root().getString("citizens-reserve.home");
    private final static String VELOCITY_BASIC_EMAIL = "basicEmailStructure.vm";
    private final static String VELOCITY_LIST_ACTION = "listAction.vm";

    private final TranslationService translationService = new TranslationServiceImpl();
    private final VelocityGeneratorService velocityGeneratorService = new VelocityGeneratorServiceImpl();
    private final EmailService emailService;
    private final SurveyService surveyService = new SurveyServiceImpl();


    public EmailController() {

        try {
            emailService = new EmailService();
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyRuntimeException(BusinessErrorType.EMAIL_FATAL_ERROR);
        }
    }

    public void sendEmail(String to, EmailEnum emailEnum, HashMap<EmailParams, String> paramsMap, LanguageEnum lang, String... emailsToCC) {

        final TranslationHelper translationHelper = new TranslationHelper(translationService, lang);

        //control params
        for (EmailParams emailParams : emailEnum.getExpectedParams()) {
            if (!paramsMap.containsKey(emailParams)) {
                throw new MyRuntimeException(BusinessErrorType.EMAIL_EMPTY_PARAMETER, emailParams.getName());
            }
        }

        String title = translationService.getTranslation(emailEnum.getSubjectKey(), lang.getAbrv());

        //build params
        String[] params = new String[emailEnum.getExpectedParams().length];

        for (EmailParams emailParams : emailEnum.getExpectedParams()) {
            params[emailParams.getOrder()] = paramsMap.get(emailParams);
        }

        //load velocity content
        Map<String, Object> values = new HashMap<>();
        values.put("hostname", hostname);
        values.put("contentKey", emailEnum.getContentKey());
        values.put("translationHelper", translationHelper);
        values.put("contentParams", params);
		values.put("citizensReserveHome", citizensReserveHome);

        String velocityContent = velocityGeneratorService.generate(VELOCITY_BASIC_EMAIL, values);

        EmailMessage emailMessage = new EmailMessage(to, title, velocityContent);
        emailMessage.setEmailsToCC(emailsToCC);
        emailService.send(emailMessage);
    }

    public String generateActionsTable(Account account) {


        final TranslationHelper translationHelper = new TranslationHelper(translationService, account.getLanguage());

        HashMap<TopicEnum, List<Pair<String,String>>> actions = surveyService.getActionsForSummaryEmail(account);

        Map<String, Object> values = new HashMap<>();
        values.put("actions", actions);
        values.put("translationHelper", translationHelper);
        values.put("hostname", hostname);

        return velocityGeneratorService.generate(VELOCITY_LIST_ACTION, values);
    }


}
