
import java.net.InetAddress;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;



public class client {
	
	public static int port = 5001;
	public static String ipUser = "127.0.0.1";
	
	// Constructor
	client(){

		//Create thread to take responsibility about client
		ClientRunnable task = new ClientRunnable(ipUser, port);
		Thread threadClient = new Thread(task);
		threadClient.start();		
	}
	
	
	
// The the end of client class	
}

// About thread
class ClientRunnable implements Runnable {
	
	// Variables
	private static int port = 0;
	private static String ip= "";
	private static Scanner scan = new Scanner(System.in);
	//variable network
	Socket socket = null;
	DataInputStream input = null;
	DataOutputStream output = null;

	// Constructor
	ClientRunnable(String ipUser, int portUser) {

		this.port = portUser;
		this.ip = ipUser;
	}

	@Override
	public void run() {
		
		try {
			// 1) Connect the server in the same socket by choosing ip of server and port number
			socket = new Socket(ip, port);
			output = new DataOutputStream(socket.getOutputStream());
			
			// 2) Communicate
			
			// test connection
			output.writeUTF("\tHello!!");
			
			// 3) Close
			output.close();
			socket.close();
			
			
		}catch(Exception error) {
			System.err.println(error.getMessage());
		}

	}
}