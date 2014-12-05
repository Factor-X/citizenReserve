package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.converter.AnswerToAnswerDTOConverter;
import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.EffectiveReductionDTO;
import eu.factorx.citizens.dto.ReductionDTO;
import eu.factorx.citizens.model.batch.BatchResult;
import eu.factorx.citizens.model.batch.BatchResultSet;
import eu.factorx.citizens.model.batch.PowerReductionType;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.AnswerValue;
import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.technical.AbstractEntity;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.model.type.ReductionDay;
import eu.factorx.citizens.service.BatchService;
import eu.factorx.citizens.service.CalculationService;
import eu.factorx.citizens.service.SurveyService;
import play.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BatchServiceImpl implements BatchService {

    private SurveyService surveyService = new SurveyServiceImpl();
    private AnswerToAnswerDTOConverter answerToAnswerDTOConverter = new AnswerToAnswerDTOConverter();
    private CalculationService calculationService = new CalculationServiceImpl();

    @Override
    public void run() {

        BatchResultSet batchResultSet = new BatchResultSet();
        batchResultSet.setPotentialBach(calculateGlobalPotentialReduction());
        batchResultSet.setEffectiveBach(calculateGlobalEffectiveReduction());

        batchResultSet.save();
    }

    @Override
    public BatchResult findLastBatchResult() {
        return Ebean.find(BatchResult.class).where().eq(AbstractEntity.LAST_UPDATE_DATE, getLastBatchResultDate()).findUnique();
    }

    private Date getLastBatchResultDate() {
        return Ebean.createNamedQuery(Date.class, "select max(br.lastUpdateDate) from BatchResult br").findUnique();
    }

    private BatchResult calculateGlobalPotentialReduction() {
        double firstPeriodResult = 0;
        double secondPeriodResult = 0;
        double thirdPeriodResult = 0;

        int nbSurveys = 0;
        int nbErrors = 0;
        int nbParticipants=0;

        for (Survey survey : surveyService.findAllSurveys()) {

            List<AnswerDTO> surveyAnswersDTOs = new ArrayList<>();
            for (Answer answer : survey.getAnswers()) {
                surveyAnswersDTOs.add(answerToAnswerDTOConverter.convert(answer));
            }

            ReductionDTO reductionDTO;
            try {
                reductionDTO = calculationService.calculatePotentialReduction(surveyAnswersDTOs);



            } catch (Exception e) {
                e.printStackTrace();
                //Logger.error("Calculation of potential reduction fails for survey with id = {}. Exception message is: {}", survey.getId(), e.getLocalizedMessage());
                nbErrors++;
                continue;
            }

            firstPeriodResult += reductionDTO.getFirstPeriodPowerReduction();
            secondPeriodResult += reductionDTO.getSecondPeriodPowerReduction();
            thirdPeriodResult += reductionDTO.getThirdPeriodPowerReduction();

            for (AnswerValue answerValue : surveyService.findAnswersByQuestionCodeAndSurvey(QuestionCode.Q1300, survey).getAnswerValues()) {
                nbParticipants+=answerValue.getDoubleValue();
                break;
            }

            nbSurveys++;
        }

        BatchResult batchResult = new BatchResult(PowerReductionType.POTENTIAL, nbSurveys, nbErrors,nbParticipants);
        batchResult.addResultItem(null, Period.FIRST, firstPeriodResult);
        batchResult.addResultItem(null, Period.SECOND, secondPeriodResult);
        batchResult.addResultItem(null, Period.THIRD, thirdPeriodResult);
        return batchResult;
    }

    public BatchResult calculateGlobalEffectiveReduction() {

        double firstDayResult_p1 = 0;
        double firstDayResult_p2 = 0;
        double firstDayResult_p3 = 0;

        double secondDayResult_p1 = 0;
        double secondDayResult_p2 = 0;
        double secondDayResult_p3 = 0;


        double thirdDayResult_p1 = 0;
        double thirdDayResult_p2 = 0;
        double thirdDayResult_p3 = 0;
        double fourthDayResult_p1 = 0;
        double fourthDayResult_p2 = 0;
        double fourthDayResult_p3 = 0;

        int nbSurveys = 0;
        int nbErrors = 0;
        int nbParticipants=0;

        for (Survey survey : surveyService.findAllSurveys()) {

            List<AnswerDTO> surveyAnswersDTOs = new ArrayList<>();
            for (Answer answer : survey.getAnswers()) {
                surveyAnswersDTOs.add(answerToAnswerDTOConverter.convert(answer));
            }

			/****************************/
			/* add unselected actions to perform calculation */

			// Validate incoming DTO - TODO
			List<AnswerDTO> missingActions = new ArrayList<AnswerDTO>();
			try {
				missingActions = calculationService.validateActions(surveyAnswersDTOs);
			} catch (Exception e) {
				//throw new MyRuntimeException("This answerValue is not savable : " + answerValueDTO + " (from answer " + answerDTO + ")");
			}

			surveyAnswersDTOs.addAll(missingActions);


			/*****************************/

            List<ReductionDTO> reductionDTOs;
            try {
                reductionDTOs = calculationService.calculateEffectiveReduction(surveyAnswersDTOs);
            } catch (Exception e) {
                e.printStackTrace();
                //Logger.error("Calculation of effective reduction fails for survey with id = {}. Exception message is: {}", survey.getId(), e.getLocalizedMessage());
                nbErrors++;
                continue;
            }

            firstDayResult_p1 += reductionDTOs.get(0).getFirstPeriodPowerReduction();
            firstDayResult_p2 += reductionDTOs.get(0).getSecondPeriodPowerReduction();
            firstDayResult_p3 += reductionDTOs.get(0).getThirdPeriodPowerReduction();

            secondDayResult_p1 += reductionDTOs.get(1).getFirstPeriodPowerReduction();
            secondDayResult_p2 += reductionDTOs.get(1).getSecondPeriodPowerReduction();
            secondDayResult_p3 += reductionDTOs.get(1).getThirdPeriodPowerReduction();

            thirdDayResult_p1 += reductionDTOs.get(2).getFirstPeriodPowerReduction();
            thirdDayResult_p2 += reductionDTOs.get(2).getSecondPeriodPowerReduction();
            thirdDayResult_p3 += reductionDTOs.get(2).getThirdPeriodPowerReduction();

            fourthDayResult_p1 += reductionDTOs.get(3).getFirstPeriodPowerReduction();
            fourthDayResult_p2 += reductionDTOs.get(3).getSecondPeriodPowerReduction();
            fourthDayResult_p3 += reductionDTOs.get(3).getThirdPeriodPowerReduction();

            for (AnswerValue answerValue : surveyService.findAnswersByQuestionCodeAndSurvey(QuestionCode.Q1300, survey).getAnswerValues()) {
                nbParticipants+=answerValue.getDoubleValue();
                break;
            }
            nbSurveys++;
        }

        BatchResult batchResult = new BatchResult(PowerReductionType.EFFECTIVE, nbSurveys, nbErrors,nbParticipants);

        batchResult.addResultItem(ReductionDay.DAY1, Period.FIRST, firstDayResult_p1);
        batchResult.addResultItem(ReductionDay.DAY1, Period.SECOND, firstDayResult_p2);
        batchResult.addResultItem(ReductionDay.DAY1, Period.THIRD, firstDayResult_p3);

        batchResult.addResultItem(ReductionDay.DAY2, Period.FIRST, secondDayResult_p1);
        batchResult.addResultItem(ReductionDay.DAY2, Period.SECOND, secondDayResult_p2);
        batchResult.addResultItem(ReductionDay.DAY2, Period.THIRD, secondDayResult_p3);

        batchResult.addResultItem(ReductionDay.DAY3, Period.FIRST, thirdDayResult_p1);
        batchResult.addResultItem(ReductionDay.DAY3, Period.SECOND, thirdDayResult_p2);
        batchResult.addResultItem(ReductionDay.DAY3, Period.THIRD, thirdDayResult_p3);

        batchResult.addResultItem(ReductionDay.DAY4, Period.FIRST, fourthDayResult_p1);
        batchResult.addResultItem(ReductionDay.DAY4, Period.SECOND, fourthDayResult_p2);
        batchResult.addResultItem(ReductionDay.DAY4, Period.THIRD, fourthDayResult_p3);

        return batchResult;
    }
}
