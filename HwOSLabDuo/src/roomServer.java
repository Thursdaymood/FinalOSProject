
// Network
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class roomServer {
	
	
	private static int portNum = 0;

	//variable
	static int score;
    static int life = 3;
	static String[] word_random ;
	
	//Constructor
	roomServer(int portNum){
		this.portNum = portNum;
		// Create thread to take responsibility about roomServer
		ServerRunnable task = new ServerRunnable(portNum);
		Thread threadServer = new Thread(task);
		threadServer.start();

	}

	//check letter
	public static int getLife() {
        return life;
    }

    public static void damageLife() {
        life -= 1;
    }

    public static int getScore() {
        return score;
    }

    public static void upScore() {
        score +=1;
    }
    
    public static void checkLetter(int turn, String letterUser) {

        String word = word_random[turn];

        if (word.contains(letterUser)){
            int count = 0;
            for(int i = 0; i < word.length(); i++) 
            {
                if(word.charAt(i) == letterUser.charAt(0)) {
                    upScore();
                }
            }
        }

        else{
            roomServer.damageLife();
        }
    }
	
//The end of roomServer class
}

// About thread
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


	}
	
	@Override // Run method
	public void run() {
		
		try {
			// 1) Open the socket by specific port
			System.out.println(space3+"Waiting the player . . . ");
			server = new ServerSocket(portUser);
	
			// 2) Waiting for client connect
			socket = server.accept(); // connect
			System.out.println("\tOpen the room");
			input= new DataInputStream(socket.getInputStream()); // for receiving
			output = new DataOutputStream(socket.getOutputStream()); // for sending
			

			// 3) Communicate
			
			// roomServer connection
			String message = input.readUTF(); // receive string
			System.out.println("\t"+message);
			// 4) Close
			socket.close();
			input.close();
			output.close();
			
		}catch(Exception error){
			System.out.println(error);
		}
		
	
	}
	
	//set method room
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
