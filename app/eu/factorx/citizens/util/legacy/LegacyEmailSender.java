package eu.factorx.citizens.util.legacy;

import eu.factorx.citizens.controllers.TranslationHelper;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.service.AccountService;
import eu.factorx.citizens.service.TranslationService;
import eu.factorx.citizens.service.VelocityGeneratorService;
import eu.factorx.citizens.service.impl.AccountServiceImpl;
import eu.factorx.citizens.service.impl.TranslationServiceImpl;
import eu.factorx.citizens.service.impl.VelocityGeneratorServiceImpl;
import eu.factorx.citizens.util.email.messages.EmailMessage;
import eu.factorx.citizens.util.email.service.EmailService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import play.Configuration;
import play.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegacyEmailSender {

	private static final String HOSTNAME = Configuration.root().getString("citizens-reserve.hostname");
	private static final String CITIZENS_RESERVE_HOME = Configuration.root().getString("citizens-reserve.home");
	private static final String EMAIL_TEMPLATE = "basicEmailStructure.vm";
	private static final String EMAIL_SUBJECT_KEY = "email.legacyuserinvitation.subject";
	private static final String EMAIL_CONTENT_KEY = "email.legacyuserinvitation.content";

	private VelocityGeneratorService velocityGeneratorService = new VelocityGeneratorServiceImpl();
	private TranslationService translationService = new TranslationServiceImpl();
	private AccountService accountService = new AccountServiceImpl();
	private EmailService emailService;
	private int nbErrors = 0;

	private String csvFilePath;

	public LegacyEmailSender(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	public void sendInvitations() throws IOException {
		emailService = new EmailService();
		for (CSVRecord record : parseCsvInputFile()) {
			Long accountId = Long.valueOf(record.get("accountId"));
			String generatedPassword = record.get("generatedPassword");

			Account account = accountService.findById(accountId);
			if (account == null) {
				Logger.error("Cannot find account with id = " + accountId);
				nbErrors++;
				continue;
			}

			String subject = translationService.getTranslation(EMAIL_SUBJECT_KEY, account.getLanguage().getAbrv());
			String content = velocityGeneratorService.generate(EMAIL_TEMPLATE, getEmailModel(account, generatedPassword));
			try {
				Thread.sleep(1000L);
				emailService.send(new EmailMessage("jerome.carton@factorx.eu", subject, content));
			} catch (Exception e) {
				Logger.error("Exception while sending mail to '{}': {}", account.getEmail(), e.getMessage());
				nbErrors++;
			}
			break;
		}
	}

	public List<CSVRecord> parseCsvInputFile() throws IOException {
		CSVParser csvParser = new CSVParser(new FileReader(csvFilePath), CSVFormat.DEFAULT.withHeader());
		return csvParser.getRecords();
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

	/**
	 * Send invitations to (already imported) legacy accounts from a csv file containing the necessary information (account id & generated password)
	 *
	 * The csv file must have these headers: "email", "password"
	 *
	 * You may trigger this job in a scala console:
	 * new play.core.StaticApplication(new java.io.File("."))
	 * eu.factorx.citizens.util.legacy.EmailSender.execute(csvFilePath)
	 *
	 * @param csvFilePath The full-qualified CSV file path
	 * @throws IOException
	 */
	public static void execute(String csvFilePath) throws IOException {
		Logger.info("==== Start of email sender: CSV input file: '{}'", csvFilePath);
		LegacyEmailSender emailSender = new LegacyEmailSender(csvFilePath);
		emailSender.sendInvitations();
		Logger.info("==== End of email sender{}", (emailSender.nbErrors == 0) ? " (no errors)" : " (" + emailSender.nbErrors + " errors)");
	}
}
