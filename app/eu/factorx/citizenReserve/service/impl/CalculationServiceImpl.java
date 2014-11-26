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


    @Override
    public ReductionDTO calculatePotentialReduction(List<AnswerDTO> surveyAnswers) {

		ReductionDTO reduction = new ReductionDTO();
		Double firstPeriodTotal = ZERO;
		Double secondPeriodTotal = ZERO;
		Double thirdPeriodTotal = ZERO;

		Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);

		// Gros electro menager
		// 1110
		addReductionForQuestionCode(QuestionCode.Q1110,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);
		// 1120
		addReductionForQuestionCode(QuestionCode.Q1120,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);
		// 1130
		addReductionForQuestionCode(QuestionCode.Q1130,byQuestionCodeAndPeriod,firstPeriodTotal,secondPeriodTotal,thirdPeriodTotal);

		// Chauffage et eau chaude
		// 1600
		byQuestionCodeAndPeriod.get(QuestionCode.Q1600).get(Period.FIRST).getDoubleValue();

		// 1210
		byQuestionCodeAndPeriod.get(QuestionCode.Q1210).get(Period.FIRST).getDoubleValue();

		// 1160
		byQuestionCodeAndPeriod.get(QuestionCode.Q1160).get(Period.FIRST).getDoubleValue();

		// 1120
		byQuestionCodeAndPeriod.get(QuestionCode.Q1120).get(Period.FIRST).getDoubleValue();

		// 1230
		byQuestionCodeAndPeriod.get(QuestionCode.Q1230).get(Period.FIRST).getDoubleValue();

		// 1700
		byQuestionCodeAndPeriod.get(QuestionCode.Q1700).get(Period.FIRST).getDoubleValue();

		// 2010
		byQuestionCodeAndPeriod.get(QuestionCode.Q2010).get(Period.FIRST).getDoubleValue();

		// 2020
		byQuestionCodeAndPeriod.get(QuestionCode.Q2020).get(Period.FIRST).getDoubleValue();

		// 2030
		byQuestionCodeAndPeriod.get(QuestionCode.Q2030).get(Period.FIRST).getDoubleValue();

		// 2040
		byQuestionCodeAndPeriod.get(QuestionCode.Q2040).get(Period.FIRST).getDoubleValue();


		// 1235
		byQuestionCodeAndPeriod.get(QuestionCode.Q1235).get(Period.FIRST).getDoubleValue();

		// 1140
		byQuestionCodeAndPeriod.get(QuestionCode.Q1140).get(Period.FIRST).getDoubleValue();

		// 1150
		byQuestionCodeAndPeriod.get(QuestionCode.Q1150).get(Period.FIRST).getDoubleValue();

		// please keep in mind to add adjustment factor
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
		adjustmentFactor.setFirstPeriodPowerReduction(0.0);
		adjustmentFactor.setSecondPeriodPowerReduction(0.0);
		adjustmentFactor.setThirdPeriodPowerReduction(0.0);
		return (adjustmentFactor);
	}

	private void addReductionForQuestionCode(QuestionCode questionCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double firstPeriodTotal, Double secondPeriodTotal, Double thirdPeriodTotal) {

		firstPeriodTotal +=
				(QuestionCode.Q1110.getNominalPower() *
						byQuestionCodeAndPeriod.get(QuestionCode.Q1110).get(Period.FIRST).getDoubleValue()
				) / WORKING_DAYS_BY_WEEK;

		secondPeriodTotal +=
				(QuestionCode.Q1110.getNominalPower() *
						byQuestionCodeAndPeriod.get(QuestionCode.Q1110).get(Period.SECOND).getDoubleValue()
				) / WORKING_DAYS_BY_WEEK;

		thirdPeriodTotal +=
				(QuestionCode.Q1110.getNominalPower() *
						byQuestionCodeAndPeriod.get(QuestionCode.Q1110).get(Period.THIRD).getDoubleValue()
				) / WORKING_DAYS_BY_WEEK;

		return;
	}


}
