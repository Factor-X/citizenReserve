package eu.factorx.citizens.service;

import java.util.Map;

/**
 * Created by florian on 3/12/14.
 */
public interface VelocityGeneratorService {

    public String generate(String templateName, Map values);

    public String getTemplateNameByMethodName();
}
