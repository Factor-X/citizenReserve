package eu.factorx.citizenReserve.service;

import com.avaje.ebean.Ebean;
import eu.factorx.citizenReserve.model.account.Account;
import eu.factorx.citizenReserve.model.account.AccountType;
import eu.factorx.citizenReserve.model.survey.Answer;
import eu.factorx.citizenReserve.model.survey.QuestionCode;
import eu.factorx.citizenReserve.model.survey.Survey;
import eu.factorx.citizenReserve.service.impl.SurveyServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.List;

public class SurveyServiceTest {

    private SurveyService surveyService = new SurveyServiceImpl();

    @BeforeClass
    public static void setUp() {
        FakeApplication app = Helpers.fakeApplication();
        Helpers.start(app);
    }

    @Test
    public void testSaveSurvey() throws Exception {
        Survey survey = new Survey();
        Account account = new Account(AccountType.HOUSEHOLD, "emailTest", "passwordTest", "firstNameTest", "lastNameTest", "zipCodeTest", "powerProviderTest");

        survey.setAccount(account);
        Answer answer = new Answer(survey, QuestionCode.Q1300);
        answer.addDoubleValue(4d);
        survey.addAnswer(answer);
        surveyService.saveSurvey(survey);

        List<Survey> foundSurveys = surveyService.findSurveysByAccount(account);
        Assert.assertEquals(1, foundSurveys.size());

        Survey foundSurvey = foundSurveys.get(0);
        Assert.assertEquals(survey, foundSurvey);

        Ebean.delete(foundSurvey);
    }

}
