import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FXApplication extends  Application{
	StackPane root;
	LuciadView view;
	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new StackPane();
		primaryStage.setScene(new Scene(root,640*2.5,360*2.5));
		primaryStage.show();
		view = new LuciadView();
		addMap(view.getMapSwingNode());
	}
	
	public void addMap(Node mapNode){
		root.getChildren().add(mapNode);
	}

}
