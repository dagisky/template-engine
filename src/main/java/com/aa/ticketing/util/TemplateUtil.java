package com.aa.ticketing.util;

import com.aa.ticketing.exception.TemplateRendererException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class TemplateUtil{
    private static final String BASE_TEMPLATE_PATH = ".\\src\\main\\resources\\templates";
    /**
     * Replaces placeholders in a file's content using values from a specified object and string.
     *
     * Reads a file at filePath, replaces placeholders ({{placeholder}}) with values from baseResource and baseTarget,
     * and returns the modified content. Placeholders are replaced via injectString method.
     *
     * @param filePath Path to the file containing the template.
     * @param baseResource {@link Object} holding the data to be pulled from
     * @param baseTarget String to influence placeholder values.
     *
     * @return Modified content with replaced placeholders.
     *
     * @throws TemplateRendererException if the file cannot be read, with a message detailing the issue.
     *
     * Note: File read failures are logged and encapsulated in a TemplateGeneratorException.
     */
//    static {
//        String base_template_path;
//        Properties properties = new Properties();
//        try {
//            properties.load(TemplateUtil.class.getClassLoader().getResourceAsStream("config.properties"));
//            base_template_path = properties.getProperty("template.directory", "templates");
//        } catch (IOException e) {
//            // If there's an issue loading the configuration, use the default path
//            base_template_path = "templates";
//            System.out.println(e.getMessage());
//        }
//        BASE_TEMPLATE_PATH = base_template_path;
//    }
    public static String loadTemplate(String filePath) {

        String templatePathString = String.format(BASE_TEMPLATE_PATH + "\\%s.json", filePath);
        Path path = Paths.get(templatePathString);

        try {
            if (Files.exists(path)) {
                return new String(Files.readAllBytes(path));
            } else {
                // Attempt to load from classpath if not found in filesystem
                InputStream inputStream = TemplateUtil.class.getClassLoader().getResourceAsStream(String.format("templates/%s.json", filePath));
                if (inputStream != null) {
                    return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                } else {
                    log.warn("Template file not found: {}", filePath);
                    throw new TemplateRendererException("File not found: " + filePath);
                }
            }
        } catch (IOException e) {
            log.warn("Error reading template file: {}", filePath, e);
            throw new TemplateRendererException(e.getMessage());
        }
    }


}
