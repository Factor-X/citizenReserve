package eu.factorx.citizens.service;

import eu.factorx.citizens.model.shedding.SheddingRiskAnswer;

public interface SheddingRiskAnswerService {

	SheddingRiskAnswer findByUUID(String uuid);

	SheddingRiskAnswer save(SheddingRiskAnswer sheddingRiskAnswer);

	SheddingRiskAnswer update(SheddingRiskAnswer sheddingRiskAnswer);

}
