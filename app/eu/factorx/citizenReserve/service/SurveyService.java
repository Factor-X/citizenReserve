package eu.factorx.citizenReserve.service;

import eu.factorx.citizenReserve.model.account.Account;
import eu.factorx.citizenReserve.model.survey.Survey;

import java.util.List;

public interface SurveyService {

    Survey saveSurvey(Survey survey);

    Survey getSurveyById(Long id);

    List<Survey> findSurveysByAccount(Account account);

}
