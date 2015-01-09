package eu.factorx.citizens.controllers;

import eu.factorx.citizens.controllers.technical.AbstractController;
import eu.factorx.citizens.model.shedding.SheddingRiskAnswer;
import eu.factorx.citizens.service.SheddingRiskAnswerService;
import eu.factorx.citizens.service.impl.SheddingRiskAnswerServiceImpl;
import play.db.ebean.Transactional;
import play.mvc.Result;

public class SheddingRiskAnswerController extends AbstractController {

	private SheddingRiskAnswerService sheddingRiskAnswerService = new SheddingRiskAnswerServiceImpl();

	@Transactional
	public Result saveUserAnswer(String uuid, String answer) {

		response().setHeader("Access-Control-Allow-Origin", "*");
		response().setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
		response().setHeader("Access-Control-Max-Age", "3600");
		response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Auth-Token");
		response().setHeader("Access-Control-Allow-Credentials", "true");

		SheddingRiskAnswer sheddingRiskAnswer = sheddingRiskAnswerService.findByUUID(uuid);
		Boolean blAnswer = Boolean.valueOf(answer);
		if ((sheddingRiskAnswer == null) || (blAnswer == null)) {
			forbidden();
		}
		sheddingRiskAnswer.setAnswer(blAnswer);
		sheddingRiskAnswerService.update(sheddingRiskAnswer);
		return ok();
	}

}
