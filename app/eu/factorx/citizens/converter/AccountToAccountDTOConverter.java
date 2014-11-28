package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.AccountDTO;
import eu.factorx.citizens.model.account.Account;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by florian on 20/11/14.
 */
public class AccountToAccountDTOConverter implements Converter<Account, AccountDTO> {


	@Override
	public AccountDTO convert(Account account) {

		AccountDTO dto = new AccountDTO();

        dto.setId(account.getId());

        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
		dto.setEmail(account.getEmail());
        dto.setZipCode(account.getZipCode());

        dto.setPowerProvider(account.getPowerProvider());
        dto.setPowerComsumerCategory(account.getPowerComsumerCategory());
        if(account.getOtherEmailAdresses()!=null) {
            dto.setOtherEmailAddresses(Arrays.asList(StringUtils.split(account.getOtherEmailAdresses(), ";")));
        }
        dto.setSensitizationKit(account.getSensitizationKit());
        dto.setAccoutType(account.getAccountType().getString());

		return dto;
	}
}
