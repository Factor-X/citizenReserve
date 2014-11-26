package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.service.SurveyService;

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
