package eu.factorx.citizens.util.legacy;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.TxRunnable;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.account.LanguageEnum;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.type.AccountType;
import eu.factorx.citizens.model.type.QuestionCode;
import eu.factorx.citizens.service.AccountService;
import eu.factorx.citizens.service.SurveyService;
import eu.factorx.citizens.service.impl.AccountServiceImpl;
import eu.factorx.citizens.service.impl.SurveyServiceImpl;
import eu.factorx.citizens.util.security.KeyGenerator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;
import play.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LegacyDataImporter implements TxRunnable {

	//Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";
	//CSV file header
	private static final Object[] OUTPUT_HEADER = {"accountId", "email", "generatedPassword"};

	private AccountService accountService = new AccountServiceImpl();
	private SurveyService surveyService = new SurveyServiceImpl();
	private List<Pair<Account, String>> results = new ArrayList<>();
	private List<CSVRecord> errors = new ArrayList<>();

	private String csvFilePath;

	public LegacyDataImporter(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	@Override
	public void run() {
		List<CSVRecord> csvRecords;
		try {
			csvRecords = new CSVParser(new FileReader(csvFilePath), CSVFormat.DEFAULT.withHeader()).getRecords();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for (CSVRecord record : csvRecords) {
			try {
				// Generate random password
				String generatedPassword = KeyGenerator.generateRandomPassword(8);
				// Create account
				Account account = accountService.saveOrUpdate(createAccount(record, generatedPassword));
				// Create survey (if 'nbPersons' info is defined)
				Double nbPersons = Double.valueOf(record.get("nbPersons"));
				if (nbPersons != null) {
					Survey survey = new Survey(account);
					survey.addAnswer(QuestionCode.Q1300, null, nbPersons);
					surveyService.saveSurvey(survey);
				}
				results.add(Pair.of(account, generatedPassword));
			} catch (Exception e) {
				Logger.error("Exception while importing record #{}: {}\nt\t=> {}", record.getRecordNumber(), record, e.getMessage());
				errors.add(record);
			}
		}
		exportResults(results, FilenameUtils.removeExtension(csvFilePath) + "_output.csv");
	}

	private static Account createAccount(CSVRecord record, String generatedPassword) {
		Account account = new Account();
		account.setAccountType(AccountType.HOUSEHOLD);
		account.setLanguage(LanguageEnum.FRANCAIS);
		account.setPassword(generatedPassword);
		account.setPasswordToChange(true);
		account.setEmail(record.get("email"));
		account.setFirstName(record.get("firstName"));
		account.setLastName(record.get("lastName"));
		account.setZipCode(record.get("zipCode"));
		account.setPowerProvider(record.get("powerProvider"));
		account.setPowerComsumerCategory(record.get("powerComsumerCategory"));
		account.setLegacyAccountPowerReduction(Double.valueOf(record.get("averagePowerReduction")));
		return account;
	}

	private void exportResults(List<Pair<Account, String>> results, String fileName) {
		FileWriter fileWriter = null;

		CSVPrinter csvFilePrinter = null;

		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {

			//initialize FileWriter object
			fileWriter = new FileWriter(fileName);

			//initialize CSVPrinter object
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

			//Create CSV file header
			csvFilePrinter.printRecord(OUTPUT_HEADER);

			//Write each result in CSV file
			for (Pair<Account, String> resultInfo : results) {
				Account account = resultInfo.getLeft();
				String generatedPassword = resultInfo.getRight();
				List<String> studentDataRecord = new ArrayList<>();
				studentDataRecord.add(String.valueOf(account.getId()));
				studentDataRecord.add(account.getEmail());
				studentDataRecord.add(generatedPassword);
				csvFilePrinter.printRecord(studentDataRecord);
			}

			Logger.info("CSV file was created successfully !!!");

		} catch (Exception e) {
			Logger.error("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				Logger.error("Error while flushing/closing fileWriter/csvPrinter !!!");
				e.printStackTrace();
			}
		}

	}

	/**
	 * Import accounts of citizens' reserve legacy version from a csv file.
	 * <br> You may trigger this importer in a scala console:
	 * <br>> new play.core.StaticApplication(new java.io.File("."))
	 * <br>> eu.factorx.citizens.util.legacy.script.LegacyDataImporter.execute(csvFilePath)
	 * <br><br> The csv file must have these headers: "email", "firstName", "lastName", "zipCode", "nbPersons", "powerProvider", "powerComsumerCategory", "averagePowerReduction"
	 *
	 * @param csvFilePath The full-qualified CSV file path
	 */
	public static void execute(String csvFilePath) {
		Logger.info("==== Start of data importer: CSV input file: '{}'", csvFilePath);
		LegacyDataImporter importer = new LegacyDataImporter(csvFilePath);
		Ebean.execute(importer);
		Logger.info("==== End of data importer: {} accounts imported{}", importer.results.size(), importer.errors.isEmpty() ? "" : " (" + importer.errors.size() + " errors)");
	}

}
