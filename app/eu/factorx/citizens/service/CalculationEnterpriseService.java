package eu.factorx.citizens.service;

import eu.factorx.citizens.dto.*;

import java.util.List;

public interface CalculationEnterpriseService {

	EnterpriseEffectiveReductionDTO calculateEffectiveReduction(List<ActionAnswerDTO> actionAnswers);

}
