package eu.factorx.citizens.util.legacy;

import eu.factorx.citizens.controllers.TranslationHelper;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.service.TranslationService;
import eu.factorx.citizens.service.VelocityGeneratorService;
import eu.factorx.citizens.service.impl.TranslationServiceImpl;
import eu.factorx.citizens.service.impl.VelocityGeneratorServiceImpl;
import eu.factorx.citizens.util.email.messages.EmailMessage;
import eu.factorx.citizens.util.email.service.EmailService;
import org.apache.commons.lang3.tuple.Pair;
import play.Configuration;
import play.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailSender {

	private static final String HOSTNAME = Configuration.root().getString("citizens-reserve.hostname");
	private static final String CITIZENS_RESERVE_HOME = Configuration.root().getString("citizens-reserve.home");
	private static final String EMAIL_TEMPLATE = "basicEmailStructure.vm";
	private static final String EMAIL_SUBJECT_KEY = "email.legacyuserinvitation.subject";
	private static final String EMAIL_CONTENT_KEY = "email.legacyuserinvitation.content";

	private VelocityGeneratorService velocityGeneratorService = new VelocityGeneratorServiceImpl();
	private TranslationService translationService = new TranslationServiceImpl();
	public Integer nbErrors = 0;

	public void sendInvitations(List<Pair<Account, String>> accountsInfos) {
		for (Pair<Account, String> accountInfo : accountsInfos) {
			Account account = accountInfo.getLeft();
			String generatedPassword = accountInfo.getRight();
			String subject = translationService.getTranslation(EMAIL_SUBJECT_KEY, account.getLanguage().getAbrv());
			String content = velocityGeneratorService.generate(EMAIL_TEMPLATE, getEmailModel(account, generatedPassword));
			try {
				sendEmail(account.getEmail(), subject, content);
				Thread.sleep(1000L);
			} catch (Exception e) {
				Logger.error("Exception while sending mail to '{}': {}", account.getEmail(), e.getMessage());
				nbErrors++;
			}
		}
	}

	private Map<String, Object> getEmailModel(Account account, String generatedPassword) {
		Map<String, Object> values = new HashMap<>();
		values.put("contentKey", EMAIL_CONTENT_KEY);
		// (params: 0 = firstname, 1 = lastname, 2 = citizens reserve web site, 3 = login, 4 = password)
		values.put("contentParams", new String[]{account.getFirstName(), account.getLastName(), CITIZENS_RESERVE_HOME, account.getEmail(), generatedPassword});
		values.put("hostname", HOSTNAME);
		values.put("citizensReserveHome", CITIZENS_RESERVE_HOME);
		values.put("translationHelper", new TranslationHelper(translationService, account.getLanguage()));
		return values;
	}

	private void sendEmail(String recipient, String subject, String content) throws IOException, InterruptedException {
		new EmailService().send(new EmailMessage("jerome.carton.77@gmail.com", subject, content));
	}
}
