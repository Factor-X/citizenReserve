package eu.factorx.citizens.service;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.type.AccountType;

import java.util.List;

public interface AccountService {

    public Account saveOrUpdate(Account account);

    public List<Account> findAll();

    public Account findByEmail(String email);

    public boolean controlPassword(String password, Account account);

    public Account findById(Long id);

	public Double getLegacyAccountsPowerReduction();

	int getAccountsNumber(AccountType... accountTypes);
}
