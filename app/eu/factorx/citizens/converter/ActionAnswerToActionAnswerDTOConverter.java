package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.ActionAnswerDTO;
import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.model.survey.ActionAnswer;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.AnswerValue;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormatter;

public class ActionAnswerToActionAnswerDTOConverter implements Converter<ActionAnswer, ActionAnswerDTO> {

    @Override
    public ActionAnswerDTO convert(ActionAnswer answer) {

		ActionAnswerDTO dto = new ActionAnswerDTO();

        dto.setQuestionKey(answer.getQuestionCode().name());

		dto.setTitle(answer.getTitle());
		dto.setPower(answer.getPowerReduction());
		dto.setBegin(answer.getStartTime().toString("HH:mm"));
		long totalMinutes = Duration.millis(answer.getDuration()).getStandardMinutes();
		dto.setDuration(String.format("%02d:%02d", totalMinutes / 60L, totalMinutes % 60L));
		dto.setDescription(answer.getComment());

        return dto;
    }
}
