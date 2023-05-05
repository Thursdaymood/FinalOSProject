
import java.util.ArrayList;
import java.util.Scanner;


public class mainArea {
	
	private static String spaces3 = "   ";
	private static ArrayList<Integer> ports = new ArrayList<Integer>(5); // Just 4 rooms
	private static Scanner scan = new Scanner(System.in);

	
	public static void main(String[] args) {
		
		// default port number
		ports.add(5001);
		ports.add(5002);
		ports.add(5003);
		ports.add(5004);
		//------------------------
		// create room
		createRoom(ports.get(0));
		
		
		// navigate page
		// GUI java
		
	}

	// Create room method
	private static void createRoom(int port) {
		printLine(20);
		System.out.println("\tCreate room");
		roomServer room = new roomServer(port);
	}

	private static void joinRoom(int port, String ip) {
		printLine(20);
		System.out.println("\tJoin room");
		client user = new client(port,ip);
	}
	private static void printLine(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("-");
		}
		System.out.println();

	}

	
}
