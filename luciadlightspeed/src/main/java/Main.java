import javafx.application.Application;

public class Main {
	
	public static void main(String[] args) throws Exception {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Application.launch(FXApplication.class);

			}
		});

		thread.start();
	}
	
}
