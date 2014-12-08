package eu.factorx.citizens.service.impl;


import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.AnswerValueDTO;
import eu.factorx.citizens.dto.ReductionDTO;
import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.model.type.ReductionDay;
import eu.factorx.citizens.service.CalculationService;
import play.Logger;

import java.util.ArrayList;
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

    // TODO validate incoming information method
	@Override
	public void validateProfile (List<AnswerDTO> surveyAnswers) {

		Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);


	}

	// TODO validate incoming information method
	@Override
	public List<AnswerDTO> validateActions (List<AnswerDTO> surveyAnswers) {

		List<AnswerDTO> answersDTOs = new ArrayList<AnswerDTO>();
		Map<QuestionCode, Map<Period, AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);

		// Sortir
		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3210) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3210, null, false));
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "0"));
		}

		if ((byQuestionCodeAndPeriod.get(QuestionCode.Q3211) == null) || (byQuestionCodeAndPeriod.get(QuestionCode.Q3211).get(Period.FIRST).getStringValue() == null)) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3211, null, "0"));
		}

		// Programme et electromenager
		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3110) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3110, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3120) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3120, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3130) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3130, null, false));
		}

		//Chauffage et eau chaude


		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3310) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3310, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3320) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3320, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3330) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3330, null, false));
		}


		//Eclairage & electromenager

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3410) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3410, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3420) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3420, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3510) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3510, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3530) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3530, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3610) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3610, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3620) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3620, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3630) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3630, null, false));
		}

		if ( (byQuestionCodeAndPeriod.get(QuestionCode.Q3631) == null) || (byQuestionCodeAndPeriod.get(QuestionCode.Q3631).get(Period.FIRST).getStringValue() == null)) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3631, null, "0"));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3640) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3640, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3810) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3810, null, false));
		}


		//Repas

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3710) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3710, null, false));
		}

		if ( (byQuestionCodeAndPeriod.get(QuestionCode.Q3711) == null) || (byQuestionCodeAndPeriod.get(QuestionCode.Q3711).get(Period.FIRST).getStringValue() == null)) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3711, null, "0"));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3720) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3720, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3730) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3730, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3740) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3740, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3750) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3750, null, false));
		}

		if (byQuestionCodeAndPeriod.get(QuestionCode.Q3760) == null) {
			answersDTOs.add(buildAnswerDTO(QuestionCode.Q3760, null, false));
		}

		return (answersDTOs);

	}

	/*****************************************************************************/


	/************************************** Private methods ***************************/

	// TODO set in common package

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

	/************************************************************************************/

	@Override
    public ReductionDTO calculatePotentialReduction(List<AnswerDTO> surveyAnswers) {

		play.Logger.debug("Entering -> calculatePotentialReduction");

		Map <QuestionCode,ReductionDTO> potentialReductionDetails = new HashMap <QuestionCode,ReductionDTO>();
		ReductionDTO potentialReductionSummary = new ReductionDTO();

		//for debug purposes
		//Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);
		//dumpMap(byQuestionCodeAndPeriod);

		potentialReductionDetails = calculatePotentialReductionDetails(surveyAnswers);
		potentialReductionSummary = calculatePotentialSummary(potentialReductionDetails);

		return potentialReductionSummary;
    }


	@Override
	public List<ReductionDTO> calculateEffectiveReduction(List<AnswerDTO> surveyAnswers) {

		Map <QuestionCode,ReductionDTO> potentialReductionDetails = new HashMap <QuestionCode,ReductionDTO>();
		Map <QuestionCode,List<ReductionDTO>> effectiveReductionDetails = new HashMap <QuestionCode,List<ReductionDTO>>();

		List<ReductionDTO> effectiveReductionSummary = new ArrayList<ReductionDTO>();
		ReductionDTO potentialReductionSummary = new ReductionDTO();

		// for debug purposes
		//Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);
		//dumpMap(byQuestionCodeAndPeriod);

		potentialReductionDetails = calculatePotentialReductionDetails(surveyAnswers);
		potentialReductionSummary = calculatePotentialSummary(potentialReductionDetails);

		effectiveReductionDetails = calculateEffectiveReductionDetails(surveyAnswers, potentialReductionDetails, potentialReductionSummary);
		effectiveReductionSummary = calculateEffectiveSummary(effectiveReductionDetails);

		//dumpEffectiveReductionDetailsMap(effectiveReductionDetails);

		return effectiveReductionSummary;
	}

	/*************** Private methods ****************/

	/*************** EFFECTIVE REDUCTION ************/

	private List<ReductionDTO> calculateEffectiveSummary(Map <QuestionCode,List<ReductionDTO>> effectiveReductionDetails) {

		List<ReductionDTO> potentialReductionSummary = new ArrayList<ReductionDTO>();

		// temp data storage
		List<Double> firstPeriodTotal = new ArrayList<Double> ();
		List<Double> secondPeriodTotal = new ArrayList<Double> ();
		List<Double> thirdPeriodTotal = new ArrayList<Double> ();

		// init temp data lists
		for (ReductionDay day : ReductionDay.values()) {
			firstPeriodTotal.add(day.ordinal(), ZERO);
			secondPeriodTotal.add(day.ordinal(), ZERO);
			thirdPeriodTotal.add(day.ordinal(), ZERO);
		}

		// Perform temporary sum items
		for (Map.Entry<QuestionCode, List<ReductionDTO>> item : effectiveReductionDetails.entrySet()) {
			QuestionCode key = item.getKey();
			List<ReductionDTO> value = item.getValue();
			//play.Logger.debug(">> Summary : [" + key.name() + "]");

			for (ReductionDay day : ReductionDay.values()) {
				//play.Logger.debug(">> DAY : [" + day.name() + "]");
				//play.Logger.debug(">>>>>> First : [" + value.get(day.ordinal()).getFirstPeriodPowerReduction() + "]");
				//play.Logger.debug(">>>>>> Secon : [" + value.get(day.ordinal()).getSecondPeriodPowerReduction() + "]");
				//play.Logger.debug(">>>>>> Third : [" + value.get(day.ordinal()).getThirdPeriodPowerReduction() + "]");

				firstPeriodTotal.set(day.ordinal(),firstPeriodTotal.get(day.ordinal()) + value.get(day.ordinal()).getFirstPeriodPowerReduction());
				secondPeriodTotal.set(day.ordinal(), secondPeriodTotal.get(day.ordinal()) + value.get(day.ordinal()).getSecondPeriodPowerReduction());
				thirdPeriodTotal.set(day.ordinal(), thirdPeriodTotal.get(day.ordinal()) + value.get(day.ordinal()).getThirdPeriodPowerReduction());
			}
		}

		// store result into return list
		for (ReductionDay day : ReductionDay.values()) {
			ReductionDTO loop = new ReductionDTO();

			loop.setFirstPeriodPowerReduction(firstPeriodTotal.get(day.ordinal()));
			loop.setSecondPeriodPowerReduction(secondPeriodTotal.get(day.ordinal()));
			loop.setThirdPeriodPowerReduction(thirdPeriodTotal.get(day.ordinal()));

			Double AllPeriodsTotal = firstPeriodTotal.get(day.ordinal()) + secondPeriodTotal.get(day.ordinal()) + thirdPeriodTotal.get(day.ordinal());

			loop.setAveragePowerReduction(AllPeriodsTotal / HOURSRANGE);
			loop.setEnergyReduction((loop.getAveragePowerReduction() * HOURSRANGE) / THOUSAND);

			potentialReductionSummary.add(day.ordinal(),loop);
		}

		return (potentialReductionSummary);
	}

	private Map <QuestionCode,List<ReductionDTO>> calculateEffectiveReductionDetails(List<AnswerDTO> surveyAnswers, Map <QuestionCode,ReductionDTO> potentialReductionDetails, ReductionDTO potentialReductionSummary) {

		Map <QuestionCode,List<ReductionDTO>> reductionDetails = new HashMap <QuestionCode,List<ReductionDTO>>();
		ReductionDTO reductionSummary = new ReductionDTO();

		Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);

		// Sortir
		reductionDetails.put(QuestionCode.Q3210, computeReductionForQuestionCode3210(byQuestionCodeAndPeriod, potentialReductionDetails, potentialReductionSummary));

		// Programmes et Gros electromenager
		reductionDetails.put(QuestionCode.Q3110,computeReductionForQuestionCodeGeneral(QuestionCode.Q3110, QuestionCode.Q1110, byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3120,computeReductionForQuestionCodeGeneral(QuestionCode.Q3120, QuestionCode.Q1120, byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3130,computeReductionForQuestionCodeGeneral(QuestionCode.Q3130,QuestionCode.Q1130,byQuestionCodeAndPeriod, potentialReductionDetails));

		//Chauffage et eau chaude
		reductionDetails.put(QuestionCode.Q3310,computeReductionForQuestionCodeGeneral(QuestionCode.Q3310,QuestionCode.Q1600,byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3320,computeReductionForQuestionCodeGeneral(QuestionCode.Q3320,QuestionCode.Q1900,byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3330,computeReductionForQuestionCodeGeneral(QuestionCode.Q3330,QuestionCode.Q1210,byQuestionCodeAndPeriod, potentialReductionDetails));

		//Eclairage et electromenager
		reductionDetails.put(QuestionCode.Q3410,computeReductionForQuestionCodeGeneral(QuestionCode.Q3410,QuestionCode.Q1220,byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3420,computeReductionForQuestionCodeGeneral(QuestionCode.Q3420,QuestionCode.Q1230,byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3510,computeReductionForQuestionCodeGeneral(QuestionCode.Q3510,QuestionCode.Q1750,byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3530,computeReductionForQuestionCodeGeneral(QuestionCode.Q3530,QuestionCode.Q1800,byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3610,computeReductionForQuestionCodeGeneral(QuestionCode.Q3610,QuestionCode.Q2010,byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3620,computeReductionForQuestionCodeGeneral(QuestionCode.Q3620,QuestionCode.Q2020,byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3630,computeReductionForQuestionCode3630(byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3640,computeReductionForQuestionCodeGeneral(QuestionCode.Q3640,QuestionCode.Q1160,byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3810,computeReductionForQuestionCodeGeneral(QuestionCode.Q3810,QuestionCode.Q1235,byQuestionCodeAndPeriod, potentialReductionDetails));

		//Repas
		reductionDetails.put(QuestionCode.Q3710,computeReductionForQuestionCode3710(byQuestionCodeAndPeriod, potentialReductionDetails));
		reductionDetails.put(QuestionCode.Q3720,computeReductionForQuestionCode3720(byQuestionCodeAndPeriod, potentialReductionDetails)); // TODO refactor to unique method
		reductionDetails.put(QuestionCode.Q3730,computeReductionForQuestionCode3730(byQuestionCodeAndPeriod, potentialReductionDetails)); // TODO refactor to unique method
		reductionDetails.put(QuestionCode.Q3750,computeReductionForQuestionCode3750(byQuestionCodeAndPeriod, potentialReductionDetails)); // TODO refactor to unique method
		reductionDetails.put(QuestionCode.Q3760,computeReductionForQuestionCode3760(byQuestionCodeAndPeriod, potentialReductionDetails)); // TODO refactor to unique method



		return reductionDetails;
	}

	//specific for 3110, 3120, 3130, 3310
	private List<ReductionDTO> computeReductionForQuestionCodeGeneral(QuestionCode effectiveCode,QuestionCode potentialCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Map <QuestionCode,ReductionDTO> potentialReductionDetails) {

		List<ReductionDTO> result = new ArrayList<ReductionDTO>();

		Double value = ZERO;

		for (ReductionDay day : ReductionDay.values()) {
			ReductionDTO localResult = new ReductionDTO();

			if ( ((byQuestionCodeAndPeriod.get(effectiveCode).get(Period.FIRST).getBooleanValue()!=null) && (byQuestionCodeAndPeriod.get(effectiveCode).get(Period.FIRST).getBooleanValue()))
					&& (Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3211).get(Period.FIRST).getStringValue())<1) ) {
				// YES
				localResult.setFirstPeriodPowerReduction(potentialReductionDetails.get(potentialCode).getFirstPeriodPowerReduction());
				localResult.setSecondPeriodPowerReduction(potentialReductionDetails.get(potentialCode).getSecondPeriodPowerReduction());
				localResult.setThirdPeriodPowerReduction(potentialReductionDetails.get(potentialCode).getThirdPeriodPowerReduction());
			} else {
				// NO
				localResult.setFirstPeriodPowerReduction(ZERO);
				localResult.setSecondPeriodPowerReduction(ZERO);
				localResult.setThirdPeriodPowerReduction(ZERO);
			}
			// add localResult to return list
			result.add(day.ordinal(),localResult);
		}

		return result;
	}





	//specific for 3210
	private List<ReductionDTO> computeReductionForQuestionCode3210(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Map <QuestionCode,ReductionDTO> potentialReductionDetails, ReductionDTO potentialReductionSummary) {


		List<ReductionDTO> result = new ArrayList<ReductionDTO>();

		Double value = ZERO;

		//
		Double reductionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3211).get(Period.FIRST).getStringValue());

		//play.Logger.debug("Number of days : " + reductionDaysNumber.intValue());

		for (ReductionDay day : ReductionDay.values()) {
			ReductionDTO localResult = new ReductionDTO();

			if ( ((byQuestionCodeAndPeriod.get(QuestionCode.Q3210).get(Period.FIRST).getBooleanValue()!=null) && (byQuestionCodeAndPeriod.get(QuestionCode.Q3210).get(Period.FIRST).getBooleanValue()))
					&& ((day.ordinal()+1) <= reductionDaysNumber.intValue()) ) {
				// YES to 3210
				localResult.setFirstPeriodPowerReduction(potentialReductionSummary.getFirstPeriodPowerReduction());
				localResult.setSecondPeriodPowerReduction(potentialReductionSummary.getSecondPeriodPowerReduction());
				localResult.setThirdPeriodPowerReduction(potentialReductionSummary.getThirdPeriodPowerReduction());
			} else {
				// NO to 3210
				localResult.setFirstPeriodPowerReduction(ZERO);
				localResult.setSecondPeriodPowerReduction(ZERO);
				localResult.setThirdPeriodPowerReduction(ZERO);
			}
			// add localResult to return list
			result.add(day.ordinal(),localResult);
		}

		return result;
	}

	//specific for 3630
	private List<ReductionDTO> computeReductionForQuestionCode3630(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Map <QuestionCode,ReductionDTO> potentialReductionDetails) {


		List<ReductionDTO> result = new ArrayList<ReductionDTO>();

		Double value = ZERO;

		//
		Double reductionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3631).get(Period.FIRST).getStringValue());

		Double GlobalActionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3211).get(Period.FIRST).getStringValue());

		//play.Logger.debug("Number of days : " + reductionDaysNumber.intValue());

		for (ReductionDay day : ReductionDay.values()) {
			ReductionDTO localResult = new ReductionDTO();

			if ( ((byQuestionCodeAndPeriod.get(QuestionCode.Q3630).get(Period.FIRST).getBooleanValue()!=null) && (byQuestionCodeAndPeriod.get(QuestionCode.Q3630).get(Period.FIRST).getBooleanValue()))
					&& ((day.ordinal()+1) <= reductionDaysNumber.intValue()) ) {

				if ((day.ordinal()+1) > GlobalActionDaysNumber) {
					// YES to 3630
					localResult.setFirstPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q1700).getFirstPeriodPowerReduction());
					localResult.setSecondPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q1700).getSecondPeriodPowerReduction());
					localResult.setThirdPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q1700).getThirdPeriodPowerReduction());
				} else {
					localResult.setFirstPeriodPowerReduction(ZERO);
					localResult.setSecondPeriodPowerReduction(ZERO);
					localResult.setThirdPeriodPowerReduction(ZERO);
				}
			} else {
				// NO to 3630
				localResult.setFirstPeriodPowerReduction(ZERO);
				localResult.setSecondPeriodPowerReduction(ZERO);
				localResult.setThirdPeriodPowerReduction(ZERO);
			}
			// add localResult to return list
			result.add(day.ordinal(),localResult);
		}

		return result;
	}

	//specific for 3710
	private List<ReductionDTO> computeReductionForQuestionCode3710(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Map <QuestionCode,ReductionDTO> potentialReductionDetails) {


		List<ReductionDTO> result = new ArrayList<ReductionDTO>();

		Double value = ZERO;

		//
		Double reductionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3711).get(Period.FIRST).getStringValue());

		Double GlobalActionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3211).get(Period.FIRST).getStringValue());

		play.Logger.debug("Number of days : " + reductionDaysNumber.intValue());

		for (ReductionDay day : ReductionDay.values()) {
			ReductionDTO localResult = new ReductionDTO();

			if ( ((byQuestionCodeAndPeriod.get(QuestionCode.Q3710).get(Period.FIRST).getBooleanValue()!=null) && (byQuestionCodeAndPeriod.get(QuestionCode.Q3710).get(Period.FIRST).getBooleanValue()))
					&& ((day.ordinal()+1) <= reductionDaysNumber.intValue()) ) {

					if ((day.ordinal()+1) > GlobalActionDaysNumber) {

						// YES to 3710
						localResult.setFirstPeriodPowerReduction(
								potentialReductionDetails.get(QuestionCode.Q2030).getFirstPeriodPowerReduction() +
										potentialReductionDetails.get(QuestionCode.Q2040).getFirstPeriodPowerReduction() +
										potentialReductionDetails.get(QuestionCode.Q1140).getFirstPeriodPowerReduction() +
										potentialReductionDetails.get(QuestionCode.Q1150).getFirstPeriodPowerReduction()
						);
						localResult.setSecondPeriodPowerReduction(
								potentialReductionDetails.get(QuestionCode.Q2030).getSecondPeriodPowerReduction() +
										potentialReductionDetails.get(QuestionCode.Q2040).getSecondPeriodPowerReduction() +
										potentialReductionDetails.get(QuestionCode.Q1140).getSecondPeriodPowerReduction() +
										potentialReductionDetails.get(QuestionCode.Q1150).getSecondPeriodPowerReduction()
						);
						localResult.setThirdPeriodPowerReduction(
								potentialReductionDetails.get(QuestionCode.Q2030).getThirdPeriodPowerReduction() +
										potentialReductionDetails.get(QuestionCode.Q2040).getThirdPeriodPowerReduction() +
										potentialReductionDetails.get(QuestionCode.Q1140).getThirdPeriodPowerReduction() +
										potentialReductionDetails.get(QuestionCode.Q1150).getThirdPeriodPowerReduction()
						);
					} else {
						localResult.setFirstPeriodPowerReduction(ZERO);
						localResult.setSecondPeriodPowerReduction(ZERO);
						localResult.setThirdPeriodPowerReduction(ZERO);
					}
			} else {
				// NO to 3710
				localResult.setFirstPeriodPowerReduction(ZERO);
				localResult.setSecondPeriodPowerReduction(ZERO);
				localResult.setThirdPeriodPowerReduction(ZERO);
			}
			// add localResult to return list
			result.add(day.ordinal(),localResult);
		}

		return result;
	}

	//specific for 3720
	private List<ReductionDTO> computeReductionForQuestionCode3720(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Map <QuestionCode,ReductionDTO> potentialReductionDetails) {


		List<ReductionDTO> result = new ArrayList<ReductionDTO>();

		Double value = ZERO;

		//
		Double reductionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3711).get(Period.FIRST).getStringValue());

		Double GlobalActionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3211).get(Period.FIRST).getStringValue());

		play.Logger.debug("Number of days : " + reductionDaysNumber.intValue());

		for (ReductionDay day : ReductionDay.values()) {
			ReductionDTO localResult = new ReductionDTO();

			if ( ((byQuestionCodeAndPeriod.get(QuestionCode.Q3730).get(Period.FIRST).getBooleanValue()!=null) && (byQuestionCodeAndPeriod.get(QuestionCode.Q3730).get(Period.FIRST).getBooleanValue()) )) {
				if ( ((day.ordinal()+1) <= reductionDaysNumber.intValue()) ) {
					// YES to 3730 and day is one of the repeat actions one
					//play.Logger.debug("DAY: " + day.name() + " set to ZER0 ");
					localResult.setFirstPeriodPowerReduction(ZERO);
					localResult.setSecondPeriodPowerReduction(ZERO);
					localResult.setThirdPeriodPowerReduction(ZERO);
				} else {
					if ((day.ordinal()+1) > GlobalActionDaysNumber) {
						//play.Logger.debug("DAY: " + day.name() + " set to value ");
						localResult.setFirstPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q1140).getFirstPeriodPowerReduction());
						localResult.setSecondPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q1140).getSecondPeriodPowerReduction());
						localResult.setThirdPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q1140).getThirdPeriodPowerReduction());
					} else {
						localResult.setFirstPeriodPowerReduction(ZERO);
						localResult.setSecondPeriodPowerReduction(ZERO);
						localResult.setThirdPeriodPowerReduction(ZERO);
					}
				}
			} else {
				// NO to 3730
				localResult.setFirstPeriodPowerReduction(ZERO);
				localResult.setSecondPeriodPowerReduction(ZERO);
				localResult.setThirdPeriodPowerReduction(ZERO);
			}
			// add localResult to return list
			result.add(day.ordinal(),localResult);
		}

		return result;
	}

	//specific for 3730
	private List<ReductionDTO> computeReductionForQuestionCode3730(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Map <QuestionCode,ReductionDTO> potentialReductionDetails) {


		List<ReductionDTO> result = new ArrayList<ReductionDTO>();

		Double value = ZERO;

		//
		Double reductionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3711).get(Period.FIRST).getStringValue());

		Double GlobalActionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3211).get(Period.FIRST).getStringValue());

		play.Logger.debug("Number of days : " + reductionDaysNumber.intValue());

		for (ReductionDay day : ReductionDay.values()) {
			ReductionDTO localResult = new ReductionDTO();

			if ( ((byQuestionCodeAndPeriod.get(QuestionCode.Q3730).get(Period.FIRST).getBooleanValue()!=null) && (byQuestionCodeAndPeriod.get(QuestionCode.Q3730).get(Period.FIRST).getBooleanValue()) )) {
				if ( ((day.ordinal()+1) <= reductionDaysNumber.intValue()) ) {
					// YES to 3730 and day is one of the repeat actions one
					//play.Logger.debug("DAY: " + day.name() + " set to ZER0 ");
					localResult.setFirstPeriodPowerReduction(ZERO);
					localResult.setSecondPeriodPowerReduction(ZERO);
					localResult.setThirdPeriodPowerReduction(ZERO);
				} else {
					if ((day.ordinal()+1) > GlobalActionDaysNumber) {
						//play.Logger.debug("DAY: " + day.name() + " set to value ");
						localResult.setFirstPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q1150).getFirstPeriodPowerReduction());
						localResult.setSecondPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q1150).getSecondPeriodPowerReduction());
						localResult.setThirdPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q1150).getThirdPeriodPowerReduction());
					} else {
						localResult.setFirstPeriodPowerReduction(ZERO);
						localResult.setSecondPeriodPowerReduction(ZERO);
						localResult.setThirdPeriodPowerReduction(ZERO);
					}
				}
			} else {
				// NO to 3730
				localResult.setFirstPeriodPowerReduction(ZERO);
				localResult.setSecondPeriodPowerReduction(ZERO);
				localResult.setThirdPeriodPowerReduction(ZERO);
			}
			// add localResult to return list
			result.add(day.ordinal(),localResult);
		}

		return result;
	}

	//specific for 3750
	private List<ReductionDTO> computeReductionForQuestionCode3750(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Map <QuestionCode,ReductionDTO> potentialReductionDetails) {


		List<ReductionDTO> result = new ArrayList<ReductionDTO>();

		Double value = ZERO;

		Double GlobalActionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3211).get(Period.FIRST).getStringValue());

		//
		Double reductionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3711).get(Period.FIRST).getStringValue());

		play.Logger.debug("Number of days : " + reductionDaysNumber.intValue());

		for (ReductionDay day : ReductionDay.values()) {
			ReductionDTO localResult = new ReductionDTO();

			if ( ((byQuestionCodeAndPeriod.get(QuestionCode.Q3750).get(Period.FIRST).getBooleanValue()!=null) && (byQuestionCodeAndPeriod.get(QuestionCode.Q3750).get(Period.FIRST).getBooleanValue()) )) {
				if ( ((day.ordinal()+1) <= reductionDaysNumber.intValue()) ) {
					// YES to 3730 and day is one of the repeat actions one
					//play.Logger.debug("DAY: " + day.name() + " set to ZER0 ");
					localResult.setFirstPeriodPowerReduction(ZERO);
					localResult.setSecondPeriodPowerReduction(ZERO);
					localResult.setThirdPeriodPowerReduction(ZERO);
				} else {
					if ((day.ordinal()+1) > GlobalActionDaysNumber) {
						//play.Logger.debug("DAY: " + day.name() + " set to value ");
						localResult.setFirstPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q2030).getFirstPeriodPowerReduction());
						localResult.setSecondPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q2030).getSecondPeriodPowerReduction());
						localResult.setThirdPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q2030).getThirdPeriodPowerReduction());
					} else {
						localResult.setFirstPeriodPowerReduction(ZERO);
						localResult.setSecondPeriodPowerReduction(ZERO);
						localResult.setThirdPeriodPowerReduction(ZERO);
					}
				}
			} else {
				// NO to 3730
				localResult.setFirstPeriodPowerReduction(ZERO);
				localResult.setSecondPeriodPowerReduction(ZERO);
				localResult.setThirdPeriodPowerReduction(ZERO);
			}
			// add localResult to return list
			result.add(day.ordinal(),localResult);
		}

		return result;
	}

	//specific for 3760
	private List<ReductionDTO> computeReductionForQuestionCode3760(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Map <QuestionCode,ReductionDTO> potentialReductionDetails) {


		List<ReductionDTO> result = new ArrayList<ReductionDTO>();

		Double value = ZERO;

		Double GlobalActionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3211).get(Period.FIRST).getStringValue());
		//
		Double reductionDaysNumber = Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q3711).get(Period.FIRST).getStringValue());

		play.Logger.debug("Number of days : " + reductionDaysNumber.intValue());

		for (ReductionDay day : ReductionDay.values()) {
			ReductionDTO localResult = new ReductionDTO();

			if ( ((byQuestionCodeAndPeriod.get(QuestionCode.Q3760).get(Period.FIRST).getBooleanValue()!=null) && (byQuestionCodeAndPeriod.get(QuestionCode.Q3760).get(Period.FIRST).getBooleanValue()) )) {
				if ( ((day.ordinal()+1) <= reductionDaysNumber.intValue()) ) {
					// YES to 3730 and day is one of the repeat actions one
					//play.Logger.debug("DAY: " + day.name() + " set to ZER0 ");
					localResult.setFirstPeriodPowerReduction(ZERO);
					localResult.setSecondPeriodPowerReduction(ZERO);
					localResult.setThirdPeriodPowerReduction(ZERO);
				} else {
					if ((day.ordinal()+1) > GlobalActionDaysNumber) {
						//play.Logger.debug("DAY: " + day.name() + " set to value ");
						localResult.setFirstPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q2040).getFirstPeriodPowerReduction());
						localResult.setSecondPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q2040).getSecondPeriodPowerReduction());
						localResult.setThirdPeriodPowerReduction(potentialReductionDetails.get(QuestionCode.Q2040).getThirdPeriodPowerReduction());
					} else {
						localResult.setFirstPeriodPowerReduction(ZERO);
						localResult.setSecondPeriodPowerReduction(ZERO);
						localResult.setThirdPeriodPowerReduction(ZERO);
					}
				}
			} else {
				// NO to 3730
				localResult.setFirstPeriodPowerReduction(ZERO);
				localResult.setSecondPeriodPowerReduction(ZERO);
				localResult.setThirdPeriodPowerReduction(ZERO);
			}
			// add localResult to return list
			result.add(day.ordinal(),localResult);
		}

		return result;
	}



	/*
	* Dump to map
	*/

	private void dumpEffectiveReductionDetailsMap (Map <QuestionCode,List<ReductionDTO>> localMapByQuestionCode) {
		play.Logger.debug("Entering -> dumpEffectiveReductionDetailsMap");

		for (Map.Entry <QuestionCode,List<ReductionDTO>> item : localMapByQuestionCode.entrySet()) {
			QuestionCode key = item.getKey();
			List<ReductionDTO> value = item.getValue();
			play.Logger.debug("DUMP: QuestionCode [" + key + "]");

			for (ReductionDay day : ReductionDay.values()) {

				ReductionDTO reductionDTO = value.get(day.ordinal());

				play.Logger.debug(">>>DUMP: Day [" + day.name() + "]");
				play.Logger.debug(">>>DUMP: Values F[" + reductionDTO.getFirstPeriodPowerReduction() + "]S[" + reductionDTO.getSecondPeriodPowerReduction() + "]T[" + reductionDTO.getThirdPeriodPowerReduction() + "]");
			}
		}
		play.Logger.debug("Quitting -> dumpEffectiveReductionDetailsMap");
	}



	/*************** POTENTIAL REDUCTION ************/

	private ReductionDTO calculatePotentialSummary(Map <QuestionCode,ReductionDTO> potentialReductionDetails) {

		ReductionDTO potentialReductionSummary = new ReductionDTO();

		Double firstPeriodTotal = ZERO;
		Double secondPeriodTotal = ZERO;
		Double thirdPeriodTotal = ZERO;

		// Perform sum
		for (Map.Entry<QuestionCode, ReductionDTO> item : potentialReductionDetails.entrySet()) {
			QuestionCode key = item.getKey();
			ReductionDTO value = item.getValue();

			play.Logger.debug(">> Summary : [" + key.name() + "]");
			play.Logger.debug(">>>>>> First : [" + value.getFirstPeriodPowerReduction() + "]");
			play.Logger.debug(">>>>>> Secon : [" + value.getSecondPeriodPowerReduction() + "]");
			play.Logger.debug(">>>>>> Third : [" + value.getThirdPeriodPowerReduction() + "]");
			firstPeriodTotal+=value.getFirstPeriodPowerReduction();
			secondPeriodTotal+=value.getSecondPeriodPowerReduction();
			thirdPeriodTotal+=value.getThirdPeriodPowerReduction();
		}

		// set up return object's values
		potentialReductionSummary.setFirstPeriodPowerReduction(firstPeriodTotal);
		potentialReductionSummary.setSecondPeriodPowerReduction(secondPeriodTotal);
		potentialReductionSummary.setThirdPeriodPowerReduction(thirdPeriodTotal);

		Double AllPeriodsTotal =  firstPeriodTotal+secondPeriodTotal+thirdPeriodTotal;

		potentialReductionSummary.setAveragePowerReduction(AllPeriodsTotal/HOURSRANGE);
		potentialReductionSummary.setEnergyReduction((potentialReductionSummary.getAveragePowerReduction()*HOURSRANGE)/THOUSAND);

		return (potentialReductionSummary);
	}


	private Map <QuestionCode,ReductionDTO> calculatePotentialReductionDetails(List<AnswerDTO> surveyAnswers) {

		Map <QuestionCode,ReductionDTO> reductionDetails = new HashMap <QuestionCode,ReductionDTO>();
		ReductionDTO reductionSummary = new ReductionDTO();

		Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod = convertToMap(surveyAnswers);


		// ******************** Gros electromenager **********************************
		// 1110
		reductionDetails.put(QuestionCode.Q1110,computeReductionForQuestionCodeAlgo0(QuestionCode.Q1110, byQuestionCodeAndPeriod));
		// 1120
		reductionDetails.put(QuestionCode.Q1120,computeReductionForQuestionCodeAlgo0(QuestionCode.Q1120, byQuestionCodeAndPeriod));
		// 1130
		reductionDetails.put(QuestionCode.Q1130,computeReductionForQuestionCodeAlgo0(QuestionCode.Q1130, byQuestionCodeAndPeriod));

		// ******************** Chauffage et eau chaude ******************************
		// 1600
		reductionDetails.put(QuestionCode.Q1600,computeReductionForQuestionCodeAlgo3(QuestionCode.Q1600, byQuestionCodeAndPeriod));
		// 1900
		reductionDetails.put(QuestionCode.Q1900,computeReductionForQuestionCodeAlg4(QuestionCode.Q1900, byQuestionCodeAndPeriod));
		// 1210
		reductionDetails.put(QuestionCode.Q1210,computeReductionForQuestionCode1210(byQuestionCodeAndPeriod));

		// ******************** Eclairage et electromenager ***************************
		// 1160
		reductionDetails.put(QuestionCode.Q1160,computeReductionForQuestionCodeAlgo0(QuestionCode.Q1160, byQuestionCodeAndPeriod));
		// 1120
		reductionDetails.put(QuestionCode.Q1220,computeReductionForQuestionCodeAlgo1(QuestionCode.Q1220,byQuestionCodeAndPeriod));
		// 1230
		reductionDetails.put(QuestionCode.Q1230,computeReductionForQuestionCodeAlgo1(QuestionCode.Q1230,byQuestionCodeAndPeriod));
		// 1700 - special
		reductionDetails.put(QuestionCode.Q1700,computeReductionForQuestionCode1700(byQuestionCodeAndPeriod));
		// 1750 - special
		reductionDetails.put(QuestionCode.Q1750,computeReductionForQuestionCode1750(byQuestionCodeAndPeriod));
		// 1800 - special
		reductionDetails.put(QuestionCode.Q1800,computeReductionForQuestionCodeAlg4(QuestionCode.Q1800, byQuestionCodeAndPeriod));
		// 2010 - special
		reductionDetails.put(QuestionCode.Q2010,computeReductionForQuestionCodeAlgo2(QuestionCode.Q2010,byQuestionCodeAndPeriod));
		// 2020 - special
		reductionDetails.put(QuestionCode.Q2020,computeReductionForQuestionCodeAlgo2(QuestionCode.Q2020,byQuestionCodeAndPeriod));
		// 2030 - special
		reductionDetails.put(QuestionCode.Q2030,computeReductionForQuestionCodeAlgo2(QuestionCode.Q2030,byQuestionCodeAndPeriod));
		// 2040 - special
		reductionDetails.put(QuestionCode.Q2040,computeReductionForQuestionCode2040(byQuestionCodeAndPeriod));
		// 1235 - special
		reductionDetails.put(QuestionCode.Q1235,computeReductionForQuestionCode1235(byQuestionCodeAndPeriod));


		// ******************** Repas ***************************
		// 1140 - special
		reductionDetails.put(QuestionCode.Q1140,computeReductionForQuestionCode1140(byQuestionCodeAndPeriod));
		// 1150
		reductionDetails.put(QuestionCode.Q1150,computeReductionForQuestionCodeAlgo0(QuestionCode.Q1150, byQuestionCodeAndPeriod));

		// please keep in mind to add adjustment factor - TODO
		//reductionDetails.put("ADJUSTMENT,adjustmentFactor());

		return reductionDetails;
	}


	/************************ UTIL methods ******************************/

	/*
	* Dump to map
	*/

	private void dumpMap (Map<QuestionCode,Map<Period,AnswerValueDTO>> localMapByQuestionCode) {
		play.Logger.debug("Entering -> dumpMap");

		for (Map.Entry <QuestionCode,Map<Period,AnswerValueDTO>> item : localMapByQuestionCode.entrySet()) {
			QuestionCode key = item.getKey();
			Map<Period,AnswerValueDTO> value = item.getValue();
			play.Logger.debug("DUMP: QuestionCode [" + key + "]");

			for (Map.Entry <Period,AnswerValueDTO> detail : item.getValue().entrySet()) {
				Period periodKey;
				if (detail.getKey()!=null) {
					periodKey = Period.valueOf(detail.getKey().name());
				} else {
					periodKey = detail.getKey();
				}
				AnswerValueDTO avDTO = detail.getValue();
				play.Logger.debug(">>>DUMP: Period [" + periodKey + "]");
				play.Logger.debug(">>>DUMP: Values D[" + avDTO.getDoubleValue() + "]S[" + avDTO.getStringValue() + "]B[" + avDTO.getBooleanValue() + "]");
			}
		}
		play.Logger.debug("Quitting -> dumpMap");
	}

	/*
	* Convert to map
	*/


	private Map<QuestionCode,Map<Period,AnswerValueDTO>> convertToMap (List<AnswerDTO> surveyAnswers) {

		//play.Logger.debug("Entering -> convertToMap");
		Map<QuestionCode,Map<Period,AnswerValueDTO>> localMapByQuestionCode = new HashMap<QuestionCode,Map<Period,AnswerValueDTO>> ();


		// keep for all answers and generate map
		for (AnswerDTO answer : surveyAnswers) {

			Map localMapByPeriod = new HashMap<Period,AnswerValueDTO> ();

			//play.Logger.debug("into 1st loop - QuestionCode : [" + answer.getQuestionKey() + "]");
			//play.Logger.debug("into 1st loop - Period : [" + answer.getPeriodKey() + "]");
			//play.Logger.debug("into 1st loop - StringValue : [" + answer.getAnswerValues().get(0).getStringValue() + "]");
			//play.Logger.debug("into 1st loop - DoubleValue : [" + answer.getAnswerValues().get(0).getDoubleValue() + "]");
			//play.Logger.debug("into 1st loop - BooleanValue : [" + answer.getAnswerValues().get(0).getBooleanValue() + "]");

			// check if QuestionCode already into main key map
			if (localMapByQuestionCode.containsKey(QuestionCode.valueOf(answer.getQuestionKey()))) {
				// QuestionCode already present
				localMapByPeriod = localMapByQuestionCode.get(QuestionCode.valueOf(answer.getQuestionKey()));
			}

			if (answer.getPeriodKey()==null) {
				// assume Period.FIRST in case no period specified.
				localMapByPeriod.put(Period.FIRST, answer.getAnswerValues().get(0));
			} else {
				//play.Logger.debug("Pushing :" + answer.getPeriodKey());
				localMapByPeriod.put(Period.valueOf(answer.getPeriodKey()), answer.getAnswerValues().get(0));
			}
			localMapByQuestionCode.put(QuestionCode.valueOf(answer.getQuestionKey()), localMapByPeriod);
		}

		//play.Logger.debug("Quitting -> convertToMap");
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
	private ReductionDTO computeReductionForQuestionCodeAlgo0(QuestionCode questionCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		//play.Logger.debug("Processing QuestionCode: " + questionCode.name());

		result.setFirstPeriodPowerReduction(
				(questionCode.getNominalPower() *
				  Double.parseDouble(byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getStringValue())
				) / WORKING_DAYS_BY_WEEK);

		result.setSecondPeriodPowerReduction(
				(questionCode.getNominalPower() *
				  Double.parseDouble(byQuestionCodeAndPeriod.get(questionCode).get(Period.SECOND).getStringValue())
				) / WORKING_DAYS_BY_WEEK);

		result.setThirdPeriodPowerReduction(
				(questionCode.getNominalPower() *
					Double.parseDouble(byQuestionCodeAndPeriod.get(questionCode).get(Period.THIRD).getStringValue())
				) / WORKING_DAYS_BY_WEEK);


		return result;
	}

	//specific for 1600, 1220, 1230
	private ReductionDTO computeReductionForQuestionCodeAlgo1(QuestionCode questionCode,Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =
				(questionCode.getNominalPower() *
						Double.parseDouble(byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getStringValue())
				);

		result.setFirstPeriodPowerReduction(value);
		result.setSecondPeriodPowerReduction(value);
		result.setThirdPeriodPowerReduction(value);

		return result;
	}

	//specific for 1600
	private ReductionDTO computeReductionForQuestionCodeAlgo3(QuestionCode questionCode,Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

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
	private ReductionDTO computeReductionForQuestionCode1210(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =
				(
				  (QuestionCode.Q1210.getNominalPower() *
						Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1210).get(Period.FIRST).getStringValue())
				  ) *   byQuestionCodeAndPeriod.get(QuestionCode.Q1300).get(Period.FIRST).getDoubleValue() // nb of people in house
				) / HALF;

		result.setFirstPeriodPowerReduction(value);
		result.setSecondPeriodPowerReduction(value);
		result.setThirdPeriodPowerReduction(value);

		return result;
	}

	//set result values according human presence
	private ReductionDTO evaluateAccordingHumanPresence (Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod, Double value) {

		ReductionDTO result = new ReductionDTO();

		if (Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getStringValue())>-1) { // if someone house between 17h00 and 18h00
			result.setFirstPeriodPowerReduction(value);
			result.setSecondPeriodPowerReduction(value);
			result.setThirdPeriodPowerReduction(value);
		}

		if (Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getStringValue())>0) { // if someone house between 18h00 and 19h00
			result.setFirstPeriodPowerReduction(ZERO);
			result.setSecondPeriodPowerReduction(value);
			result.setThirdPeriodPowerReduction(value);
		}

		if (Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1500).get(Period.FIRST).getStringValue())>1) { // if someone house between 19h00 and 20h00
			result.setFirstPeriodPowerReduction(ZERO);
			result.setSecondPeriodPowerReduction(ZERO);
			result.setThirdPeriodPowerReduction(value);
		}

		return result;
	}

	//specific for 1700
	private ReductionDTO computeReductionForQuestionCode1700(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {


		Double value = ZERO;

		value =
				(QuestionCode.Q1700.getNominalPower() *
						Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1700).get(Period.FIRST).getStringValue())
				) / WORKING_DAYS_BY_WEEK;


		ReductionDTO result = evaluateAccordingHumanPresence (byQuestionCodeAndPeriod,value);

		return result;
	}


	//specific for 1800, 1900
	private ReductionDTO computeReductionForQuestionCodeAlg4(QuestionCode questionCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		ReductionDTO result = new ReductionDTO();

		Double value = ZERO;

		value =		byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getDoubleValue();


		result.setFirstPeriodPowerReduction(value);
		result.setSecondPeriodPowerReduction(value);
		result.setThirdPeriodPowerReduction(value);

		return result;
	}

	//specific for 2010, 2020, 2030
	private ReductionDTO computeReductionForQuestionCodeAlgo2(QuestionCode questionCode, Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		Double value = ZERO;

		value =
				(
					(questionCode.getNominalPower() *
								Double.parseDouble(byQuestionCodeAndPeriod.get(questionCode).get(Period.FIRST).getStringValue()))

			) / THREEQUARTER;

		ReductionDTO result = evaluateAccordingHumanPresence (byQuestionCodeAndPeriod,value);

		return result;
	}


	//specific for 1750
	private ReductionDTO computeReductionForQuestionCode1750(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		Double value = ZERO;

		value =
				(
						(	QuestionCode.Q1750.getNominalPower()
							* ((Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1750).get(Period.FIRST).getStringValue())) / FOUR)
						)
							* (byQuestionCodeAndPeriod.get(QuestionCode.Q1300).get(Period.FIRST).getDoubleValue() / HALF) // nb of people in house
				);

		ReductionDTO result = evaluateAccordingHumanPresence (byQuestionCodeAndPeriod,value);

		return result;
	}


	//specific for 2040
	private ReductionDTO computeReductionForQuestionCode2040(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		Double value = ZERO;

		value =
				(
						(QuestionCode.Q2040.getNominalPower() *
								Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q2040).get(Period.FIRST).getStringValue()))

				) / THREEQUARTERDOUBLE;

		ReductionDTO result = evaluateAccordingHumanPresence (byQuestionCodeAndPeriod,value);

		return result;
	}

	//specific for 1235
	private ReductionDTO computeReductionForQuestionCode1235(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		Double value = ZERO;

		value =
				(
						(QuestionCode.Q1235.getNominalPower() *
								Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1235).get(Period.FIRST).getStringValue()))

				);

		ReductionDTO result = evaluateAccordingHumanPresence (byQuestionCodeAndPeriod,value);

		return result;
	}

	//specific for 1140
	private ReductionDTO computeReductionForQuestionCode1140(Map<QuestionCode,Map<Period,AnswerValueDTO>> byQuestionCodeAndPeriod) {

		Double firstPeriod;
		Double secondPeriod;
		Double thirdPeriod;

		ReductionDTO result = new ReductionDTO();


		firstPeriod =
				(
						(
								(QuestionCode.Q1140.getNominalPower() *
								 Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1140).get(Period.FIRST).getStringValue()))
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
										Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1140).get(Period.SECOND).getStringValue()))
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
										Double.parseDouble(byQuestionCodeAndPeriod.get(QuestionCode.Q1140).get(Period.THIRD).getStringValue()))
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
