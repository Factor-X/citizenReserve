package eu.factorx.citizens.service;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.survey.TopicEnum;
import eu.factorx.citizens.model.type.QuestionCode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public interface SurveyService {

    Survey saveSurvey(Survey survey);

    Survey getSurveyById(Long id);

    Survey findValidSurveyByAccount(Account account);

    List<Survey> findSurveysByAccount(Account account);

	List<Survey> findDeletedSurveysByAccountLastDeletedFirst(Account account);

    List<Survey> findAllSurveys();

    int countSurveys();

    List<Answer> findAnswersByQuestionCode(QuestionCode questionCode);

    HashMap<TopicEnum, List<Pair<String,String>>> getActionsForSummaryEmail(Account account);

    Answer findAnswersByQuestionCodeAndSurvey(QuestionCode questionCode, Survey survey);
}
