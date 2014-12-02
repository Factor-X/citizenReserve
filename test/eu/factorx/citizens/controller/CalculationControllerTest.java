package eu.factorx.citizens.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.factorx.citizens.controllers.routes;
import eu.factorx.citizens.dto.*;
import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.model.type.ReductionDay;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Test;


public class CalculationControllerTest {

		@Test
		public void _001_calculatePotentialReductionActionSuccess() {


			// prepare account data
			AccountDTO account= new AccountDTO();
			account.setFirstName("gaston");
			account.setLastName("hollands");
			account.setEmail("gaston.hollands@factorx.eu");

			// prepare answers data
			List<AnswerDTO> answers = prepareData();

			SurveyDTO survey = new SurveyDTO ();
			survey.setAccount(account);
			survey.setAnswers(answers);

			// ConnectionFormDTO
			//ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

			//Json node
			JsonNode node = Json.toJson(survey);

			// perform save
			// Fake request

			// Fake request
			FakeRequest fr = new FakeRequest();
			fr.withHeader("Content-type", "application/json");
			fr.withJsonBody(node);

			// Call controller action
			Result result = callAction(
					routes.ref.CalculationController.calculatePotentialReduction(),
					fr
			); // callAction

			// test results

			// expecting an HTTP 200 return code
			assertEquals(200, status(result));

			// get ReductionDTO
			Logger.info("results: " + new String(contentAsBytes(result)));
			String content = new String(contentAsBytes(result));
			JsonNode jsonResponse = Json.parse(content);

			ReductionDTO potentialReduction = ReductionDTO.getDTO(jsonResponse,ReductionDTO.class);

			play.Logger.debug("Potential Reduction FIRST:" + potentialReduction.getFirstPeriodPowerReduction());
			play.Logger.debug("Potential Reduction SECOND:" + potentialReduction.getSecondPeriodPowerReduction());
			play.Logger.debug("Potential Reduction THIRD:" + potentialReduction.getThirdPeriodPowerReduction());

			play.Logger.debug("Potential Reduction Average:" + potentialReduction.getAveragePowerReduction());
			play.Logger.debug("Potential Reduction Energy ratio:" + potentialReduction.getEnergyReduction());

//			System.out.println("Potential Reduction FIRST:" + potentialReduction.getFirstPeriodPowerReduction());
//			System.out.println("Potential Reduction SECOND:" + potentialReduction.getSecondPeriodPowerReduction());
//			System.out.println("Potential Reduction THIRD:" + potentialReduction.getThirdPeriodPowerReduction());
//
//			System.out.println("Potential Reduction Average:" + potentialReduction.getAveragePowerReduction());
//			System.out.println("Potential Reduction Energy ratio:" + potentialReduction.getEnergyReduction());

			Assert.assertEquals(new Double(375), potentialReduction.getFirstPeriodPowerReduction());
			Assert.assertEquals(new Double(1798), potentialReduction.getSecondPeriodPowerReduction());
			Assert.assertEquals(new Double(2678), potentialReduction.getThirdPeriodPowerReduction());
			Assert.assertEquals(new Double(1617), potentialReduction.getAveragePowerReduction());
			Assert.assertEquals(new Double(4.851), potentialReduction.getEnergyReduction());

		} // end of controller test method

	@Test
	public void _002_calculateEffectiveReductionActionSuccess() {


		// prepare account data
		AccountDTO account= new AccountDTO();
		account.setFirstName("gaston");
		account.setLastName("hollands");
		account.setEmail("gaston.hollands@factorx.eu");

		// prepare answers data
		List<AnswerDTO> answers = prepareData();

		SurveyDTO survey = new SurveyDTO ();
		survey.setAccount(account);
		survey.setAnswers(answers);

		// ConnectionFormDTO
		//ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		//Json node
		JsonNode node = Json.toJson(survey);

		// perform save
		// Fake request

		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				routes.ref.CalculationController.calculateEffectiveReduction(),
				fr
		); // callAction

		// test results

		// expecting an HTTP 200 return code
		assertEquals(200, status(result));

		// get EffectiveReductionDTO
		Logger.info("results: " + new String(contentAsBytes(result)));
		String content = new String(contentAsBytes(result));
		JsonNode jsonResponse = Json.parse(content);

		EffectiveReductionDTO effectiveReduction = EffectiveReductionDTO.getDTO(jsonResponse,EffectiveReductionDTO.class);

		for (ReductionDay day : ReductionDay.values()) {

			play.Logger.debug("DAY : " + day.ordinal());
			play.Logger.debug("Effective Reduction FIRST:" + effectiveReduction.getReductions().get(day.ordinal()).getFirstPeriodPowerReduction());
			play.Logger.debug("Effective Reduction SECOND:" + effectiveReduction.getReductions().get(day.ordinal()).getSecondPeriodPowerReduction());
			play.Logger.debug("Effective Reduction THIRD:" + effectiveReduction.getReductions().get(day.ordinal()).getThirdPeriodPowerReduction());
			play.Logger.debug("Effective Reduction Average:" + effectiveReduction.getReductions().get(day.ordinal()).getAveragePowerReduction());
			play.Logger.debug("Effective Reduction Energy ratio:" + effectiveReduction.getReductions().get(day.ordinal()).getEnergyReduction());
		}

		Assert.assertEquals(new Double(275), effectiveReduction.getReductions().get(ReductionDay.DAY1.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1698), effectiveReduction.getReductions().get(ReductionDay.DAY1.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2578), effectiveReduction.getReductions().get(ReductionDay.DAY1.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.getReductions().get(ReductionDay.DAY2.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1698), effectiveReduction.getReductions().get(ReductionDay.DAY2.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2578), effectiveReduction.getReductions().get(ReductionDay.DAY2.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.getReductions().get(ReductionDay.DAY3.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1590), effectiveReduction.getReductions().get(ReductionDay.DAY3.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2470), effectiveReduction.getReductions().get(ReductionDay.DAY3.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(275), effectiveReduction.getReductions().get(ReductionDay.DAY4.ordinal()).getFirstPeriodPowerReduction());
		Assert.assertEquals(new Double(1590), effectiveReduction.getReductions().get(ReductionDay.DAY4.ordinal()).getSecondPeriodPowerReduction());
		Assert.assertEquals(new Double(2470), effectiveReduction.getReductions().get(ReductionDay.DAY4.ordinal()).getThirdPeriodPowerReduction());

		Assert.assertEquals(new Double(1517), effectiveReduction.getReductions().get(ReductionDay.DAY1.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.551), effectiveReduction.getReductions().get(ReductionDay.DAY1.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1517), effectiveReduction.getReductions().get(ReductionDay.DAY2.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.551), effectiveReduction.getReductions().get(ReductionDay.DAY2.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1445), effectiveReduction.getReductions().get(ReductionDay.DAY3.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.335), effectiveReduction.getReductions().get(ReductionDay.DAY3.ordinal()).getEnergyReduction());

		Assert.assertEquals(new Double(1445), effectiveReduction.getReductions().get(ReductionDay.DAY4.ordinal()).getAveragePowerReduction());
		Assert.assertEquals(new Double(4.335), effectiveReduction.getReductions().get(ReductionDay.DAY4.ordinal()).getEnergyReduction());


	} // end of controller test method



	/*******************************************************************************/
		/**** TODO - next methods should be common to service and controller tests *****/
		/*******************************************************************************/

		List<AnswerDTO> prepareData () {

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

			return (answersDTOs);

		} // end of prepareData

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


	} // end of class
