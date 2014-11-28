package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.util.exception.MyRuntimeException;

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
}
