package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.ActionAnswerDTO;
import eu.factorx.citizens.model.survey.ActionAnswer;
import org.joda.time.Duration;

public class ActionAnswerToActionAnswerDTOConverter implements Converter<ActionAnswer, ActionAnswerDTO> {

	@Override
	public ActionAnswerDTO convert(ActionAnswer answer) {

		ActionAnswerDTO dto = new ActionAnswerDTO();

		dto.setQuestionKey(answer.getQuestionCode().name());

		dto.setTitle(answer.getTitle());
		dto.setPower(answer.getPowerReduction());
		dto.setBegin(answer.getStartTime().toString("HH:mm"));
		Duration millis = Duration.millis(answer.getDuration());
		dto.setDuration(millis.getStandardHours() + ":" + millis.getStandardMinutes());
		dto.setDescription(answer.getComment());

		return dto;
	}
}
