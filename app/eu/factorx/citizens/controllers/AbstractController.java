package eu.factorx.citizens.controllers;

import eu.factorx.citizens.dto.technical.DTO;
import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.type.AccountType;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.exception.MyRuntimeException;

import play.mvc.Controller;

import com.fasterxml.jackson.databind.JsonNode;


public abstract class AbstractController extends Controller {


	protected <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		JsonNode json = request().body().asJson();
		T dto = DTO.getDTO(json, DTOclass);
		if (dto == null) {
			throw new MyRuntimeException(BusinessErrorType.CONVERSION_DTO_ERROR, DTOclass.getName());
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


}
