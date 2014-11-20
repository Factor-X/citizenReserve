package eu.factorx.citizenReserve.converter;

import eu.factorx.citizenReserve.dto.AccountDTO;
import eu.factorx.citizenReserve.model.Account;

/**
 * Created by florian on 20/11/14.
 */
public class AccountToAccountDTOConverter implements Converter<Account, AccountDTO> {


    @Override
    public AccountDTO convert(Account account) {

        AccountDTO dto = new AccountDTO();

        dto.setEmail(account.getEmail());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setId(account.getId());

        return dto;
    }
}
