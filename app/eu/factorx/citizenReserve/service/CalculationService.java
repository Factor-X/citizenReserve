package eu.factorx.citizenReserve.service;

import eu.factorx.citizenReserve.dto.AnswerDTO;
import eu.factorx.citizenReserve.dto.ReductionDTO;

import java.util.List;

public interface CalculationService {

    ReductionDTO calculatePotentialReduction(List<AnswerDTO> surveyAnswers);

    ReductionDTO calculateEffectiveReduction(List<AnswerDTO> surveyAnswers);

}
