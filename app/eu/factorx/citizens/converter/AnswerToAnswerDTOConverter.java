package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.AnswerValue;

/**
 * Created by florian on 28/11/14.
 */
public class AnswerToAnswerDTOConverter implements Converter<Answer, AnswerDTO> {

    @Override
    public AnswerDTO convert(Answer answer) {

        AnswerDTO dto = new AnswerDTO();

        if (answer.getPeriod() != null) {
            dto.setPeriodKey(answer.getPeriod().toString());
        }
        dto.setQuestionKey(answer.getQuestionCode().name());

        if (answer.getAnswerValues() != null) {
            for (AnswerValue answerValue : answer.getAnswerValues()) {
                if (answerValue.getBooleanValue() != null) {
                    dto.addBooleanValue(answerValue.getBooleanValue());
                } else if (answerValue.getDoubleValue() != null) {
                    dto.addDoubleValue(answerValue.getDoubleValue());
                } else if (answerValue.getStringValue() != null) {
                    dto.addStringValue(answerValue.getStringValue());
                }
            }
        }

        return dto;
    }
}
