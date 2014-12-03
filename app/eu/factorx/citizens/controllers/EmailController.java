package eu.factorx.citizens.controllers;

import eu.factorx.citizens.service.TranslationService;
import eu.factorx.citizens.service.VelocityGeneratorService;
import eu.factorx.citizens.service.impl.TranslationServiceImpl;
import eu.factorx.citizens.service.impl.VelocityGeneratorServiceImpl;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.email.EmailEnum;
import eu.factorx.citizens.util.email.EmailParams;
import eu.factorx.citizens.util.email.messages.EmailMessage;
import eu.factorx.citizens.util.email.service.EmailService;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import play.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by florian on 3/12/14.
 */
public class EmailController extends AbstractController {


    private final String hostname = Configuration.root().getString("citizens-reserve.hostname");
    private final static String VELOCITY_BASIC_EMAIL = "basicEmailStructure.vm";

    private final TranslationService translationService = new TranslationServiceImpl();
    private final VelocityGeneratorService velocityGeneratorService = new VelocityGeneratorServiceImpl();
    private final TranslationHelp translationHelp = new TranslationHelp(translationService);
    private final EmailService emailService;


    public EmailController() {

        try {
            emailService = new EmailService();
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyRuntimeException(BusinessErrorType.EMAIL_FATAL_ERROR);
        }
    }

    public void sendEmail(String to, EmailEnum emailEnum, HashMap<EmailParams, String> paramsMap) {

        //TODO
        String lang = "fr";

        //control params
        for (EmailParams emailParams : emailEnum.getExpectedParams()) {
            if (!paramsMap.containsKey(emailParams)) {
                throw new MyRuntimeException(BusinessErrorType.EMAIL_EMPTY_PARAMETER, emailParams.getName());
            }
        }

        String title = translationService.getTranslation(emailEnum.getSubjectKey(), lang);

        //build params
        String[] params = new String[emailEnum.getExpectedParams().length];

        for (EmailParams emailParams : emailEnum.getExpectedParams()) {
            params[emailParams.getOrder()] = paramsMap.get(emailParams);
        }

        //load velocity content
        Map<String, Object> values = new HashMap<>();
        values.put("hostname", hostname);
        values.put("contentKey", emailEnum.getContentKey());
        values.put("translationHelper", translationHelp);
        values.put("contentParams", params);

        String velocityContent = velocityGeneratorService.generate(VELOCITY_BASIC_EMAIL, values);

        EmailMessage emailMessage = new EmailMessage(to, title, velocityContent);
        emailService.send(emailMessage);
    }


}
