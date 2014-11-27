package eu.factorx.citizens.service;

import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.ReductionDTO;
import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.service.impl.CalculationServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.List;

public class CalculationServiceTest {

	private CalculationService calculationService = new CalculationServiceImpl();

	@BeforeClass
	public static void setUp() {
		FakeApplication app = Helpers.fakeApplication();
		Helpers.start(app);
	}

    @Test
    public void testCalculatePotentialReduction() throws Exception {

		play.Logger.debug("Entering Calculate Potential Reduction test...");
        List<AnswerDTO> answersDTOs = buildSampleConsumerProfile();

        ReductionDTO potentialReduction = calculationService.calculatePotentialReduction(answersDTOs);

        Assert.assertEquals(new Double(375), potentialReduction.getFirstPeriodPowerReduction());
        Assert.assertEquals(new Double(1798), potentialReduction.getSecondPeriodPowerReduction());
        Assert.assertEquals(new Double(2678), potentialReduction.getThirdPeriodPowerReduction());

        Assert.assertEquals(new Double(1617), potentialReduction.getAveragePowerReduction());

        Assert.assertEquals(new Double(4.851), potentialReduction.getEnergyReduction());
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

	@Test
	public void testCalculateEffectiveReduction() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile();

		// Sortir
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "0"));

		// Programme et electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, true));

		//Chauffage et eau chaude
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, true));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "2"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3810, null, true));

		//Repas
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3710, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3711, null, "1"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3720, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3730, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3750, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3760, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3740, null, "1"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3741, null, "1"));


		ReductionDTO effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);

	}

	private List<AnswerDTO> buildSampleActionsConsumerProfile() {
		List<AnswerDTO> answersDTOs = new ArrayList<>();

		answersDTOs = buildSampleConsumerProfile();


		return answersDTOs;
	}

	/************************************** Private methods ***************************/


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

	private AnswerDTO buildAnswerDTO(QuestionCode questionCode, Period period, Boolean booleanValue) {
		AnswerDTO answerDTO = createAnswerDTO(questionCode, period);
		answerDTO.addBooleanValue(booleanValue);
		return answerDTO;
	}


}
