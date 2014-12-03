package eu.factorx.citizens.util.email;

/**
 * Created by florian on 3/12/14.
 */
public class EmailParams {

    private int order;
    private String name;

    public EmailParams(int order, String name) {
        this.order = order;
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailParams)) return false;

        EmailParams that = (EmailParams) o;

        if (order != that.order) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = order;
        result = 31 * result + name.hashCode();
        return result;
    }
}
