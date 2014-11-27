package eu.factorx.citizens.service;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Survey;

import java.util.List;

public interface SurveyService {

	Survey saveSurvey(Survey survey);

	Survey getSurveyById(Long id);

	List<Survey> findSurveysByAccount(Account account);

}
