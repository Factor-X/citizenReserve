package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.shedding.SheddingRisk;
import eu.factorx.citizens.model.shedding.SheddingRiskAnswer;
import eu.factorx.citizens.service.AccountService;
import eu.factorx.citizens.service.SheddingRiskService;

import java.util.ArrayList;
import java.util.List;

public class SheddingRiskServiceImpl implements SheddingRiskService {

	private AccountService accountService = new AccountServiceImpl();

	@Override
	public SheddingRisk findById(Long id) {
		return Ebean.find(SheddingRisk.class, id);
	}

	@Override
	public List<SheddingRisk> findAll() {
		return Ebean.find(SheddingRisk.class).findList();
	}

	@Override
	public SheddingRisk save(SheddingRisk sheddingRisk) {
		Ebean.save(sheddingRisk);
		return sheddingRisk;
	}

	@Override
	public SheddingRisk update(SheddingRisk sheddingRisk) {
		Ebean.update(sheddingRisk);
		return sheddingRisk;
	}
}
