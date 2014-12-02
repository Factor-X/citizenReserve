package eu.factorx.citizens.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.citizens.controllers.AbstractController;
import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.AnswerValueDTO;
import eu.factorx.citizens.dto.ReductionDTO;
import eu.factorx.citizens.dto.SurveyDTO;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.service.CalculationService;
import eu.factorx.citizens.service.impl.CalculationServiceImpl;
import play.api.Play;
import play.mvc.BodyParser;
import play.mvc.Result;

import java.util.List;

public class CalculationController extends AbstractController {


	//service
	CalculationService calculationService = new CalculationServiceImpl();

	//@BodyParser.Of(BodyParser.Json.class)
	public Result calculatePotentialReduction() {

		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("empty json");
		}
		//play.Logger.debug(json.asText());


		SurveyDTO survey = extractDTOFromRequest(SurveyDTO.class);

		// Validate incoming DTO - TODO
		try {
			calculationService.validateData(survey.getAnswers());
		} catch (Exception e) {
			//throw new MyRuntimeException("This answerValue is not savable : " + answerValueDTO + " (from answer " + answerDTO + ")");
		}

		// calculate potential reduction
		ReductionDTO result = calculationService.calculatePotentialReduction(survey.getAnswers());

		play.Logger.debug("Potential Reduction FIRST:" + result.getFirstPeriodPowerReduction());
		play.Logger.debug("Potential Reduction SECOND:" + result.getSecondPeriodPowerReduction());
		play.Logger.debug("Potential Reduction THIRD:" + result.getThirdPeriodPowerReduction());

		play.Logger.debug("Potential Reduction Average:" + result.getAveragePowerReduction());
		play.Logger.debug("Potential Reduction Energy ratio:" + result.getEnergyReduction());

		return ok(result);

    } // end of action calculate potential reduction

	// TODO - to be implemented
	@BodyParser.Of(BodyParser.Json.class)
	List<ReductionDTO> calculateEffectiveReduction (SurveyDTO dto, Account account) {

		// Validate incoming DTO - TODO
		try {
			calculationService.validateData(dto.getAnswers());
		} catch (Exception e) {
			//throw new MyRuntimeException("This answerValue is not savable : " + answerValueDTO + " (from answer " + answerDTO + ")");
		}

		List<ReductionDTO> result = calculationService.calculateEffectiveReduction(dto.getAnswers());

		return (result);

	} // end of action calculate effective reduction

}  // end of controller
