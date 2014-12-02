package eu.factorx.citizens.service;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.type.AccountType;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
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
    public void testCRUD() throws Exception {
        Survey survey = new Survey();
        Account account = new Account(AccountType.HOUSEHOLD, "emailTest", "passwordTest", "firstNameTest", "lastNameTest", "zipCodeTest", "powerProviderTest");
        survey.setAccount(account);

        QuestionCode questionCode = QuestionCode.Q1300;
        double value = 4d;
        survey.addAnswer(QuestionCode.Q1300, null, 4d);
        surveyService.saveSurvey(survey);

        List<Survey> foundSurveys = surveyService.findSurveysByAccount(account);
        Assert.assertEquals(1, foundSurveys.size());

        Survey foundSurvey = foundSurveys.get(0);
        Assert.assertEquals(survey, foundSurvey);

        Ebean.delete(foundSurvey);
    }

    @Test
    public void testFindAnswersByQuestionCode() throws Exception {
        Survey survey = new Survey();
        Account account = new Account(AccountType.HOUSEHOLD, "emailTest", "passwordTest", "firstNameTest", "lastNameTest", "zipCodeTest", "powerProviderTest");
        survey.setAccount(account);

        survey.addAnswer(QuestionCode.Q1300, null, 4d);
        surveyService.saveSurvey(survey);

        List<Answer> answers = surveyService.findAnswersByQuestionCode(QuestionCode.Q1300);
        Assert.assertEquals(1, answers.size());

        survey.markAsDeleted();
        surveyService.saveSurvey();



    }
}
