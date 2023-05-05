
// Network
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class roomServer {
	
	// variable
	private static int portUser; // Server -> port number
	private static String space3 = "   "; // 3 times
	
	
	//Constructor
	roomServer(int portNum){
		
		// Create thread to take responsibility about roomServer
		ServerRunnable task = new ServerRunnable(portNum);
		Thread tmpThread = new Thread(task);
		tmpThread.start();
	}
//The end of roomServer class
}



// About multi-thread
class ServerRunnable implements Runnable{
	
	//variable
	private static boolean stateRoom;
	private static int portUser;
	ServerSocket server = null;
	Socket socket = new Socket();
	DataInputStream input = null;
	DataOutputStream output = null;
	private static String space3 = "   "; // 3 times 
	

	//Constructor
	ServerRunnable(int portNum){
		this.portUser = portNum;
		this.stateRoom = true;

	}
	
	@Override // Run method
	public void run() {
		
		try {
			// Open the socket by specific port
			System.out.println(space3+"Waiting the player . . . ");
			server = new ServerSocket(portUser);
	
			//----Waiting for client connect
			socket = server.accept();
			System.out.println("Open the room");
			input= new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			
			while(stateRoom) {
				
				String message = input.readUTF(); // receive the 
				System.out.println(message);
				
				if(message.toLowerCase() == "close") {
					setStateRoom(false);
				}
				
			}
			socket.close();
			input.close();
			output.close();
			
		}catch(Exception error){
			System.out.println(error);
		}
		
	
	}
	
	//set method
	public void setPortRoom(int user) {
		this.portUser = user;
	}
	public void setStateRoom(boolean user) {
		this.stateRoom = user;
	}
	
	//get method
	public int getPortNum() {
		return portUser;
	}
	public boolean stateRoom() {
		return stateRoom;
	}
	
	private void printLine(int num) {
		for(int i = 0 ; i< num ; i++) {
			System.out.print("-");
		}
		System.out.println();
		
	}
	
}
