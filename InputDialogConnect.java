import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;    
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
 
public class InputDialogConnect extends Stage {
    private String result;
    private TextField txtInputIp;
   	private Button btnOk;
    
    public String getResult(){
    	return result;
    }

    public  InputDialogConnect(Stage owner, String defaultIp)  {

		VBox vbox = new VBox(1);
		txtInputIp = new TextField(defaultIp);
		btnOk = new Button("OK");			
		
	    initOwner(owner);
	    initModality(Modality.APPLICATION_MODAL);
	    initStyle(StageStyle.UTILITY);
	    setTitle("IP");
	    setResizable(false);
	    this.setOnCloseRequest((WindowEvent event) -> {
	            event.consume();
	    });            

		btnOk.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
			  	result = txtInputIp.getText();
				hide();
	        }
	  	});

		BorderPane pane = new BorderPane();
		pane.setCenter(txtInputIp);
		vbox.getChildren().addAll(pane, btnOk);
		Scene scene = new Scene(vbox);
	    setScene(scene);
    }
}
