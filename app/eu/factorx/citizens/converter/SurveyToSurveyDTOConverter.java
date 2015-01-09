package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.ActionAnswerDTO;
import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.SurveyDTO;
import eu.factorx.citizens.model.survey.ActionAnswer;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.Survey;

import java.util.ArrayList;
import java.util.List;

public class SurveyToSurveyDTOConverter implements Converter<Survey, SurveyDTO> {

	private AccountToAccountDTOConverter accountToAccountDTOConverter = new AccountToAccountDTOConverter();
	private AnswerToAnswerDTOConverter answerToAnswerDTOConverter = new AnswerToAnswerDTOConverter();
	private ActionAnswerToActionAnswerDTOConverter actionAnswerToActionAnswerDTOConverter = new ActionAnswerToActionAnswerDTOConverter();


	@Override
	public SurveyDTO convert(Survey survey) {

		SurveyDTO dto = new SurveyDTO();

		dto.setAccount(accountToAccountDTOConverter.convert(survey.getAccount()));

		List<AnswerDTO> answerDTOList = new ArrayList<>();
		if (survey.getAnswers() != null) {
			for (Answer answer : survey.getAnswers()) {
				answerDTOList.add(answerToAnswerDTOConverter.convert(answer));
			}
		}
		dto.setAnswers(answerDTOList);

		List<ActionAnswerDTO> actionAnswerDTOList = new ArrayList<>();
		List<ActionAnswer> actionAnswers = survey.getActionAnswers();
		if (actionAnswers != null) {
			for (ActionAnswer actionAnswer : actionAnswers) {
				actionAnswerDTOList.add(actionAnswerToActionAnswerDTOConverter.convert(actionAnswer));
			}
		}
		dto.setActionAnswers(actionAnswerDTOList);

		return dto;
	}
}
