package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.shedding.SheddingRiskAnswer;
import eu.factorx.citizens.service.SheddingRiskAnswerService;

public class SheddingRiskAnswerServiceImpl implements SheddingRiskAnswerService {

	@Override
	public SheddingRiskAnswer findByUUID(String uuid) {
		return Ebean.find(SheddingRiskAnswer.class).where().eq(SheddingRiskAnswer.PROPERTY_UUID, uuid).findUnique();
	}

	@Override
	public SheddingRiskAnswer save(SheddingRiskAnswer sheddingRiskAnswer) {
		Ebean.save(sheddingRiskAnswer);
		return sheddingRiskAnswer;
	}

	@Override
	public SheddingRiskAnswer update(SheddingRiskAnswer sheddingRiskAnswer) {
		Ebean.update(sheddingRiskAnswer);
		return sheddingRiskAnswer;
	}
}
