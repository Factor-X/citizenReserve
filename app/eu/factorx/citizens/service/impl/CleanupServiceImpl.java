package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.converter.AnswerToAnswerDTOConverter;
import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.ReductionDTO;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.batch.BatchResult;
import eu.factorx.citizens.model.batch.BatchResultSet;
import eu.factorx.citizens.model.batch.PowerReductionType;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.AnswerValue;
import eu.factorx.citizens.model.survey.Period;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.technical.AbstractEntity;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.model.type.ReductionDay;
import eu.factorx.citizens.service.BatchService;
import eu.factorx.citizens.service.CalculationService;
import eu.factorx.citizens.service.CleanupService;
import eu.factorx.citizens.service.SurveyService;
import play.Logger;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CleanupServiceImpl implements CleanupService {

    private SurveyService surveyService = new SurveyServiceImpl();

    @Override
	@Transactional
	public void run() {
		List<Survey> notDeleted = surveyService.findAllSurveys();
		for (Survey survey : notDeleted) {
			Account account = survey.getAccount();
			Logger.info("cleaning up account #" + account.getId() + " (" + account.getEmail() + ") ...");
			List<Survey> allSurveysDeleted = surveyService.findDeletedSurveysByAccountLastDeletedFirst(account);
			for (int i = 1; i < allSurveysDeleted.size(); i++) {
				allSurveysDeleted.get(i).delete();
			}
			Logger.info("cleaning up account #" + account.getId() + " (" + account.getEmail() + ") done.");
		}
	}

}
