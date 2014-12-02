package eu.factorx.citizens.service;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.type.QuestionCode;

import java.util.List;

public interface SurveyService {

    Survey saveSurvey(Survey survey);

    Survey getSurveyById(Long id);

    Survey findValidSurveyByAccount(Account account);

    List<Survey> findSurveysByAccount(Account account);

    int countSurveys();

    List<Answer> findAnswersByQuestionCode(QuestionCode questionCode);

}
