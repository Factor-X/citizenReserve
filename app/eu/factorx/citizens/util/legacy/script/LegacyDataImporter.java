package eu.factorx.citizens.util.legacy.script;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.TxRunnable;
import eu.factorx.citizens.controllers.TranslationHelper;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.account.LanguageEnum;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.type.AccountType;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.service.AccountService;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.service.TranslationService;
import eu.factorx.citizens.service.VelocityGeneratorService;
import eu.factorx.citizens.service.impl.AccountServiceImpl;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import eu.factorx.citizens.service.impl.TranslationServiceImpl;
import eu.factorx.citizens.service.impl.VelocityGeneratorServiceImpl;
import eu.factorx.citizens.util.email.messages.EmailMessage;
import eu.factorx.citizens.util.email.service.EmailService;
import eu.factorx.citizens.util.security.KeyGenerator;
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

public class LegacyDataImporter {

	public static final String HOSTNAME = Configuration.root().getString("citizens-reserve.hostname");
	public static final String CITIZENS_RESERVE_HOME = Configuration.root().getString("citizens-reserve.home");
	public static final String EMAIL_VELOCITY_TEMPLATE = "basicEmailStructure.vm";
	public static final String EMAIL_LEGACY_USER_INVITATION_SUBJECT = "email.legacyuserinvitation.subject";
	public static final String EMAIL_LEGACY_USER_INVITATION_CONTENT = "email.legacyuserinvitation.content";

	private TranslationService translationService;
	private VelocityGeneratorService velocityGeneratorService;
	private EmailService emailService;
	private AccountService accountService;
	private SurveyService surveyService;
	private Map<LanguageEnum, TranslationHelper> translationHelpers;

	public LegacyDataImporter() throws IOException {
		translationService = new TranslationServiceImpl();
		velocityGeneratorService = new VelocityGeneratorServiceImpl();
		emailService = new EmailService();
		accountService = new AccountServiceImpl();
		surveyService = new SurveyServiceImpl();
		translationHelpers = new HashMap<>();
		translationHelpers.put(LanguageEnum.FRANCAIS, new TranslationHelper(translationService, LanguageEnum.FRANCAIS));
		translationHelpers.put(LanguageEnum.NEERDERLANDS, new TranslationHelper(translationService, LanguageEnum.NEERDERLANDS));
	}

	/**
	 * Import accounts of citizens reserve previous version from a csv file
	 * The csv must have these headers: email,firstName,lastName,zipCode,nbPersons,powerProvider,powerComsumerCategory,averagePowerReduction
	 *
	 * @param csvFilePath
	 * @throws IOException
	 */
	public void execute(final String csvFilePath) throws IOException {
		final List<CSVRecord> csvRecords = new CSVParser(new FileReader(csvFilePath), CSVFormat.DEFAULT.withHeader()).getRecords();

		Ebean.execute(new TxRunnable() {
			public void run() {

				Logger.info("==== Start of import of legacy accounts: '{}' ====", csvFilePath);

				int nbSuccesses = 0;
				int nbFailures = 0;

				for (CSVRecord record : csvRecords) {

					// 1 - Create account
					String generatedPassword = KeyGenerator.generateRandomPassword(12);
					Account account = createAccount(record, generatedPassword);
					try {
						validateAccountInfos(account);
					} catch (Exception e) {
						Logger.error("Validation error while importing record #{} : {}", record.getRecordNumber(), e.getMessage());
						nbFailures++;
						continue;
					}
					accountService.saveOrUpdate(account);

					// 2 - Create survey (...with only one answer, for question Q1300: 'number of persons in household')
					Survey survey = new Survey(account);
					survey.addAnswer(QuestionCode.Q1300, null, Double.valueOf(record.get("nbPersons")));
					surveyService.saveSurvey(survey);

					// 3 - Send email
					sendEmail(account, generatedPassword);

					nbSuccesses++;
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}

				Logger.info("==== End of import of legacy accounts : {} successes, {} failures ====", nbSuccesses, nbFailures);
			}
		});
	}

	private void validateAccountInfos(Account account) throws Exception {
		if (accountService.findByEmail(account.getEmail()) != null) {
			throw new Exception("email '" + account.getEmail() + "' already exist!");
		}
	}

	private Account createAccount(CSVRecord record, String generatedPassword) {
		Account account = new Account();
		account.setAccountType(AccountType.HOUSEHOLD);
		account.setLanguage(LanguageEnum.FRANCAIS);
		account.setEmail(record.get("email"));
		account.setPassword(generatedPassword);
		account.setFirstName(record.get("firstName"));
		account.setLastName(record.get("lastName"));
		account.setZipCode(record.get("zipCode"));
		account.setPowerProvider(record.get("powerProvider"));
		account.setPowerComsumerCategory(record.get("powerComsumerCategory"));
		account.setLegacyAccountPowerReduction(Double.valueOf(record.get("averagePowerReduction")));
		account.setPasswordToChange(true);
		return account;
	}

	private void sendEmail(Account account, String generatedPassword) {
		LanguageEnum language = account.getLanguage();
		// title
		String title = translationService.getTranslation(EMAIL_LEGACY_USER_INVITATION_SUBJECT, language.getAbrv());
		// content
		Map<String, Object> values = new HashMap<>();
		values.put("contentKey", EMAIL_LEGACY_USER_INVITATION_CONTENT);
		// (content params: 0 = firstname, 1 = lastname, 2 = citizens reserve web site, 3 = login, 4 = password)
		values.put("contentParams", new String[]{account.getFirstName(), account.getLastName(), CITIZENS_RESERVE_HOME, account.getEmail(), generatedPassword});
		values.put("hostname", HOSTNAME);
		values.put("citizensReserveHome", CITIZENS_RESERVE_HOME);
		values.put("translationHelper", translationHelpers.get(account.getLanguage()));
		String content = velocityGeneratorService.generate(EMAIL_VELOCITY_TEMPLATE, values);
		emailService.send(new EmailMessage("jerome.carton@factorx.eu", title, content));
	}

}
