package eu.factorx.citizens.service;

import eu.factorx.citizens.model.shedding.SheddingRisk;

import java.util.List;

public interface SheddingRiskService {

	SheddingRisk findById(Long id);

	List<SheddingRisk> findAll();

	SheddingRisk save(SheddingRisk sheddingRisk);

	SheddingRisk update(SheddingRisk sheddingRisk);
}
