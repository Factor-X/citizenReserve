package eu.factorx.citizens.service;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.TxRunnable;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.type.AccountType;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import play.db.ebean.Transactional;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.List;

public class SurveyServiceTest {

    private static final QuestionCode QUESTION_CODE_TEST = QuestionCode.Q9999;

    private SurveyService surveyService = new SurveyServiceImpl();

    @BeforeClass
    public static void setUp() {
        FakeApplication app = Helpers.fakeApplication();
        Helpers.start(app);
    }

    @Test
    @Transactional
    public void testCRUD() throws Exception {
        Ebean.execute(new TxRunnable() {
            public void run() {
                Survey survey = new Survey();
                Account account = new Account(AccountType.HOUSEHOLD, "emailtest", "passwordTest", "firstNameTest", "lastNameTest", "zipCodeTest", "powerProviderTest");
                survey.setAccount(account);

                survey.addAnswer(QUESTION_CODE_TEST, null, 4d);
                surveyService.saveSurvey(survey);

                List<Survey> foundSurveys = surveyService.findSurveysByAccount(account);
                Assert.assertEquals(1, foundSurveys.size());

                Survey foundSurvey = foundSurveys.get(0);
                Assert.assertEquals(survey, foundSurvey);

                Long accountId = foundSurvey.getAccount().getId();
                Ebean.delete(foundSurvey);
                Ebean.delete(Account.class, accountId);
            }
        });
    }

    @Test
    public void testFindAnswersByQuestionCode() throws Exception {
        Ebean.execute(new TxRunnable() {
            public void run() {
                Survey survey = new Survey();
                Account account = new Account(AccountType.HOUSEHOLD, "emailtest", "passwordTest", "firstNameTest", "lastNameTest", "zipCodeTest", "powerProviderTest");
                survey.setAccount(account);

                survey.addAnswer(QUESTION_CODE_TEST, null, 4d);
                surveyService.saveSurvey(survey);

                List<Answer> answers = surveyService.findAnswersByQuestionCode(QUESTION_CODE_TEST);
                Assert.assertEquals(1, answers.size());

                survey.markAsDeleted();
                Ebean.update(survey);

                answers = surveyService.findAnswersByQuestionCode(QUESTION_CODE_TEST);
                Assert.assertEquals(0, answers.size());

                Long accountId = survey.getAccount().getId();
                Ebean.delete(survey);
                Ebean.delete(Account.class, accountId);
            }
        });
    }
}
