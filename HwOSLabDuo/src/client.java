
import java.net.InetAddress;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class client {
	private static int port = 0;
	private static String ip= "";
	private static Scanner scan = new Scanner(System.in);
	
	// Constructor
	client(){
		
	}

	public static void main(String[] args) {
		
		ClientRunnable tmp = new ClientRunnable(port);
		
		

	}
	
	// get method
	public String getIP() {
		return this.ip;
	}
	public int getPort() {
		return this.port;
	}
	// set method
	public void setIP(String user) {
		this.ip = user;
	}
	public void setPort(int user) {
		this.port = user;
	}
	
}

class ClientRunnable implements Runnable {

	private static int port = 0;
	private static String ip= "";
	private static Scanner scan = new Scanner(System.in);

	// Constructor
	ClientRunnable(int portUser) {

		this.port = portUser;
	}

	@Override
	public void run() {

	}
}