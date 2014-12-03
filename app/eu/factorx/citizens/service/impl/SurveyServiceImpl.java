package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.AnswerValue;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.technical.AbstractEntity;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.model.survey.TopicEnum;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.util.exception.MyRuntimeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SurveyServiceImpl implements SurveyService {

    @Override
    public Survey saveSurvey(Survey survey) {
        Ebean.save(survey);
        return survey;
    }

    @Override
    public Survey getSurveyById(Long id) {
        return Ebean.find(Survey.class, id);
    }

    @Override
    public List<Survey> findSurveysByAccount(Account account) {
        return Ebean.find(Survey.class)
                .where().eq("account", account)
                .findList();
    }

    public Survey findValidSurveyByAccount(Account account) {
        List<Survey> list = Ebean.createNamedQuery(Survey.class, Survey.FIND_VALID_BY_ACCOUNT)
                .setParameter(Survey.COL_ACCOUNT, account.getId())
                .findList();

        if (list.size() > 1) {

            String ids = "";

            for (Survey survey : list) {
                ids += survey.getId() + ";";
            }
            throw new MyRuntimeException("More than one survey not deleted : " + ids);
        }
        if (list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public int countSurveys() {
        return Ebean.find(Survey.class).where().isNull(AbstractEntity.DELETION_DATE).findRowCount();
    }

    @Override
    public List<Answer> findAnswersByQuestionCode(QuestionCode questionCode) {
        return Ebean.createNamedQuery(Answer.class, Answer.FIND_BY_QUESTION_CODE)
                .setParameter(Answer.QUESTION_CODE, questionCode)
                .findList();
    }


    /**
     * @return a map :
     * key: the name of the topic
     * value : a list of the question name reference
     */
    @Override
    public HashMap<TopicEnum, List<String>> getActions(Account account) {

        HashMap<TopicEnum, List<String>> result = new HashMap<>();

        //load my survey
        Survey survey = findValidSurveyByAccount(account);

        if (survey == null) {
            return result;
        }

        //loading by topic
        for (Answer answer : survey.getAnswers()) {
            for (TopicEnum topicEnum : TopicEnum.values()) {
                for (String questionCode : topicEnum.getQuestionCodeList()) {
                    if (answer.getQuestionCode().name().equals(questionCode)) {

                        if (!result.containsKey(answer)) {
                            result.put(topicEnum, new ArrayList<String>());
                        }
                        if (answer.getAnswerValues().size() > 0) {
                            for (AnswerValue answerValue : answer.getAnswerValues()) {

                                //if the answer is boolean, expect true
                                if(answerValue.getBooleanValue()!=null && answerValue.getBooleanValue()){
                                    result.get(topicEnum).add(questionCode);
                                }
                                //if the answer is a number, expect more than 0
                                else if(answerValue.getDoubleValue()!=null && answerValue.getDoubleValue()>0){
                                    result.get(topicEnum).add(questionCode);
                                }
                                //stop after 1 loop => only on answerValue by answer
                                break;
                            }
                        }
                    }
                }

            }

        }
        return result;
    }
}
