package dvd.gcs.app.start;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
public class ApplicationReaderStarter {

    private String dsRtspUrl;
    private String

    private static String configFileLocation = "./app.properties";
    private static final File configFile = new File(configFileLocation);
    private static final String defaultDsRtspUrl = "192.168.0.1/ds-gcs";
    private static final String defaultTelemetryPort = "5557";
    private static final String defaultCommandPort = "5556";

    private void readConfigFile() {
        try (InputStream input = new FileInputStream("./app.properties")) {
            Properties properties = new Properties();
            dsRtspUrl = properties.getProperty("app.deepstream-url");
            dsRtspUrl
        } catch (FileNotFoundException fileNotFoundException) {
            File newConfigFile = new File("app.properties");
            try {
                System.out.println(fileNotFoundException.getMessage());
                fileNotFoundException.printStackTrace();
                System.out.println("app.properties not found, loading defaults...");
                Properties properties = new Properties();
                properties.setProperty("app.deepstream-url", defaultDsRtspUrl);
                properties.setProperty("app.",defaultTelemetryPort);
                properties.setProperty("app.command-port", defaultCommandPort);
                newConfigFile.createNewFile();
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
                ioException.printStackTrace();
            }
        }

    }
}