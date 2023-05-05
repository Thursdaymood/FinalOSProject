
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class mainArea {
	private static String space3 = "   "; // 3 times
	private static int defaultPort = 9999;

	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		// first input from user [JOIN / OPEN]
		printLine(45);
		System.out.print(space3+ "Open the room || Join the room: ");
		String userType = scan.nextLine().toLowerCase();

		while (!(userType.equals("open") || userType.equals("join"))) {
			
			System.out.println(space3+"Please enter just \"open\" or \"join\" ");
			System.out.print(space3+"Open the room || Join the room: ");
			userType = scan.nextLine().toLowerCase();
		}
		
		//If user open the room
		if(userType.equals("open")){
			//Server
			createRoom();
			
			// TODO: Setting room

		}else if(userType.equals("join")){
			// Client
			System.out.println("Hello");
		}
		

	}

	// Create room method
	private static void createRoom() {
		
		System.out.println("\n"+space3+"Detail room");
		System.out.println(space3+"Id room: "+defaultPort);
		roomServer room = new roomServer(defaultPort);

	}

	private static void printLine(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("-");
		}
		System.out.println();

	}

	
}
