package eu.factorx.citizens.controllers.technical;

import eu.factorx.citizens.dto.technical.DTO;
import eu.factorx.citizens.dto.technical.verification.NotNull;
import eu.factorx.citizens.dto.technical.verification.Pattern;
import eu.factorx.citizens.dto.technical.verification.Size;
import eu.factorx.citizens.model.account.LanguageEnum;
import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.type.AccountType;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.service.TranslationService;
import eu.factorx.citizens.service.impl.TranslationServiceImpl;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.exception.MyRuntimeException;

import play.mvc.Controller;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public abstract class AbstractController extends Controller {

    //controller
    protected SecuredController securedController = new SecuredController();
    protected TranslationService translationService = new TranslationServiceImpl();


	protected <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		JsonNode json = request().body().asJson();
		T dto = DTO.getDTO(json, DTOclass);
		if (dto == null) {
			throw new MyRuntimeException(BusinessErrorType.CONVERSION_DTO_ERROR, DTOclass.getName());
		}

        //control dto
        try {
            validation(DTOclass, dto, securedController.getCurrentUser().getLanguage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyRuntimeException(BusinessErrorType.FATAL_ERROR, e.getMessage());
        }

		return dto;
	}

    public AccountType getAccountTypeByString(String stringExpected){
        for(AccountType accountType : AccountType.values()){
            if(accountType.getString().equals(stringExpected)){
                return  accountType;
            }
        }
        throw new MyRuntimeException("Wrong account type string : "+stringExpected);
    }

    public Period getPeriodByPeriodKey(String periodKey){
        for (Period period : Period.values()) {
            if(period.name().equals(periodKey)){
                return period;
            }
        }
        return null;
    }

   public QuestionCode getQuestionCodeByQuestionKey(String questionKey){
       for (QuestionCode questionCode : QuestionCode.values()) {
           if(questionCode.name().equals(questionKey)){
               return questionCode;
           }
       }
       throw new MyRuntimeException("The questionKey : "+questionKey+" is unknown");
   }

    public String getIpAddress(){
        return request().remoteAddress();
    }

    private <T extends DTO> void validation(Class<T> DTOclass, T dto, LanguageEnum language) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String errorMessage = "";

        for (Field field : DTOclass.getDeclaredFields()) {

            Object v = dto.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(dto);

            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof NotNull) {

                    if (v == null) {
                        //build error message
                        errorMessage += translationService.getTranslation(((NotNull) annotation).message(), language) + "\n";
                    }
                } else if (annotation instanceof Pattern) {
                    if (!field.getType().equals(String.class)) {
                        throw new MyRuntimeException(BusinessErrorType.DTO_VERIFICATION_PATTERN_STRING_EXPECTED, field.getName(), field.getType().getName());
                    }
                    String string;

                    if (v == null) {
                        string = "";
                    } else {
                        string = ((String) v);
                    }

                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(((Pattern) annotation).regexp());

                    if (!pattern.matcher(string).find()) {

                        //build error message
                        errorMessage += translationService.getTranslation(((Pattern) annotation).message(), language,field.getName(), ((Pattern) annotation).regexp()) + "\n";
                    }

                } else if (annotation instanceof Size) {

                    int min = ((Size) annotation).min();
                    int max = ((Size) annotation).max();

                    if (!field.getType().equals(String.class)) {
                        throw new MyRuntimeException(BusinessErrorType.DTO_VERIFICATION_PATTERN_STRING_EXPECTED, field.getName(), field.getType().getName());
                    }
                    String string;

                    if (v == null) {
                        string = "";
                    }
                    else{
                        string = ((String) v);
                    }

                    if (string.length() > max || string.length() < min) {

                        //build error message
                        errorMessage += translationService.getTranslation(((Size) annotation).message(), language, field.getName(),min+"", max+"") + "\n";
                    }

                }
            }

        }
        if (errorMessage.length() > 0) {
            throw new MyRuntimeException(errorMessage);
        }


    }


}
