package eu.factorx.citizens.util.exception;

/**
 * Created by florian on 30/08/14.
 */
public class MyRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private final String toClientMessage;

	public MyRuntimeException(String message) {
		super(message);
		toClientMessage = message;
	}

	public MyRuntimeException(Throwable cause, String toClientMessage) {
		super(cause);
		this.toClientMessage = toClientMessage;
	}

	public String getToClientMessage() {
		return toClientMessage;
	}

}
