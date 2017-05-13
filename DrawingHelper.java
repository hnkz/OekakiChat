import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

public class DrawingHelper{
    private static final int BUFFER_SEND_COUNT = 5;
	private Canvas canvas;
	private Connecter connecter;
    private GraphicsContext gc;
    private Color color1=Color.BLACK,color2=Color.BLACK;
    private double size1=3,size2=3;

    private double oldPosX;
    private double oldPosY;

    private ArrayList<double[]> clientBuffer  = new ArrayList<double[]>();
    private int     bufferCount = 0;
    private String  sendMsg = "";

    DrawingHelper(Canvas c){
    	connecter = null;
    	canvas = c;
		gc = canvas.getGraphicsContext2D();

        EventHandler<MouseEvent> canvasClickFilter= ( event ) -> {
			oldPosX = event.getX();
			oldPosY = event.getY();
        };
        canvas.addEventFilter( MouseEvent.MOUSE_PRESSED , canvasClickFilter );

        EventHandler<MouseEvent>  canvasMoveFilter= ( event ) -> {
    		double currentX = event.getX();
    		double currentY = event.getY();

            double sx = oldPosX;
            double sy = oldPosY;
            double ex = currentX;
            double ey = currentY;
            double[] array_pos = {sx,sy,ex,ey};
            clientBuffer.add(array_pos);
            if(connecter != null){
                
                sendMsg = "draw," + oldPosX + "," + oldPosY + "," + currentX + "," + currentY + ",";
                System.out.println("I : " + sendMsg);
		        connecter.sendMessage(sendMsg);
                
                bufferCount++;
                if(bufferCount >= BUFFER_SEND_COUNT){
                    DrawLine(clientBuffer, "client");
                    clientBuffer.clear();
                    bufferCount = 0;
                }
            }

			oldPosX = currentX;
			oldPosY = currentY;
        };
        canvas.addEventFilter( MouseEvent.MOUSE_DRAGGED,canvasMoveFilter);

        oldPosX = 0;
        oldPosY = 0;
    }    

    public void setLineWidth(double size){
        sendMsg       = "";
        bufferCount   = 0;
        clientBuffer.clear();
        size1 = size;
    }
    
    public void changeLineColor(Color color){
        sendMsg       = "";
        bufferCount   = 0;
        clientBuffer.clear();
		color1 = color;
    }

    public void setLineWidth2(double size){
    	size2 = size;
    }

    public void changeLineColor2(Color color){
    	color2 = color;
    }

	public void changePen(double size,Color color){
		gc.setLineWidth(size);
		gc.setStroke(color);
	}

	public double getSize1(){
		return size1;
	}

	public double getSize2(){
		return size2;
	}

	public Color getColor1(){
		return color1;
	}

	public Color getColor2(){
		return color2;
	}

    public synchronized void DrawLine(ArrayList<double[]> receiveBuffer, String s){
        if(s.equals("client")){
            changePen(size1, color1);
        } else if(s.equals("server")){
            changePen(size2,color2);
        }
        for(int i = 0; i < BUFFER_SEND_COUNT; i++){
            double[] array_pos = receiveBuffer.get(i);
            double sx = array_pos[0];
            double sy = array_pos[1];
            double ex = array_pos[2];
            double ey = array_pos[3];
            gc.strokeLine(sx, sy, ex,ey);
        }
    }

    public void setConnecter(Connecter c){
    	connecter = c;
    }

    public synchronized void clear(){
      gc.setFill(Color.WHITESMOKE);
      gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
      sendMsg       = "";
      bufferCount   = 0;
      clientBuffer.clear();
    }
}
