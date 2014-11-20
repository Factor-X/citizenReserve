package eu.factorx.citizenReserve.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizenReserve.model.Account;
import eu.factorx.citizenReserve.service.AccountService;

import java.util.List;

/**
 * Created by florian on 20/11/14.
 */
public class AccountServiceImpl implements AccountService {

    public List<Account> findAll() {
        return Ebean.find(Account.class).findList();
    }
}
