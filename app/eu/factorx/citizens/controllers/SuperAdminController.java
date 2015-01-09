package eu.factorx.citizens.controllers;

import eu.factorx.citizens.controllers.technical.AbstractController;
import eu.factorx.citizens.controllers.technical.SecuredController;
import eu.factorx.citizens.converter.ActionAnswerToActionAnswerDTOConverter;
import eu.factorx.citizens.converter.BatchSetToBatchSetDTOConverter;
import eu.factorx.citizens.converter.SheddingRiskToSheddingRiskDTO;
import eu.factorx.citizens.dto.ActionAnswerDTO;
import eu.factorx.citizens.dto.ListDTO;
import eu.factorx.citizens.dto.SheddingRiskDTO;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.batch.BatchResultSet;
import eu.factorx.citizens.model.shedding.SheddingRisk;
import eu.factorx.citizens.model.shedding.SheddingRiskAnswer;
import eu.factorx.citizens.model.survey.ActionAnswer;
import eu.factorx.citizens.model.survey.Survey;
import eu.factorx.citizens.model.survey.TopicEnum;
import eu.factorx.citizens.model.type.AccountType;
import eu.factorx.citizens.service.*;
import eu.factorx.citizens.service.impl.*;
import eu.factorx.citizens.util.email.messages.EmailMessage;
import eu.factorx.citizens.util.email.service.EmailService;
import eu.factorx.citizens.util.security.SecurityAnnotation;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.LocalDate;
import play.Configuration;
import play.Logger;
import play.db.ebean.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.*;

public class SuperAdminController extends AbstractController {

	private static final String EMAIL_SUBJECT_KEY = "email.alert.subject";
	private static final String EMAIL_TEMPLATE = "alertEmail.vm";
	private static final String ACTIONS_TABLE_TEMPLATE = "listAction.vm";
	private static final String HOSTNAME = Configuration.root().getString("citizens-reserve.hostname");
	private static final String CITIZENS_RESERVE_HOME = Configuration.root().getString("citizens-reserve.home");
	private static final String CONFIRMATION_PAGE_URL = "risks/user_answer";
	private static final String ENTERPRISE_ACTIONS_TABLE_TEMPLATE = "listEnterpriseActions.vm";

	//services
	private BatchSetService batchSetService = new BatchSetServiceImpl();
	private SheddingRiskService sheddingRiskService = new SheddingRiskServiceImpl();
	private SheddingRiskAnswerService sheddingRiskAnswerService = new SheddingRiskAnswerServiceImpl();
	private AccountService accountService = new AccountServiceImpl();
	private TranslationService translationService = new TranslationServiceImpl();
	private VelocityGeneratorService velocityGeneratorService = new VelocityGeneratorServiceImpl();
	private SurveyService surveyService = new SurveyServiceImpl();

