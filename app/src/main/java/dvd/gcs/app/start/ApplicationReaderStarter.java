package dvd.gcs.app.start;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
@Scope("singleton")
public class ApplicationReaderStarter {

    private static String dsRtspUrl;
    private static String commandPort;
    private static String telemetryPort;
    private static String configFileLocation = "./app.properties";
    private static final String defaultDsRtspUrl = "192.168.0.1/ds-gcs";
    private static final String defaultTelemetryPort = "5557";
    private static final String defaultCommandPort = "5556";

    public static String getTelemetryPort() {
        return telemetryPort;
    }

    public static String getCommandPort() {
        return commandPort;
    }

    public static String getDsRtspUrl() {
        return dsRtspUrl;
    }

    @PostConstruct
    public void init() {
        loadProperties();
    }

    private void loadProperties() {
        File configFile = new File(configFileLocation);
        if(!configFile.exists()) {
            createConfigFile();
        } else {
            readConfigFile();
        }
    }

    private void readConfigFile() {
        try (InputStream input = new FileInputStream("./app.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            dsRtspUrl = properties.getProperty("app.deepstream-url");
            if (dsRtspUrl.equals("")) {
                dsRtspUrl = defaultDsRtspUrl;
            }
            commandPort = properties.getProperty("app.command-port");
            if (commandPort.equals("")) {
                commandPort = defaultCommandPort;
            }
            telemetryPort = properties.getProperty("app.telemetry-port");
            if (telemetryPort.equals("")) {
                telemetryPort = defaultTelemetryPort;
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            ioException.printStackTrace();
        }
    }

    private void createConfigFile() {
        try  {
            File newConfigFile = new File(configFileLocation);
            if (newConfigFile.createNewFile()) {
                OutputStream output = new FileOutputStream("app.properties");
                Properties properties = new Properties();
                properties.setProperty("app.deepstream-url", defaultDsRtspUrl);
                properties.setProperty("app.telemetry-port", defaultTelemetryPort);
                properties.setProperty("app.command-port", defaultCommandPort);
                properties.store(output, null);
            }
        } catch(IOException ioException) {
            System.out.println(ioException.getMessage());
            ioException.printStackTrace();
        }
    }
}