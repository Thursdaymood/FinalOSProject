
import java.util.Scanner;


public class mainArea {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String state = "";
		try{
			while(true) {
				System.out.println("Open room || Join room:");
				state = scan.nextLine();
				
				if(state.toLowerCase() == "join" || state.toLowerCase() == "open") {
					break;
				}
				
			}
			
		}
		catch() {
			System.out.println("Please enter just open or join room");
		}
		
		
		
		
		
		if(state.toLowerCase() == "open") {
			createRoom();
		}
		
		
		
	}
	
	
	
	
	// Create room method
	public static void createRoom() {
		//variable
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the id room(9999, 4444):");
		int port = scan.nextInt();
		roomServer room = new roomServer(port);
		
	}
	

}
