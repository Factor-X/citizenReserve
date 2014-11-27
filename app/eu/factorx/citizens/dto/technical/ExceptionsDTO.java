package eu.factorx.citizens.dto.technical;

/**
 * Created by florian on 18/11/14.
 */
public class ExceptionsDTO extends DTO {

	private final String message;

	public ExceptionsDTO() {
		this.message = null;
	}

	public ExceptionsDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
