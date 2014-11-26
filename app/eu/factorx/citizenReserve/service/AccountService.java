package eu.factorx.citizenReserve.service;

import eu.factorx.citizenReserve.model.account.Account;

import java.util.List;

public interface AccountService {

    public Account saveAccount(Account account);

    public Account getAccountById(Long id);

    public List<Account> findAll();
}
