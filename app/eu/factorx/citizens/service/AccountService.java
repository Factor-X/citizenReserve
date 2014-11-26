package eu.factorx.citizens.service;

import eu.factorx.citizens.model.account.Account;

import java.util.List;

public interface AccountService {

    public Account saveAccount(Account account);

    public Account getAccountById(Long id);

    public List<Account> findAll();
}
