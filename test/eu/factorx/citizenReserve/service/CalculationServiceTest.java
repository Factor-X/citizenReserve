package eu.factorx.citizenReserve.service;

import eu.factorx.citizenReserve.dto.AnswerDTO;
import eu.factorx.citizenReserve.dto.ReductionDTO;
import eu.factorx.citizenReserve.model.survey.Period;
import eu.factorx.citizenReserve.model.survey.QuestionCode;
import eu.factorx.citizenReserve.service.impl.CalculationServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CalculationServiceTest {

    private CalculationService calculationService = new CalculationServiceImpl();

    @Test
    public void testCalculatePotentialReduction() throws Exception {

		play.Logger.debug("Entering test...");
        List<AnswerDTO> answersDTOs = buildSampleConsumerProfile();

        ReductionDTO potentialReduction = calculationService.calculatePotentialReduction(answersDTOs);

        Assert.assertEquals(new Double(375), potentialReduction.getFirstPeriodPowerReduction());
        Assert.assertEquals(new Double(1798), potentialReduction.getSecondPeriodPowerReduction());
        Assert.assertEquals(new Double(2678), potentialReduction.getThirdPeriodPowerReduction());

        Assert.assertEquals(new Double(1600), potentialReduction.getAveragePowerReduction());

        Assert.assertEquals(new Double(4.8), potentialReduction.getEnergyReduction());
    }

    private List<AnswerDTO> buildSampleConsumerProfile() {
        List<AnswerDTO> answersDTOs = new ArrayList<>();

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1300, null, 4d));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1400, null, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1500, null, "1"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1110, Period.FIRST, "1"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1110, Period.SECOND, "1"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1110, Period.THIRD, "2"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1120, Period.FIRST, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1120, Period.SECOND, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1120, Period.THIRD, "2"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1130, Period.FIRST, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1130, Period.SECOND, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1130, Period.THIRD, "0"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1600, null, 0));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1900, null, 0));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1210, null, "0"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1160, Period.FIRST, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1160, Period.SECOND, "2"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1160, Period.THIRD, "3"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1220, null, "1"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1230, null, "1"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1700, null, "3"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1750, null, "2"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1800, null, 15)); // TODO: 15 (Watt) is not a typical value for this question!!

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q2010, null, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q2020, null, "1"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q2030, null, "1"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q2040, null, "1"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1235, null, "0"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1140, Period.FIRST, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1140, Period.SECOND, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1140, Period.THIRD, "0"));

        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1150, Period.FIRST, "0"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1150, Period.SECOND, "1"));
        answersDTOs.add(buildAnswerDTO(QuestionCode.Q1150, Period.THIRD, "0"));
        return answersDTOs;
    }

    private AnswerDTO createAnswerDTO(QuestionCode questionCode, Period period) {
        String periodKey = null;
        if (period != null) {
            periodKey = period.name();
        }
        return new AnswerDTO(questionCode.name(), periodKey);
    }

    private AnswerDTO buildAnswerDTO(QuestionCode questionCode, Period period, double doubleValue) {
        AnswerDTO answerDTO = createAnswerDTO(questionCode, period);
        answerDTO.addDoubleValue(doubleValue);
        return answerDTO;
    }

    private AnswerDTO buildAnswerDTO(QuestionCode questionCode, Period period, String stringValue) {
        AnswerDTO answerDTO = createAnswerDTO(questionCode, period);
        answerDTO.addStringValue(stringValue);
        return answerDTO;
    }

}
