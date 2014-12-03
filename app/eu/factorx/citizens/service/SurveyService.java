package eu.factorx.citizens.service;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.survey.TopicEnum;

import java.util.HashMap;
import java.util.List;

public interface SurveyService {

    Survey saveSurvey(Survey survey);

    Survey getSurveyById(Long id);

    Survey findValidSurveyByAccount(Account account);

    List<Survey> findSurveysByAccount(Account account);

    HashMap<TopicEnum, List<String>> getActions(Account account);

}
