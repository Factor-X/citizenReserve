package eu.factorx.citizens.controllers;

import eu.factorx.citizens.controllers.technical.AbstractController;
import eu.factorx.citizens.converter.BatchSetToBatchSetDTOConverter;
import eu.factorx.citizens.dto.SummaryResultDTO;
import eu.factorx.citizens.model.batch.BatchResultItem;
import eu.factorx.citizens.model.batch.BatchResultSet;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.AnswerValue;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.model.type.ReductionDay;
import eu.factorx.citizens.service.BatchSetService;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.service.impl.BatchSetServiceImpl;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import play.db.ebean.Transactional;
import play.mvc.Result;

import java.util.Iterator;
import java.util.List;

/**
 * Created by florian on 5/12/14.
 */
public class BatchResultController extends AbstractController {

	public static final int OLD_VERSION_NB_SURVEYS = 307;
	public static final int OLD_VERSION_NB_PARTICIPANTS = 935;
	public static final int OLD_VERSION_REDUCTION = 965525;

    //service
    private BatchSetService batchSetService = new BatchSetServiceImpl();
    private SurveyService surveyService = new SurveyServiceImpl();

    //converter
    private BatchSetToBatchSetDTOConverter batchSetToBatchSetDTOConverter = new BatchSetToBatchSetDTOConverter();

    @Transactional
    public Result getLastBatchResultSet() {

        BatchResultSet batchResultSet = batchSetService.findLast();

        return ok(batchSetToBatchSetDTOConverter.convert(batchResultSet));

    }

    @Transactional
    public Result getSummaryResults() {

        int nbSurveys = surveyService.countSurveys();
        List<Answer> answers = surveyService.findAnswersByQuestionCode(QuestionCode.Q1300);

        BatchResultSet batchResultSet = batchSetService.findLast();

        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        response().setHeader("Access-Control-Max-Age", "3600");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Auth-Token");
        response().setHeader("Access-Control-Allow-Credentials", "true");

        Integer nbSurvey = surveyService.countSurveys();

        double total = 0.0;
        double nbValues = 0;
        for (BatchResultItem batchResultItem : batchResultSet.getEffectiveBach().getResultItems()) {
            if (batchResultItem.getDay().equals(ReductionDay.DAY1)) {
                total += batchResultItem.getPowerReduction();
                nbValues++;
            }
        }

        double reduction = total / nbValues;

        int nbParticipants = 0;
        for (Answer answer : answers) {
            Iterator<AnswerValue> answerValueIterator = answer.getAnswerValues().iterator();
            if (answerValueIterator.hasNext()) {
                nbParticipants += answerValueIterator.next().getDoubleValue();
            }
        }

		// TODO: Incorporate old version accounts
        return ok(new SummaryResultDTO(OLD_VERSION_NB_SURVEYS + nbSurvey, OLD_VERSION_NB_PARTICIPANTS + nbParticipants, OLD_VERSION_REDUCTION + reduction));
    }

}