	//converters
	private BatchSetToBatchSetDTOConverter batchSetToBatchSetDTOConverter = new BatchSetToBatchSetDTOConverter();
	private SheddingRiskToSheddingRiskDTO sheddingRiskToSheddingRiskDTOConverter = new SheddingRiskToSheddingRiskDTO();
	private ActionAnswerToActionAnswerDTOConverter actionAnswerToActionAnswerDTOConverter = new ActionAnswerToActionAnswerDTOConverter();

	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isSuperAdmin = true)
	public Result getReductionValuesProgression() {

		List<BatchResultSet> batchToDisplayForSuperAdmin = batchSetService.findBatchToDisplayForSuperAdmin();

		ListDTO listDTO = new ListDTO();

		for (BatchResultSet batchResult : batchToDisplayForSuperAdmin) {
			listDTO.add(batchSetToBatchSetDTOConverter.convert(batchResult));

		}

		return ok(listDTO);
	}

	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isSuperAdmin = true)
	public Result findAllSheddingRisks() {
		ListDTO listDTO = new ListDTO();
		for (SheddingRisk sheddingRisk : sheddingRiskService.findAll()) {
			listDTO.add(sheddingRiskToSheddingRiskDTOConverter.convert(sheddingRisk));
		}
		return ok(listDTO);
	}

	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isSuperAdmin = true)
	public Result saveSheddingRisk() {
		SheddingRiskDTO sheddingRiskDTO = extractDTOFromRequest(SheddingRiskDTO.class);
		LocalDate riskDate = LocalDate.fromDateFields(sheddingRiskDTO.getRiskDate());
		SheddingRisk sheddingRisk = sheddingRiskService.save(new SheddingRisk(riskDate));
		return ok(sheddingRiskToSheddingRiskDTOConverter.convert(sheddingRisk));
	}

	@Transactional
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isSuperAdmin = true)
	public Result sendAlerts() throws IOException {
		SheddingRiskDTO sheddingRiskDTO = extractDTOFromRequest(SheddingRiskDTO.class);
		SheddingRisk sheddingRisk = sheddingRiskService.findById(sheddingRiskDTO.getId());

		List<SheddingRiskAnswer> sheddingRiskAnswers = generateAnswers(sheddingRisk);

		sendAlertEmails(sheddingRiskAnswers);

		sheddingRisk.setMailSendingDate(new LocalDate());
		sheddingRiskService.update(sheddingRisk);

		return ok(sheddingRiskToSheddingRiskDTOConverter.convert(sheddingRisk));
	}

	private List<SheddingRiskAnswer> generateAnswers(SheddingRisk sheddingRisk) {
		List<SheddingRiskAnswer> res = new ArrayList<>();
		for (Account account : accountService.findAll()) {
			res.add(sheddingRiskAnswerService.save(new SheddingRiskAnswer(sheddingRisk, account, null)));
		}
		return res;
	}

	private void sendAlertEmails(List<SheddingRiskAnswer> sheddingRiskAnswers) throws IOException {
		final EmailService emailService = new EmailService();
		for (SheddingRiskAnswer sheddingRiskAnswer : sheddingRiskAnswers) {
			Account account = sheddingRiskAnswer.getAccount();
			SheddingRisk sheddingRisk = sheddingRiskAnswer.getRisk();
			String subject = translationService.getTranslation(EMAIL_SUBJECT_KEY, account.getLanguage().getAbrv(), sheddingRisk.getRiskDate().toString("dd/MM"));
			String content = velocityGeneratorService.generate(EMAIL_TEMPLATE, getEmailModel(sheddingRiskAnswer));
			try {
				Thread.sleep(3000L);
				emailService.send(new EmailMessage(account.getEmail(), subject, content));
			} catch (Exception e) {
				Logger.error("Exception while sending mail to '{}': {}", account.getEmail(), e.getMessage());
			}
		}
	}

	private Map getEmailModel(SheddingRiskAnswer sheddingRiskAnswer) {
		Account account = sheddingRiskAnswer.getAccount();
		TranslationHelper translationHelper = new TranslationHelper(translationService, account.getLanguage());

		Map<String, Object> values = new HashMap<>();

		values.put("citizensReserveHome", CITIZENS_RESERVE_HOME);
		values.put("translationHelper", translationHelper);
		values.put("riskAnswer", sheddingRiskAnswer);
		if (account.getAccountType().equals(AccountType.HOUSEHOLD)) {
			values.put("actionsTable", generateActionsTableForHousehold(account, translationHelper));
		}
		if (account.getAccountType().equals(AccountType.ENTERPRISE)) {
			values.put("actionsTable", generateActionsTableForEnterprise(account, translationHelper));
		}
		if (account.getAccountType().equals(AccountType.INSTITUTION)) {
			values.put("actionsTable", generateActionsTableForInstitution(account, translationHelper));
		}
		values.put("confirmationPageUrl", CITIZENS_RESERVE_HOME + "/" + CONFIRMATION_PAGE_URL);

		return values;
	}

	public String generateActionsTableForHousehold(Account account, TranslationHelper translationHelper) {
		HashMap<TopicEnum, List<Pair<String, String>>> actions = surveyService.getActionsForSummaryEmail(account);

		Map<String, Object> values = new HashMap<>();
		values.put("actions", actions);
		values.put("translationHelper", translationHelper);
		values.put("hostname", HOSTNAME);

		return velocityGeneratorService.generate(ACTIONS_TABLE_TEMPLATE, values);
	}

	public String generateActionsTableForEnterprise(Account account, TranslationHelper translationHelper) {

		Survey survey = surveyService.findValidSurveyByAccount(account);

		Map<String, Object> values = new HashMap<>();

		Map<String, Object> sections = new LinkedHashMap<>();

		sections.put("enterprise.office.label", Arrays.asList(Arrays.asList("Q10110", "Q10120"), new ArrayList<String>()));
		sections.put("enterprise.building.label", Arrays.asList(Arrays.asList("Q10210", "Q10220", "Q10230", "Q10240", "Q10250", "Q10260", "Q10270", "Q10280", "Q10290"), Arrays.asList("Q10290")));
		sections.put("enterprise.process.label", Arrays.asList(Arrays.asList("Q10310", "Q10320", "Q10330", "Q10340", "Q10350", "Q10360", "Q10380", "Q10390", "Q10395"), Arrays.asList("Q10395")));
		sections.put("enterprise.other.label", Arrays.asList(Arrays.asList("Q10400"), Arrays.asList("Q10400")));


		List<ActionAnswerDTO> dtos = new ArrayList<>();
		for (ActionAnswer actionAnswer : survey.getActionAnswers()) {
			ActionAnswerDTO dto = actionAnswerToActionAnswerDTOConverter.convert(actionAnswer);
			if (dto.getTitle() == null) dto.setTitle("");
			if (dto.getDescription() == null) dto.setDescription("");
			else dto.setDescription("(" + dto.getDescription() + ")");
			dtos.add(dto);
		}

		System.out.println(dtos);
		values.put("sections", sections);
		values.put("actions", dtos);
		values.put("translationHelper", translationHelper);
		values.put("hostname", HOSTNAME);

		String result = velocityGeneratorService.generate(ENTERPRISE_ACTIONS_TABLE_TEMPLATE, values);
		return result;
	}


	public String generateActionsTableForInstitution(Account account, TranslationHelper translationHelper) {
		throw new NotImplementedException();
	}

}
