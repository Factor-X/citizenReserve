package eu.factorx.citizens.model.type;

import eu.factorx.citizens.util.exception.MyRuntimeException;

public enum AccountType {
	HOUSEHOLD("household");

    private String string;

    AccountType(String string) {
        this.string = string;
    }



    public String getString() {
        return string;
    }
}
