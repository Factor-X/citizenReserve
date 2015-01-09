package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.SheddingRiskDTO;
import eu.factorx.citizens.model.shedding.SheddingRisk;
import org.joda.time.LocalDate;

import java.util.Date;

public class SheddingRiskToSheddingRiskDTO implements Converter<SheddingRisk, SheddingRiskDTO> {

	@Override
	public SheddingRiskDTO convert(SheddingRisk sheddingRisk) {
		return new SheddingRiskDTO(sheddingRisk.getId(),
			convertToJavaUtilDate(sheddingRisk.getRiskDate()),
			convertToJavaUtilDate(sheddingRisk.getMailSendingDate()));
	}

	private Date convertToJavaUtilDate(LocalDate localDate) {
		if (localDate != null) {
			return localDate.toDate();
		}
		return null;
	}
}
