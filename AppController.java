import java.net.URL;
import java.net.InetAddress;
import java.util.ResourceBundle;
import java.util.ArrayList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.application.Platform;

public class AppController implements Initializable {
    private static final int BUFFER_SEND_COUNT = 5;

    @FXML Canvas    canvas;
    @FXML Label     textField1;
    @FXML Label     textField2;
    @FXML Label     textField3;
    @FXML TextField sendTextField;

    DrawingHelper helper;

    String DEFAULT_IP = "127.0.0.1";

    private Connecter connecter = null;

    private ArrayList<double[]> receiveBuffer;
    private int bufferCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helper         = new DrawingHelper(canvas);
        receiveBuffer  = new ArrayList<double[]>();
		new Thread(() -> {
        try{
			while(true){
                if(connecter!=null){
	                String order = connecter.receiveMessage();
                	System.out.println("You: " + order);
                	String[] orders = order.split(",", 0);
                	if(orders[0].equals("ps")){

                        resetBuffer();

                		helper.setLineWidth2(Double.parseDouble(orders[1]));
                	}else if(orders[0].equals("pc")){

                        resetBuffer();

                		Color c = null;
        				if( orders[1].equals("RED")){
         				   	c = Color.RED;
       					}else if( orders[1].equals("BLUE")){
            				c = Color.BLUE;
        				}else if( orders[1].equals("GREEN")){
            				c = Color.GREEN;
        				}else if( orders[1].equals("YELLOW")){
           					c = Color.YELLOW;
        				}else if( orders[1].equals("SKYBLUE")){
            				c = Color.SKYBLUE;
        				}else if( orders[1].equals("PINK")){
            				c = Color.PINK;
        				}else if( orders[1].equals("YELLOWGREEN")){
            				c = Color.YELLOWGREEN;
        				}else if( orders[1].equals("BROWN")){
            				c = Color.BROWN;
        				}else if( orders[1].equals("WHITE")){
            				c = Color.WHITE;
        				}else if( orders[1].equals("BLACK")){
            				c = Color.BLACK;
        				}else if( orders[1].equals("WHITESMOKE")){
                            c = Color.WHITESMOKE;
                        }
                		helper.changeLineColor2(c);
                	}else if(orders[0].equals("clear")){
                		helper.clear();
                        receiveBuffer.clear();
                        bufferCount = 0;
                	}else if(orders[0].equals("draw")){
                        double[] array_pos = new double[4];
                		array_pos[0] = Double.parseDouble(orders[1]);
                		array_pos[1] = Double.parseDouble(orders[2]);
                		array_pos[2] = Double.parseDouble(orders[3]);
                		array_pos[3] = Double.parseDouble(orders[4]);
                        receiveBuffer.add(array_pos);

                        bufferCount++;
                        if(bufferCount >= BUFFER_SEND_COUNT){
                            helper.DrawLine(receiveBuffer, "server");
                            receiveBuffer.clear();
                            bufferCount = 0;
                        }
                	}else if(orders[0].equals("chat")){
                        System.out.println(orders[1]);

                        Platform.runLater(() -> setLabelMessage(orders[1]));
                    }

                }
                else{
                	Thread.sleep(10);
                }
			}
			} catch(Exception e){
            System.err.println(e);
        }
		}).start();

    }

    public void AcceptButtonAction(ActionEvent event) {
        System.out.println("click acceptButton");
        try{
        	InetAddress address = InetAddress.getLocalHost();
        	System.out.println(address);
        }catch(Exception e){
        	e.printStackTrace();
        }
		if(connecter==null){
			connecter = new Connecter("server",DEFAULT_IP);
			helper.setConnecter(connecter);
		}
    }

    public void ConnectButtonAction(ActionEvent event) {
		System.out.println("click connectButton");
		if(connecter==null){
		   InputDialogConnect dlg = new InputDialogConnect(App.getInstance().getStage(),DEFAULT_IP);
		   dlg.showAndWait();
		   String result = dlg.getResult();
			connecter = new Connecter("client",result);
			helper.setConnecter(connecter);
		}
    }

    public void EraserButtonAction(ActionEvent event) {
        helper.changeLineColor(Color.WHITESMOKE);

        if(connecter != null){
            connecter.sendMessage("pc,WHITESMOKE");
        }

    }

    public void PenSizeButtonAction(ActionEvent event) {
       System.out.println("PenSizeButtonAction");
       InputDialogPenSize dlg = new InputDialogPenSize(App.getInstance().getStage());
       dlg.showAndWait();
       double result = dlg.getResult();
       helper.setLineWidth(result);

       if(connecter != null){
           connecter.sendMessage("ps," + result);
       }
    }

    public void PenColorButtonAction(ActionEvent event) {
       System.out.println("PenSizeButtonAction");
       InputDialogPenColor dlg = new InputDialogPenColor(App.getInstance().getStage());
       dlg.showAndWait();
       String result = dlg.getResult();
        Color c = null;
        if( result == "RED"){
            c = Color.RED;
        }else if( result == "BLUE"){
            c = Color.BLUE;
        }else if( result == "GREEN"){
            c = Color.GREEN;
        }else if( result == "YELLOW"){
            c = Color.YELLOW;
        }else if( result == "SKYBLUE"){
            c = Color.SKYBLUE;
        }else if( result == "PINK"){
            c = Color.PINK;
        }else if( result == "YELLOWGREEN"){
            c = Color.YELLOWGREEN;
        }else if( result == "BROWN"){
            c = Color.BROWN;
        }else if( result == "WHITE"){
            c = Color.WHITE;
        }else if( result == "BLACK"){
            c = Color.BLACK;
        }

        helper.changeLineColor(c);

       System.out.println("PenColorButtonAction");
       if(connecter != null){
           connecter.sendMessage("pc,"+result);
       }
    }

    public void ClearButtonAction(ActionEvent event) {
       System.out.println("ClearButtonAction");
		Alert alert = new Alert( AlertType.NONE , "" , ButtonType.NO, ButtonType.YES);
		alert.getDialogPane().setContentText("クリアしますか?");
		ButtonType b = alert.showAndWait().orElse( ButtonType.CANCEL);
		if (b == ButtonType.YES){
            helper.clear();

            resetBuffer();

            if(connecter != null){
                connecter.sendMessage("clear");
            }
        }
    }

    public void TextSendButtonAction(ActionEvent event) {
        System.out.println("TextSendButtonAction");
        String s1 = sendTextField.getText();
        sendTextField.setText("");
        if(!s1.equals("")){
            String s2 = "You     : "  + s1;
            if(connecter != null){
                String send = "chat," + "Partner : " + s1;
                connecter.sendMessage(send);
            }
            setLabelMessage(s2);
        }
    }

    public synchronized void setLabelMessage(String s){
        if(textField1.getText().equals("")){
            textField1.setText(s);
        } else if(textField2.getText().equals("")){
            textField2.setText(s);
        } else if(textField3.getText().equals("")){
            textField3.setText(s);
        } else {
            textField1.setText(textField2.getText());
            textField2.setText(textField3.getText());
            textField3.setText(s);
        }
    }

    public void resetBuffer(){
        receiveBuffer.clear();
        bufferCount = 0;
    }

}
