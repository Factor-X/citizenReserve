package eu.factorx.citizens.controllers;

import eu.factorx.citizens.controllers.technical.AbstractController;
import eu.factorx.citizens.controllers.technical.SecuredController;
import eu.factorx.citizens.converter.BatchSetToBatchSetDTOConverter;
import eu.factorx.citizens.converter.SheddingRiskToSheddingRiskDTO;
import eu.factorx.citizens.dto.ListDTO;
import eu.factorx.citizens.dto.SheddingRiskDTO;
import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.batch.BatchResultSet;
import eu.factorx.citizens.model.shedding.SheddingRisk;
import eu.factorx.citizens.model.shedding.SheddingRiskAnswer;
import eu.factorx.citizens.model.survey.TopicEnum;
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
import play.libs.Akka;
import play.mvc.Result;
import play.mvc.Security;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SuperAdminController extends AbstractController {

	private static final String EMAIL_SUBJECT_KEY = "email.alert.subject";
	private static final String EMAIL_TEMPLATE = "alertEmail.vm";
	private static final String ACTIONS_TABLE_TEMPLATE = "listAction.vm";
	private static final String HOSTNAME = Configuration.root().getString("citizens-reserve.hostname");
	private static final String CITIZENS_RESERVE_HOME = Configuration.root().getString("citizens-reserve.home");
	private static final String CONFIRMATION_PAGE_URL = "risks/user_answer";

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

		final List<SheddingRiskAnswer> sheddingRiskAnswers = generateAnswers(sheddingRisk);

		Akka.system().scheduler().scheduleOnce(
			Duration.create(1, TimeUnit.SECONDS),
			new Runnable() {
				public void run() {
					try {
						sendAlertEmails(sheddingRiskAnswers);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			},
			Akka.system().dispatchers().defaultGlobalDispatcher());

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
				emailService.send(new EmailMessage("jerome.carton.77@gmail.com", subject, content));
				return;
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
		values.put("actionsTable", generateActionsTable(account, translationHelper));
		String confirmationPageUrl = CITIZENS_RESERVE_HOME + "/" + CONFIRMATION_PAGE_URL;
		Logger.info("confirmationPageUrl = " + confirmationPageUrl);
		values.put("confirmationPageUrl", confirmationPageUrl);

		String buttons = translationHelper.getMessage("email.alert.confirmation_buttons", confirmationPageUrl, sheddingRiskAnswer.getUuid(), confirmationPageUrl, sheddingRiskAnswer.getUuid());
		Logger.info("buttons = " + buttons);


		return values;
	}

	public String generateActionsTable(Account account, TranslationHelper translationHelper) {
		HashMap<TopicEnum, List<Pair<String, String>>> actions = surveyService.getActionsForSummaryEmail(account);

		Map<String, Object> values = new HashMap<>();
		values.put("actions", actions);
		values.put("translationHelper", translationHelper);
		values.put("hostname", HOSTNAME);

		return velocityGeneratorService.generate(ACTIONS_TABLE_TEMPLATE, values);
	}

}
