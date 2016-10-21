import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server{
	public static void main(String[] args) throws Exception {
	  int port = 6969;
	  ServerSocket serverSocket = new ServerSocket(port);
	  System.err.println("Started server on port "+port);
	
	  while (true) {
	    Socket clientSocket = serverSocket.accept();
	    Runnable connectionHandler = new ConnectionHandler(clientSocket);
	    new Thread(connectionHandler).start();
	    System.out.println("Accepted connection from client");
	
	  }
	}
}
class ConnectionHandler implements Runnable{
	Socket clientSocket;
	String username;
	public ConnectionHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		
		BufferedReader in = null;
	    PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    String s;
	    try {
	        while ((s = in.readLine()) != null) {
	        	username = s.substring(0, s.indexOf(","));
	        	
	        	String text = s.substring(s.indexOf(",")+1, s.length());
	        	String msg = "<"+username+"> "+text; 
	        	if(text.equals(""))
	        	{
		            out.println(msg);
		            out.flush();
	        	}
	        	else if(text.equals("/exit"))
	        	{
	        		out.println("Disconnected from server");
	        		out.flush();
	        		clientSocket.close();
	        		break;
	        	}
	        	else
	        	{
		            out.println(msg);
		            System.out.println(msg);
		            out.flush();
	        	}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    // close IO streams, then socket
	    System.err.println("Closing connection with client");
	    out.close();
	    try {
			in.close();
		    clientSocket.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}