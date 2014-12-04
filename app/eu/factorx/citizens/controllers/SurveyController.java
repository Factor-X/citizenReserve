package eu.factorx.citizens.controllers;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.AnswerValueDTO;
import eu.factorx.citizens.dto.SurveyDTO;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.batch.BatchResult;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.AnswerValue;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.service.BatchService;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import play.db.ebean.Transactional;
import play.mvc.Result;

import java.util.Iterator;
import java.util.List;

import java.util.Date;

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

    @Transactional
    public Result getParticipantsNumber() {
        int nbSurveys = surveyService.countSurveys();
        List<Answer> answers = surveyService.findAnswersByQuestionCode(QuestionCode.Q1300);
        int nbParticipants = 0;
        for (Answer answer : answers) {
            Iterator<AnswerValue> answerValueIterator = answer.getAnswerValues().iterator();
            if (answerValueIterator.hasNext()) {
                nbParticipants += answerValueIterator.next().getDoubleValue();
            }
        }
        return ok("{'nbSurveys':'" + nbSurveys + "','nbParticipants':'" + nbParticipants + "'}");
    }

    public Result getGlobalReductionData() {
        BatchResult batchResult = Ebean.find(Batch.cl)
    }

}
