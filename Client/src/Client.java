import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
    	
        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String username = sc.nextLine();
        if(username.contains(","))
        {
            System.err.print("Invalid name");
        	return;
        }
        System.out.print("Host: ");
        String hostIP = sc.nextLine();
        System.out.print("Port: ");
        int portNumber = sc.nextInt();

        try {
            Socket socket = new Socket(hostIP, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
         
            String userInput,text;
            while ((text = stdIn.readLine()) != null) {
            	userInput = username+","+text;
                out.println(userInput);
                System.out.println(in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostIP);
            System.exit(1);
        } 
    }
}