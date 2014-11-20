package eu.factorx.citizenReserve.service;

import com.avaje.ebean.Ebean;
import eu.factorx.citizenReserve.model.Account;

import java.util.List;

/**
 * Created by florian on 20/11/14.
 */
public interface AccountService {

    public List<Account> findAll();
}
