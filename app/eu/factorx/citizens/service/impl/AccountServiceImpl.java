package eu.factorx.citizens.service.impl;

import com.avaje.ebean.Ebean;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.type.AccountType;
import eu.factorx.citizens.service.AccountService;
import eu.factorx.citizens.util.BusinessErrorType;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import play.Logger;

import java.util.List;

public class AccountServiceImpl implements AccountService {


	public static final String LEGACY_ACCOUNTS_POWER_REDUCTION_ALIAS = "totalPowerReduction";
	public static final String LEGACY_ACCOUNTS_POWER_REDUCTION_SQL = "select sum(legacy_account_power_reduction) as " + LEGACY_ACCOUNTS_POWER_REDUCTION_ALIAS + " from accounts where legacy_account_power_reduction is not null";

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

	@Override
	public Account saveOrUpdate(Account account) {

		boolean edit = true;
		if (account.getId() == null) {
			Account existingAccount = findByEmail(account.getEmail());
			if (existingAccount != null) {
				throw new MyRuntimeException(BusinessErrorType.EMAIL_ALREADY_USED);
			}
			edit = false;
		}
		if (account.getPassword().length() < 30) {
			account.setPassword(generateEncryptingPassword(account.getPassword()));
		}
		if (edit) {
			account.update();
		} else {
			account.save();
		}
		return account;

	}

	private String generateEncryptingPassword(final String password) {

		return new StrongPasswordEncryptor().encryptPassword(password);
	}

	@Override
	public boolean controlPassword(String password, Account account) {

		return new StrongPasswordEncryptor().checkPassword(password,
			account.getPassword());
	}

	@Override
	public Account findById(Long id) {
		return Ebean.createNamedQuery(Account.class, Account.FIND_BY_ID)
			.setParameter("id", id)
			.findUnique();
	}

	@Override
	public Double getLegacyAccountsPowerReduction() {
		Double result = Ebean.createSqlQuery(LEGACY_ACCOUNTS_POWER_REDUCTION_SQL).findUnique().getDouble(LEGACY_ACCOUNTS_POWER_REDUCTION_ALIAS);
		if (result == null) {
			result = 0d;
		}
		Logger.info("Legacy accounts reserve = " + result + " Watt");
		return result;
	}

	@Override
	public int getAccountsNumber(AccountType... accountTypes) {
		return Ebean.find(Account.class).where().in(Account.ACCOUNT_TYPE_PROPERTY, (Object[])accountTypes).findRowCount();
	}
}
