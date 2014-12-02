package eu.factorx.citizens.controllers;

import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.AnswerValueDTO;
import eu.factorx.citizens.dto.SurveyDTO;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import eu.factorx.citizens.util.exception.MyRuntimeException;

import java.util.Date;

/**
 * Created by florian on 27/11/14.
 */
public class SurveyController extends AbstractController {

    //service
    private SurveyService surveyService = new SurveyServiceImpl();

    /*package*/ void saveSurvey(SurveyDTO dto, Account account) {

        //delete the last survey
        Survey lastValidSurvey = surveyService.findValidSurveyByAccount(account);

        if(lastValidSurvey!=null){
            lastValidSurvey.setDeletionDate(new Date());

            surveyService.saveSurvey(lastValidSurvey);
        }

        Survey survey = new Survey(account);

        for (AnswerDTO answerDTO : dto.getAnswers()) {

            //create answer
            Answer answer = new Answer();
            answer.setPeriod(getPeriodByPeriodKey(answerDTO.getPeriodKey()));

            for (AnswerValueDTO answerValueDTO : answerDTO.getAnswerValues()) {
                if (answerValueDTO.getBooleanValue() != null) {
                    survey.addAnswer(getQuestionCodeByQuestionKey(answerDTO.getQuestionKey()), getPeriodByPeriodKey(answerDTO.getPeriodKey()), answerValueDTO.getBooleanValue());
                } else if (answerValueDTO.getDoubleValue() != null) {
                    survey.addAnswer(getQuestionCodeByQuestionKey(answerDTO.getQuestionKey()), getPeriodByPeriodKey(answerDTO.getPeriodKey()), answerValueDTO.getDoubleValue());
                } else if (answerValueDTO.getStringValue() != null) {
                    survey.addAnswer(getQuestionCodeByQuestionKey(answerDTO.getQuestionKey()), getPeriodByPeriodKey(answerDTO.getPeriodKey()), answerValueDTO.getStringValue());
                } else {
                    //throw new MyRuntimeException("This answerValue is not savable : " + answerValueDTO + " (from answer " + answerDTO + ")");
                }
            }

        }

        surveyService.saveSurvey(survey);
    }
}
