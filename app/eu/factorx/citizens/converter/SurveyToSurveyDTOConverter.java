package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.AnswerDTO;
import eu.factorx.citizens.dto.SurveyDTO;
import eu.factorx.citizens.model.survey.Answer;
import eu.factorx.citizens.model.survey.Survey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 28/11/14.
 */
public class SurveyToSurveyDTOConverter implements Converter<Survey, SurveyDTO> {

    private AccountToAccountDTOConverter accountToAccountDTOConverter = new AccountToAccountDTOConverter();
    private AnswerToAnswerDTOConverter answerToAnswerDTOConverter = new AnswerToAnswerDTOConverter();


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

        return dto;
    }
}
