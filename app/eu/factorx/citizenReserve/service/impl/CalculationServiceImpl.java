package eu.factorx.citizenReserve.service.impl;


import eu.factorx.citizenReserve.dto.AnswerDTO;
import eu.factorx.citizenReserve.dto.AnswerValueDTO;
import eu.factorx.citizenReserve.dto.ReductionDTO;
import eu.factorx.citizenReserve.model.survey.AnswerValue;
import eu.factorx.citizenReserve.model.survey.Period;
import eu.factorx.citizenReserve.model.survey.QuestionCode;
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

		Double firstPeriodTotal = ZERO;
		Double secondPeriodTotal = ZERO;
		Double thirdPeriodTotal = ZERO;

		Map <QuestionCode,ReductionDTO> reductionDetails = new HashMap <QuestionCode,ReductionDTO>();
		ReductionDTO reductionSummary = new ReductionDTO();

		Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);

		// ******************** Gros electromenager **********************************
		// 1110
		reductionDetails.put(QuestionCode.Q1110,addReductionForQuestionCodeAlgo0(QuestionCode.Q1110, byQuestionCodeAndPeriod));
		// 1120
		reductionDetails.put(QuestionCode.Q1120,addReductionForQuestionCodeAlgo0(QuestionCode.Q1120, byQuestionCodeAndPeriod));
		// 1130
		reductionDetails.put(QuestionCode.Q1130,addReductionForQuestionCodeAlgo0(QuestionCode.Q1130, byQuestionCodeAndPeriod));

		// ******************** Chauffage et eau chaude ******************************
		// 1600
		reductionDetails.put(QuestionCode.Q1600,addReductionForQuestionCodeAlgo1(QuestionCode.Q1600, byQuestionCodeAndPeriod));
		// 1210
		reductionDetails.put(QuestionCode.Q1210,addReductionForQuestionCode1210(byQuestionCodeAndPeriod));

		// ******************** Eclairage et electromenager ***************************
		// 1160
		reductionDetails.put(QuestionCode.Q1160,addReductionForQuestionCodeAlgo0(QuestionCode.Q1160, byQuestionCodeAndPeriod));
		// 1120
		reductionDetails.put(QuestionCode.Q1220,addReductionForQuestionCodeAlgo1(QuestionCode.Q1220,byQuestionCodeAndPeriod));
		// 1230
		reductionDetails.put(QuestionCode.Q1230,addReductionForQuestionCodeAlgo1(QuestionCode.Q1230,byQuestionCodeAndPeriod));
		// 1700 - special
		reductionDetails.put(QuestionCode.Q1700,addReductionForQuestionCode1700(byQuestionCodeAndPeriod));
		// 1750 - special
		reductionDetails.put(QuestionCode.Q1750,addReductionForQuestionCode1750(byQuestionCodeAndPeriod));
		// 2010 - special
		reductionDetails.put(QuestionCode.Q2010,addReductionForQuestionCodeAlgo2(QuestionCode.Q2010,byQuestionCodeAndPeriod));
		// 2020 - special
		reductionDetails.put(QuestionCode.Q2020,addReductionForQuestionCodeAlgo2(QuestionCode.Q2020,byQuestionCodeAndPeriod));
		// 2030 - special
		reductionDetails.put(QuestionCode.Q2030,addReductionForQuestionCodeAlgo2(QuestionCode.Q2030,byQuestionCodeAndPeriod));
		// 2040 - special
		reductionDetails.put(QuestionCode.Q2040,addReductionForQuestionCode2040(byQuestionCodeAndPeriod));
		// 1235 - special
		reductionDetails.put(QuestionCode.Q1235,addReductionForQuestionCode1235(byQuestionCodeAndPeriod));


		// ******************** Repas ***************************
		// 1140 - special
		reductionDetails.put(QuestionCode.Q1140,addReductionForQuestionCode1140(byQuestionCodeAndPeriod));
		// 1150
		reductionDetails.put(QuestionCode.Q1150,addReductionForQuestionCodeAlgo0(QuestionCode.Q1150, byQuestionCodeAndPeriod));

		// please keep in mind to add adjustment factor - TODO
		//reductionDetails.put("ADJUSTMENT,adjustmentFactor());

		// Perform sum
		for (Map.Entry<QuestionCode, ReductionDTO> item : reductionDetails.entrySet()) {
			QuestionCode key = item.getKey();
			ReductionDTO value = item.getValue();

			firstPeriodTotal+=value.getFirstPeriodPowerReduction();
			secondPeriodTotal+=value.getSecondPeriodPowerReduction();
			thirdPeriodTotal+=value.getSecondPeriodPowerReduction();
		}

		// set up return object's values
		reductionSummary.setFirstPeriodPowerReduction(firstPeriodTotal);
		reductionSummary.setSecondPeriodPowerReduction(secondPeriodTotal);
		reductionSummary.setThirdPeriodPowerReduction(thirdPeriodTotal);

		Double AllPeriodsTotal =  firstPeriodTotal+secondPeriodTotal+thirdPeriodTotal;

		reductionSummary.setAveragePowerReduction(AllPeriodsTotal/HOURSRANGE);
		reductionSummary.setEnergyReduction((AllPeriodsTotal*HOURSRANGE)/THOUSAND);


		return reductionSummary;
    }

	private Map <QuestionCode,ReductionDTO> calculatePotentialReductionTable(List<AnswerDTO> surveyAnswers) {



		return null;
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
	private ReductionDTO addReductionForQuestionCodeAlgo0(QuestionCode questionCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		result.setFirstPeriodPowerReduction(
				(questionCode.getNominalPower() *
				  byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getDoubleValue()
				) / WORKING_DAYS_BY_WEEK);

		result.setSecondPeriodPowerReduction(
				(questionCode.getNominalPower() *
						byQuestionCodeAndPeriod.get(questionCode).get(Period.SECOND).getDoubleValue()
				) / WORKING_DAYS_BY_WEEK);

		result.setThirdPeriodPowerReduction(
				(questionCode.getNominalPower() *
						byQuestionCodeAndPeriod.get(questionCode).get(Period.THIRD).getDoubleValue()
				) / WORKING_DAYS_BY_WEEK);


		return result;
	}

	//specific for 1600, 1220, 1230
	private ReductionDTO addReductionForQuestionCodeAlgo1(QuestionCode questionCode,Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =
				(questionCode.getNominalPower() *
						byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getDoubleValue()
				);

		result.setFirstPeriodPowerReduction(value);
		result.setSecondPeriodPowerReduction(value);
		result.setThirdPeriodPowerReduction(value);

		return result;
	}

	//specific for 1210
	private ReductionDTO addReductionForQuestionCode1210(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =
				(
				  (QuestionCode.Q1210.getNominalPower() *
						byQuestionCodeAndPeriod.get(QuestionCode.Q1210).get(Period.FIRST).getDoubleValue()
				  ) *   byQuestionCodeAndPeriod.get(QuestionCode.Q1300).get(Period.FIRST).getDoubleValue() // nb of people in house
				) / HALF;

		result.setFirstPeriodPowerReduction(value);
		result.setSecondPeriodPowerReduction(value);
		result.setThirdPeriodPowerReduction(value);

		return result;
	}

	//specific for 1700
	private ReductionDTO addReductionForQuestionCode1700(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =
					(QuestionCode.Q1700.getNominalPower() *
							byQuestionCodeAndPeriod.get(QuestionCode.Q1700).get(Period.FIRST).getDoubleValue()
					) / WORKING_DAYS_BY_WEEK;


		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			result.setFirstPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			result.setSecondPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			result.setThirdPeriodPowerReduction(value);
		}

		return result;
	}

	//specific for 2010, 2020, 2030
	private ReductionDTO addReductionForQuestionCodeAlgo2(QuestionCode questionCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =
				(
					(questionCode.getNominalPower() *
								byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getDoubleValue())

			) / THREEQUARTER;

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			result.setFirstPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			result.setSecondPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			result.setThirdPeriodPowerReduction(value);
		}

		return result;
	}


	//specific for 1750
	private ReductionDTO addReductionForQuestionCode1750(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =
				(
						(	QuestionCode.Q1750.getNominalPower()
							* ((byQuestionCodeAndPeriod.get(QuestionCode.Q1750).get(Period.FIRST).getDoubleValue()) / FOUR)
						)
							* (byQuestionCodeAndPeriod.get(QuestionCode.Q1300).get(Period.FIRST).getDoubleValue() / HALF) // nb of people in house
				);

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			result.setFirstPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			result.setSecondPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			result.setThirdPeriodPowerReduction(value);
		}

		return result;
	}


	//specific for 2040
	private ReductionDTO addReductionForQuestionCode2040(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =
				(
						(QuestionCode.Q2040.getNominalPower() *
								byQuestionCodeAndPeriod.get(QuestionCode.Q2040).get(Period.FIRST).getDoubleValue())

				) / THREEQUARTERDOUBLE;

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			result.setFirstPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			result.setSecondPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			result.setThirdPeriodPowerReduction(value);
		}

		return result;
	}

	//specific for 1235
	private ReductionDTO addReductionForQuestionCode1235(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =
				(
						(QuestionCode.Q1235.getNominalPower() *
								byQuestionCodeAndPeriod.get(QuestionCode.Q1235).get(Period.FIRST).getDoubleValue())

				);

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			result.setFirstPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			result.setSecondPeriodPowerReduction(value);
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			result.setThirdPeriodPowerReduction(value);
		}

		return result;
	}

	//specific for 1140
	private ReductionDTO addReductionForQuestionCode1140(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		Double firstPeriod;
		Double secondPeriod;
		Double thirdPeriod;

		ReductionDTO result = new ReductionDTO();


		firstPeriod =
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

		secondPeriod =
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

		thirdPeriod =
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


		result.setFirstPeriodPowerReduction(firstPeriod);
		result.setSecondPeriodPowerReduction(secondPeriod);
		result.setThirdPeriodPowerReduction(thirdPeriod);


		return result;
	}

}
