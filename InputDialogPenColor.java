import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class InputDialogPenColor extends Stage {
    private Button[] buttons;
    private String result;
    private String strDisp;
    private Label lblDisp;
    private String[] color = new String[10];

    private void UpdateLbl(){
    	lblDisp.setText(strDisp);
    }

    public String getResult(){
    	return result;
    }

    public  InputDialogPenColor(Stage owner)  {
		//初期化
		VBox vbox = new VBox(10);
		HBox hbox = new HBox(5);
		result = "";
		strDisp = "";
		buttons = new Button[12];
		lblDisp = new Label();

        color[0] = "RED";
        color[1] = "BLUE";
        color[2] = "GREEN";
        color[3] = "YELLOW";
        color[4] = "SKYBLUE";
        color[5] = "PINK";
        color[6] = "YELLOWGREEN";
        color[7] = "BROWN";
        color[8] = "WHITE";
        color[9] = "BLACK";

	    initOwner(owner);
	    initModality(Modality.APPLICATION_MODAL);
	    initStyle(StageStyle.UTILITY);
	    setTitle("color");
	    setResizable(false);
	    this.setOnCloseRequest((WindowEvent event) -> {
	            event.consume();
	    });

        GridPane pane = new GridPane();

        for (int i=0; i < 10; i++){
        	final String c = color[i];
        	buttons[i] = new Button(String.valueOf(c));
            buttons[i].setMinWidth(130);
			buttons[i].setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e) {
                        result = c;
                        hide();
                    }

			    });

            pane.add(buttons[i],(i) % 3 , (i)/3 + 1);
        }
		vbox.getChildren().addAll(lblDisp, pane, hbox);

		Scene scene = new Scene(vbox);
	    setScene(scene);
    }
}
