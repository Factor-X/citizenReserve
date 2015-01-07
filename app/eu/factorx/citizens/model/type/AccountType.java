package eu.factorx.citizens.model.type;

public enum AccountType {
	HOUSEHOLD("household"),
	ENTERPRISE("enterprise"),
	INSTITUTION("institution"),
	SUPERADMIN("superadmin");

    private String string;

    AccountType(String string) {
        this.string = string;
    }



    public String getString() {
        return string;
    }
}
