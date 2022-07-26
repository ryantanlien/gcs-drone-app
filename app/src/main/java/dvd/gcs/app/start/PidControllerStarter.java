package dvd.gcs.app.start;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PidControllerStarter {

    private static Process pidController;

    public static void init() {
        try {
            Process process = Runtime.getRuntime().exec(new String[] {
                "python receiveamqp3.py"
            });
            pidController = process;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public static void term() {
        if (pidController != null && pidController.isAlive()) {
            pidController.destroy();
            if (pidController.isAlive()) {
                kill();
            }
        }
    }

    public static void kill() {
        if (pidController != null && pidController.isAlive()) {
            pidController.destroyForcibly();
        }
    }
}
