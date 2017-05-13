import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
 
public class InputDialogPenSize extends Stage {
    private Button[] buttons;
    private double result;
    
    public double getResult(){
    	return result;        	
    }

    public  InputDialogPenSize(Stage owner)  {
		
		result = 0;
		buttons = new Button[10];
	
	    initOwner(owner);
	    initModality(Modality.APPLICATION_MODAL);
	    initStyle(StageStyle.UTILITY);
	    setTitle("Size");
	    setResizable(false);            	
	    this.setOnCloseRequest((WindowEvent event) -> {
	            event.consume();
	    });            

        GridPane pane = new GridPane();

        for (int i=1; i <=10; i++){	
        	final double idx = i;
        	buttons[i-1] = new Button( ((idx<10) ? " " : "") + String.valueOf((int)idx) + ((idx<10) ? " " : "")); 
			buttons[i-1].setOnAction(new EventHandler<ActionEvent>(){
			        @Override
			        public void handle(ActionEvent e) {
			          result = idx;
			        hide();
	
			        }
			    });

            pane.add(buttons[i-1],(i-1) % 3 , (i-1)/3 + 1);
        }
	  	
		Scene scene = new Scene(pane);
	    setScene(scene);
    }
}
