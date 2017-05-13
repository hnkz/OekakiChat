import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
 
public class App extends Application {
    Stage stage;
    
	private static App singleton;

    @Override
    public void start(Stage primaryStage) throws Exception {
	singleton = this;

	stage = primaryStage;
	Pane myPane_top = (Pane)FXMLLoader.load(getClass().getResource("app.fxml"));
	Scene myScene = new Scene(myPane_top);
	primaryStage.setScene(myScene);
	primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
	public  static App getInstance(){
		return singleton;
	}
	
	public Stage getStage(){
		return stage;
	}
}
