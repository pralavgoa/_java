package ctsibip.shared.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SQLTemplateUtils extends TemplateUtils {

    private static final Logger LOGGER = Logger.getLogger(SQLTemplateUtils.class);

    public static String getSQLFromTemplate(Map<String, Object> mapOfParameters, String templateFolderPath,
            String templateFileName) {
        String response = "";
        try {
            Configuration cfg = TemplateUtils.createTemplateConfiguration(templateFolderPath);
            Template template = cfg.getTemplate(templateFileName);
            StringWriter stringWriter = new StringWriter();
            template.process(mapOfParameters, new PrintWriter(stringWriter));
            response = stringWriter.toString();

        } catch (TemplateException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error("Check the filepath to the template file", e);
        }
        return response;
    }

    public static String getSQLFromTemplate(String templateFolderPath, String templateFileName) {
        return getSQLFromTemplate(new HashMap<String, Object>(), templateFolderPath, templateFileName);
    }
}
