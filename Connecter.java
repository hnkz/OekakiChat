import java.io.*;
import java.net.*;
import java.net.InetAddress;

class Connecter{

    private String       	 	SERVERIP;
    private final int           PORT        = 12354;
    private ServerSocket        serverSocket;
    private Socket              socket;
    private InputStream         inputStream;
    private InputStreamReader   inputStreamReader;
    private BufferedReader      bufferedReader;
    private OutputStream        outputStream;
    private OutputStreamWriter  outputStreamWriter;
    private BufferedWriter      bufferedWriter;
    private PrintWriter         printWriter;

    private String              string = "";

	public Connecter(String type, String serverIp){
		SERVERIP = serverIp;
        try{
            switch(type){
                case "server":
                    serverSocket        = new ServerSocket(12354);
                    socket              = serverSocket.accept();
                    break;
                case "client":
                    socket              = new Socket(SERVERIP,PORT);
                    break;
                default:
                    System.err.println("You must select connection type!");
                    break;
            }
            System.out.println("Connection Succeded!");
            inputStream         = socket.getInputStream();
            inputStreamReader   = new InputStreamReader(inputStream);
            bufferedReader      = new BufferedReader(inputStreamReader);

            outputStream        = socket.getOutputStream();
            outputStreamWriter  = new OutputStreamWriter(outputStream);
            bufferedWriter      = new BufferedWriter(outputStreamWriter);
            printWriter         = new PrintWriter(bufferedWriter);

        } catch(Exception e){
            System.err.println(e);
        }
    }

    public void sendMessage(String s){
        printWriter.println(s);
        printWriter.flush();
    }

    public String receiveMessage(){
		try{
			string =  bufferedReader.readLine();
			} catch(Exception e){
                System.err.println(e);
		}
        return string;
    }

    public void closeConnection(){
        try{
            socket.close();
        } catch(Exception e){
            System.err.println(e);
        }
    }

    public void setAddress(String address){
    	SERVERIP = address;
    }
}
