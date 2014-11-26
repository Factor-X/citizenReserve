package eu.factorx.citizenReserve.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizenReserve.model.account.Account;
import eu.factorx.citizenReserve.service.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    @Override
    public Account saveAccount(Account account) {
        Ebean.save(account);
        return account;
    }

    @Override
    public Account getAccountById(Long id) {
        return Ebean.find(Account.class, id);
    }

    @Override
    public List<Account> findAll() {
        return Ebean.find(Account.class).findList();
    }
}
