package eu.factorx.citizens.service;

import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.ReductionDTO;
import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.model.type.ReductionDay;
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
        List<AnswerDTO> answersDTOs = buildSampleConsumerProfile001();

        ReductionDTO potentialReduction = calculationService.calculatePotentialReduction(answersDTOs);

        Assert.assertEquals(new Double(375), potentialReduction.getFirstPeriodPowerReduction());
        Assert.assertEquals(new Double(1798), potentialReduction.getSecondPeriodPowerReduction());
        Assert.assertEquals(new Double(2678), potentialReduction.getThirdPeriodPowerReduction());

        Assert.assertEquals(new Double(1617), potentialReduction.getAveragePowerReduction());

        Assert.assertEquals(new Double(4.851), potentialReduction.getEnergyReduction());
    }

    private List<AnswerDTO> buildSampleConsumerProfile001() {
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
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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


		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);

	}


	@Test
	public void testCalculateEffectiveReductionCase001() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 001...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

		// Sortir
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "1"));

		// Programme et electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, false));

		//Chauffage et eau chaude
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());



	}


	@Test
	public void testCalculateEffectiveReductionCase002() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 002...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

		// Sortir
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "2"));

		// Programme et electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, false));

		//Chauffage et eau chaude
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	@Test
	public void testCalculateEffectiveReductionCase003() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 003...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

		// Sortir
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "3"));

		// Programme et electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, false));

		//Chauffage et eau chaude
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(0), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	@Test
	public void testCalculateEffectiveReductionCase004() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 004...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

		// Sortir
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "4"));

		// Programme et electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, false));

		//Chauffage et eau chaude
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(375), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1798), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2678), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1617), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.851), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}


	@Test
	public void testCalculateEffectiveReductionCase005() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 005...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

		// Sortir
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "0"));

		// Programme et electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, false));

		//Chauffage et eau chaude
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(520), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(520), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(520), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(520), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(346.6666666666667), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.04), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(346.6666666666667), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.04), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(346.6666666666667), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.04), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(346.6666666666667), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.04), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	@Test
	public void testCalculateEffectiveReductionCase006() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 006...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

		// Sortir
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "0"));

		// Programme et electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, false));

		//Chauffage et eau chaude
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	@Test
	public void testCalculateEffectiveReductionCase007() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 007...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

		// Sortir
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "0"));

		// Programme et electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, true));

		//Chauffage et eau chaude
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	@Test
	public void testCalculateEffectiveReductionCase008() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 008...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

		// Sortir
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "0"));

		// Programme et electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, true));

		//Chauffage et eau chaude
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, true));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	@Test
	public void testCalculateEffectiveReductionCase009() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 009...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));

		//Eclairage & electromenager
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	@Test
	public void testCalculateEffectiveReductionCase010() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 010...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	@Test
	public void testCalculateEffectiveReductionCase011() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 010...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));



		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1320), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(613.3333333333334), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(1.84), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	// 3510
	@Test
	public void testCalculateEffectiveReductionCase012() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 010...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(510), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1570), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(510), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1570), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(510), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1570), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(260), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(510), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1570), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(780), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.34), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(780), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.34), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(780), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.34), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(780), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.34), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	// 3530
	@Test
	public void testCalculateEffectiveReductionCase013() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 010...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(525), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1585), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(525), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1585), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(525), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1585), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(525), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1585), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(795), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.385), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(795), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.385), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(795), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.385), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(795), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.385), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	// 3610
	@Test
	public void testCalculateEffectiveReductionCase014() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 014...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));




		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(525), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1585), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(525), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1585), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(525), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1585), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(525), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1585), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(795), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.385), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(795), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.385), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(795), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.385), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(795), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.385), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	// 3620
	@Test
	public void testCalculateEffectiveReductionCase015() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 015...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));


		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);


		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(575), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1635), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(575), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1635), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(575), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1635), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(575), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1635), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(828.3333333333334), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.485), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(828.3333333333334), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.485), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(828.3333333333334), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.485), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(828.3333333333334), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.485), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	// 3630
	@Test
	public void testCalculateEffectiveReductionCase016() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 016...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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
		answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));

		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(683), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1743), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(683), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1743), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(575), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1635), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(575), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(1635), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(900.3333333333334), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.701), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(900.3333333333334), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.701), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(828.3333333333334), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.485), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(828.3333333333334), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(2.485), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}

	// 3640
	@Test
	public void testCalculateEffectiveReductionCase017() throws Exception {

		play.Logger.debug("Entering Calculate Effective Reduction test Case 017...");
		List<AnswerDTO> answersDTOs = buildSampleActionsConsumerProfile001();

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

		List<ReductionDTO> effectiveReduction = calculationService.calculateEffectiveReduction(answersDTOs);

//		play.Logger.debug(" DAY1 :" + effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
//		play.Logger.debug(" DAY1 :" + effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
//		play.Logger.debug(" DAY1 :" + effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());
//
//		play.Logger.debug(" DAY2 :" + effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
//		play.Logger.debug(" DAY2 :" + effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
//		play.Logger.debug(" DAY2 :" + effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());
//
//		play.Logger.debug(" DAY3 :" + effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
//		play.Logger.debug(" DAY3 :" + effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
//		play.Logger.debug(" DAY3 :" + effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());
//
//		play.Logger.debug(" DAY4 :" + effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
//		play.Logger.debug(" DAY4 :" + effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
//		play.Logger.debug(" DAY4 :" + effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());


		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1123), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2403), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1123), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2403), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1015), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2295), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1015), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2295), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(1267), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(3.801), effectiveReduction.get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1267), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(3.801), effectiveReduction.get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1195), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(3.585), effectiveReduction.get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1195), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(3.585), effectiveReduction.get(ReductionDay.DAY4.ordinal()).getEnergyReduction());

	}



	private List<AnswerDTO> buildSampleActionsConsumerProfile001() {
		List<AnswerDTO> answersDTOs = new ArrayList<>();

		answersDTOs = buildSampleConsumerProfile001();

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
