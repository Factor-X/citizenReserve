package eu.factorx.citizenReserve.service.impl;


import eu.factorx.citizenReserve.dto.AnswerDTO;
import eu.factorx.citizenReserve.dto.AnswerValueDTO;
import eu.factorx.citizenReserve.dto.ReductionDTO;
import eu.factorx.citizenReserve.model.AnswerValue;
import eu.factorx.citizenReserve.model.Period;
import eu.factorx.citizenReserve.model.QuestionCode;
import eu.factorx.citizenReserve.service.CalculationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalculationServiceImpl implements CalculationService {

	// declare some basic constants
	static final Double WORKING_DAYS_BY_WEEK = 5.0;
	static final Double THOUSAND = 1000.0;
	static final Double HOURSRANGE = 3.0;
	static final Double ZERO = 0.0;
	static final Double HALF = 2.0;
	static final Double THREEQUARTER = (4.0 * 3.0);
	static final Double FOUR = 4.0;
	static final Double THREEQUARTERDOUBLE = THREEQUARTER * 2;


    @Override
    public ReductionDTO calculatePotentialReduction(List<AnswerDTO> surveyAnswers) {

		ReductionDTO reduction = new ReductionDTO();
		Double firstPeriodTotal = ZERO;
		Double secondPeriodTotal = ZERO;
		Double thirdPeriodTotal = ZERO;

		Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);

		// ******************** Gros electromenager **********************************
		// 1110
		addReductionForQuestionCodeAlgo0(QuestionCode.Q1110,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);
		// 1120
		addReductionForQuestionCodeAlgo0(QuestionCode.Q1120,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);
		// 1130
		addReductionForQuestionCodeAlgo0(QuestionCode.Q1130,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);

		// ******************** Chauffage et eau chaude ******************************
		// 1600
		addReductionForQuestionCodeAlgo1(QuestionCode.Q1600, byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal );
		// 1210
		addReductionForQuestionCode1210(byQuestionCodeAndPeriod,firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal);

		// ******************** Eclairage et electromenager ***************************
		// 1160
		addReductionForQuestionCodeAlgo0(QuestionCode.Q1160,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);
		// 1120
		addReductionForQuestionCodeAlgo1(QuestionCode.Q1220,byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal);
		// 1230
		addReductionForQuestionCodeAlgo1(QuestionCode.Q1230,byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal);
		// 1700 - special
		addReductionForQuestionCode1700(byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal, thirdPeriodTotal);
		// 1750 - special
		addReductionForQuestionCode1750(byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal, thirdPeriodTotal);
		// 2010 - special
		addReductionForQuestionCodeAlgo2(QuestionCode.Q2010,byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal);
		// 2020 - special
		addReductionForQuestionCodeAlgo2(QuestionCode.Q2020,byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal);
		// 2030 - special
		addReductionForQuestionCodeAlgo2(QuestionCode.Q2030,byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal);
		// 2040 - special
		addReductionForQuestionCode2040(byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal, thirdPeriodTotal);
		// 1235 - special
		addReductionForQuestionCode1235(byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal, thirdPeriodTotal);


		// ******************** Repas ***************************
		// 1140 - special
		addReductionForQuestionCode1140(byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal, thirdPeriodTotal);

		// 1150
		addReductionForQuestionCodeAlgo0(QuestionCode.Q1150,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);

		// please keep in mind to add adjustment factor - TODO
		ReductionDTO adjustmentFactor = adjustmentFactor();
		firstPeriodTotal+= adjustmentFactor.getFirstPeriodPowerReduction();
		secondPeriodTotal+= adjustmentFactor.getSecondPeriodPowerReduction();
		thirdPeriodTotal+= adjustmentFactor.getThirdPeriodPowerReduction();

		// set up return object's values
		reduction.setFirstPeriodPowerReduction(firstPeriodTotal);
		reduction.setSecondPeriodPowerReduction(secondPeriodTotal);
		reduction.setThirdPeriodPowerReduction(thirdPeriodTotal);

		Double AllPeriodsTotal =  firstPeriodTotal+secondPeriodTotal+thirdPeriodTotal;

		reduction.setAveragePowerReduction(AllPeriodsTotal/HOURSRANGE);
		reduction.setEnergyReduction((AllPeriodsTotal*HOURSRANGE)/THOUSAND);


		return reduction;
    }

    @Override
    public ReductionDTO calculateEffectiveReduction(List<AnswerDTO> surveyAnswers) {
        return null;
    }

	/************************ UTIL methods ******************************/

	/*
	* Convert to map
	*/

	private Map<QuestionCode,Map<Period,AnswerValueDTO>> convertToMap (List<AnswerDTO> surveyAnswers) {

		Map<QuestionCode,Map<Period,AnswerValueDTO>> localMapByQuestionCode = new HashMap<QuestionCode,Map<Period,AnswerValueDTO>> ();

		// koop for all answers and generate map
		for (AnswerDTO answer : surveyAnswers) {
			Map localMapByPeriod = new HashMap<Period,AnswerValue> ();

			localMapByPeriod.put (answer.getPeriodKey(),answer.getAnswerValues().get(0));
			localMapByQuestionCode.put(QuestionCode.valueOf(answer.getQuestionKey()), localMapByPeriod);
		}

		return (localMapByQuestionCode);
	}

	/******************* Calculation methods **************************/

	/*
	* Adjustement factor
	*/

	private ReductionDTO adjustmentFactor () {
		ReductionDTO adjustmentFactor = new ReductionDTO();
		adjustmentFactor.setFirstPeriodPowerReduction(ZERO);
		adjustmentFactor.setSecondPeriodPowerReduction(ZERO);
		adjustmentFactor.setThirdPeriodPowerReduction(ZERO);
		return (adjustmentFactor);
	}

	// generic for 1110, 1120, 1130, 1160, 1150
	private void addReductionForQuestionCodeAlgo0(QuestionCode questionCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal) {

		firstPeriodTotal +=
				(questionCode.getNominalPower() *
						byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getDoubleValue()
				) / WORKING_DAYS_BY_WEEK;

		secondPeriodTotal +=
				(questionCode.getNominalPower() *
						byQuestionCodeAndPeriod.get(questionCode).get(Period.SECOND).getDoubleValue()
				) / WORKING_DAYS_BY_WEEK;

		thirdPeriodTotal +=
				(questionCode.getNominalPower() *
						byQuestionCodeAndPeriod.get(questionCode).get(Period.THIRD).getDoubleValue()
				) / WORKING_DAYS_BY_WEEK;

		return;
	}

	//specific for 1600, 1220, 1230
	private void addReductionForQuestionCodeAlgo1(QuestionCode questionCode,Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal) {

		Double value = ZERO;

		value =
				(questionCode.getNominalPower() *
						byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getDoubleValue()
				);

		firstPeriodTotal += value;
		secondPeriodTotal += value;
		thirdPeriodTotal += value;

		return;
	}

	//specific for 1210
	private void addReductionForQuestionCode1210(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal ) {

		Double value = ZERO;

		value =
				(
				  (QuestionCode.Q1210.getNominalPower() *
						byQuestionCodeAndPeriod.get(QuestionCode.Q1210).get(Period.FIRST).getDoubleValue()
				  ) *   byQuestionCodeAndPeriod.get(QuestionCode.Q1300).get(Period.FIRST).getDoubleValue() // nb of people in house
				) / HALF;

		firstPeriodTotal += value;
		secondPeriodTotal += value;
		thirdPeriodTotal += value;

		return;
	}

	//specific for 1700
	private void addReductionForQuestionCode1700(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal) {

		Double value = ZERO;


		value =
					(QuestionCode.Q1700.getNominalPower() *
							byQuestionCodeAndPeriod.get(QuestionCode.Q1700).get(Period.FIRST).getDoubleValue()
					) / WORKING_DAYS_BY_WEEK;


		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			firstPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			secondPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			thirdPeriodTotal += value;
		}

		return;
	}

	//specific for 2010, 2020, 2030
	private void addReductionForQuestionCodeAlgo2(QuestionCode questionCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal ) {

		Double value = ZERO;

		value =
				(
					(questionCode.getNominalPower() *
								byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getDoubleValue())

			) / THREEQUARTER;

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			firstPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			secondPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			thirdPeriodTotal += value;
		}

		return;
	}


	//specific for 1750
	private void addReductionForQuestionCode1750(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal ) {

		Double value = ZERO;

		value =
				(
						(	QuestionCode.Q1750.getNominalPower()
							* ((byQuestionCodeAndPeriod.get(QuestionCode.Q1750).get(Period.FIRST).getDoubleValue()) / FOUR)
						)
							* (byQuestionCodeAndPeriod.get(QuestionCode.Q1300).get(Period.FIRST).getDoubleValue() / HALF) // nb of people in house
				);

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			firstPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			secondPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			thirdPeriodTotal += value;
		}

		return;
	}


	//specific for 2040
	private void addReductionForQuestionCode2040(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal ) {

		Double value = ZERO;

		value =
				(
						(QuestionCode.Q2040.getNominalPower() *
								byQuestionCodeAndPeriod.get(QuestionCode.Q2040).get(Period.FIRST).getDoubleValue())

				) / THREEQUARTERDOUBLE;

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			firstPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			secondPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			thirdPeriodTotal += value;
		}

		return;
	}

	//specific for 1235
	private void addReductionForQuestionCode1235(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal ) {

		Double value = ZERO;

		value =
				(
						(QuestionCode.Q1235.getNominalPower() *
								byQuestionCodeAndPeriod.get(QuestionCode.Q1235).get(Period.FIRST).getDoubleValue())

				);

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			firstPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			secondPeriodTotal += value;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			thirdPeriodTotal += value;
		}

		return;
	}

	//specific for 1140
	private void addReductionForQuestionCode1140(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal ) {


		firstPeriodTotal =
				(
						(
								(QuestionCode.Q1140.getNominalPower() *
								 byQuestionCodeAndPeriod.get(QuestionCode.Q1140).get(Period.FIRST).getDoubleValue())
								/ WORKING_DAYS_BY_WEEK
						) *
								(
										byQuestionCodeAndPeriod.get(QuestionCode.Q1300).get(Period.FIRST).getDoubleValue() // nb of people in house

								) / HALF
				);

		secondPeriodTotal =
				(
						(
								(QuestionCode.Q1140.getNominalPower() *
										byQuestionCodeAndPeriod.get(QuestionCode.Q1140).get(Period.SECOND).getDoubleValue())
										/ WORKING_DAYS_BY_WEEK
						) *
								(
										byQuestionCodeAndPeriod.get(QuestionCode.Q1300).get(Period.FIRST).getDoubleValue() // nb of people in house

								) / HALF
				);

		thirdPeriodTotal =
				(
						(
								(QuestionCode.Q1140.getNominalPower() *
										byQuestionCodeAndPeriod.get(QuestionCode.Q1140).get(Period.THIRD).getDoubleValue())
										/ WORKING_DAYS_BY_WEEK
						) *
								(
										byQuestionCodeAndPeriod.get(QuestionCode.Q1300).get(Period.FIRST).getDoubleValue() // nb of people in house

								) / HALF
				);


		return;
	}

}
