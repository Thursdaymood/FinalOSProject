
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
		
		System.out.print("Enter (1 for run roomServer): ");
		String state = scan.next();
		
		System.out.print("Enter ip address: ");
		String tmp = scan.next();

		
		if(state.equals("1")){
			createRoom(ports.get(0));
			
		}else{
			joinRoom(ports.get(0), tmp);
		}
		
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
