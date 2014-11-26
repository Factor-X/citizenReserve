package eu.factorx.citizenReserve.service.impl;


import eu.factorx.citizenReserve.dto.AnswerDTO;
import eu.factorx.citizenReserve.dto.AnswerValueDTO;
import eu.factorx.citizenReserve.dto.ReductionDTO;
import eu.factorx.citizenReserve.model.AnswerValue;
import eu.factorx.citizenReserve.model.Period;
import eu.factorx.citizenReserve.model.QuestionCode;
import eu.factorx.citizenReserve.service.CalculationService;
import scalax.file.PathMatcher;

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


    @Override
    public ReductionDTO calculatePotentialReduction(List<AnswerDTO> surveyAnswers) {

		ReductionDTO reduction = new ReductionDTO();
		Double firstPeriodTotal = ZERO;
		Double secondPeriodTotal = ZERO;
		Double thirdPeriodTotal = ZERO;

		Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);

		// ******************** Gros electromenager **********************************
		// 1110
		addReductionForQuestionCode(QuestionCode.Q1110,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);
		// 1120
		addReductionForQuestionCode(QuestionCode.Q1120,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);
		// 1130
		addReductionForQuestionCode(QuestionCode.Q1130,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);

		// ******************** Chauffage et eau chaude ******************************
		// 1600
		addReductionForQuestionCodeType1(QuestionCode.Q1600, byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal );
		// 1210
		addReductionForQuestionCode1210(byQuestionCodeAndPeriod,firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal);

		// ******************** Eclairage et electromenager ***************************
		// 1160
		addReductionForQuestionCode(QuestionCode.Q1160,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);
		// 1120
		addReductionForQuestionCodeType1(QuestionCode.Q1220,byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal);
		// 1230
		addReductionForQuestionCodeType1(QuestionCode.Q1230,byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal,thirdPeriodTotal);
		// 1700 - special
		addReductionForQuestionCode1700(byQuestionCodeAndPeriod, firstPeriodTotal, secondPeriodTotal, thirdPeriodTotal);


		// 2010 - special - TODO
		byQuestionCodeAndPeriod.get(QuestionCode.Q2010).get(Period.FIRST).getDoubleValue();

		// 2020 - special - TODO
		byQuestionCodeAndPeriod.get(QuestionCode.Q2020).get(Period.FIRST).getDoubleValue();

		// 2030 - special - TODO
		byQuestionCodeAndPeriod.get(QuestionCode.Q2030).get(Period.FIRST).getDoubleValue();

		// 2040 - special - TODO
		byQuestionCodeAndPeriod.get(QuestionCode.Q2040).get(Period.FIRST).getDoubleValue();


		// 1235 - special - TODO
		byQuestionCodeAndPeriod.get(QuestionCode.Q1235).get(Period.FIRST).getDoubleValue();

		// 1140 - special - TODO
		byQuestionCodeAndPeriod.get(QuestionCode.Q1140).get(Period.FIRST).getDoubleValue();

		// 1150
		addReductionForQuestionCode(QuestionCode.Q1150,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);

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

	// generic
	private void addReductionForQuestionCode(QuestionCode questionCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal) {

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
	private void addReductionForQuestionCodeType1(QuestionCode questionCode,Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal) {

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

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>0) { // if someone house between 17h00 and 18h00
			firstPeriodTotal +=
					(QuestionCode.Q1700.getNominalPower() *
							byQuestionCodeAndPeriod.get(QuestionCode.Q1700).get(Period.FIRST).getDoubleValue()
					) / WORKING_DAYS_BY_WEEK;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>1) { // if someone house between 18h00 and 19h00
			secondPeriodTotal +=
					(QuestionCode.Q1700.getNominalPower() *
							byQuestionCodeAndPeriod.get(QuestionCode.Q1700).get(Period.SECOND).getDoubleValue()
					) / WORKING_DAYS_BY_WEEK;
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getDoubleValue()>2) { // if someone house between 19h00 and 20h00
			thirdPeriodTotal +=
					(QuestionCode.Q1700.getNominalPower() *
							byQuestionCodeAndPeriod.get(QuestionCode.Q1700).get(Period.THIRD).getDoubleValue()
					) / WORKING_DAYS_BY_WEEK;
		}

		return;
	}




}
