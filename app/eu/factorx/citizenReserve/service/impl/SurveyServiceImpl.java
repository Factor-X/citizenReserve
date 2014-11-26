package eu.factorx.citizenReserve.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizenReserve.model.account.Account;
import eu.factorx.citizenReserve.model.survey.Survey;
import eu.factorx.citizenReserve.service.SurveyService;

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
}
