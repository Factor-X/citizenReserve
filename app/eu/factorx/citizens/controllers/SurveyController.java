package eu.factorx.citizens.controllers;

import eu.factorx.citizens.controllers.technical.AbstractController;
import eu.factorx.citizens.controllers.technical.SecuredController;
import eu.factorx.citizens.dto.*;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import play.db.ebean.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Date;

public class SurveyController extends AbstractController {

    //service
    private SurveyService surveyService = new SurveyServiceImpl();

	private CalculationController calculationController = new CalculationController();

    @Transactional
    @Security.Authenticated(SecuredController.class)
    public Result updateSurvey() {

        SurveyDTO dto = extractDTOFromRequest(SurveyDTO.class);

        saveSurvey(dto, securedController.getCurrentUser());

        return ok(new ResultDTO());
    }


    /*package*/ void saveSurvey(SurveyDTO dto, Account account) {

        //delete the last survey
        Survey lastValidSurvey = surveyService.findValidSurveyByAccount(account);

        if (lastValidSurvey != null) {
            lastValidSurvey.setDeletionDate(new Date());

            surveyService.saveSurvey(lastValidSurvey);
        }

        Survey survey = new Survey(account);

        for (AnswerDTO answerDTO : dto.getAnswers()) {

            //create answer
            Answer answer = new Answer();
            answer.setPeriod(getPeriodByPeriodKey(answerDTO.getPeriodKey()));

            for (AnswerValueDTO answerValueDTO : answerDTO.getAnswerValues()) {
                if (answerValueDTO.getBooleanValue() != null) {
                    survey.addAnswer(getQuestionCodeByQuestionKey(answerDTO.getQuestionKey()), getPeriodByPeriodKey(answerDTO.getPeriodKey()), answerValueDTO.getBooleanValue());
                } else if (answerValueDTO.getDoubleValue() != null) {
                    survey.addAnswer(getQuestionCodeByQuestionKey(answerDTO.getQuestionKey()), getPeriodByPeriodKey(answerDTO.getPeriodKey()), answerValueDTO.getDoubleValue());
                } else if (answerValueDTO.getStringValue() != null) {
                    survey.addAnswer(getQuestionCodeByQuestionKey(answerDTO.getQuestionKey()), getPeriodByPeriodKey(answerDTO.getPeriodKey()), answerValueDTO.getStringValue());
                } else {
                    //throw new MyRuntimeException("This answerValue is not savable : " + answerValueDTO + " (from answer " + answerDTO + ")");
                }
            }

        }

		// For legacy accounts:
		// If we can calculate an effective reduction, then user is a "normal" account => we can put the value of 'legacyAccountPowerReduction' to 0
		Double legacyAccountPowerReduction = account.getLegacyAccountPowerReduction();
		if (legacyAccountPowerReduction != null && legacyAccountPowerReduction > 0) {
			EffectiveReductionDTO effectiveReductionDTO = null;
			try {
				effectiveReductionDTO = calculationController.getEffectiveReductionDTO(dto);
				if ((effectiveReductionDTO != null) && (effectiveReductionDTO.getReductions().get(0).getAveragePowerReduction() > 0)) {
					account.setLegacyAccountPowerReduction(null);
				}
			} catch (Exception e) {}
		}

        surveyService.saveSurvey(survey);
    }

    public Result getGlobalReductionData() {
        //BatchResult batchResult = Ebean.find(Batch.cl)
        return ok();
    }

    public Result getNbSurveys() {
        return ok(new DoubleDTO(new Double(surveyService.countSurveys())));
    }
}
