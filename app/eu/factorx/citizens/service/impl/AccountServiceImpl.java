package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.service.AccountService;

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

    @Override
    public Account findByEmail(String email) {
        return Ebean.createNamedQuery(Account.class, Account.FIND_BY_EMAIL)
                .setParameter(Account.COL_EMAIL, email)
                .findUnique();
    }
}
