
// Network
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class roomServer {
	private static int numPlayer = 1;
	
	// Network
	ServerSocket server = null;
	Socket socket;
	ServerRunnable task1;
	ServerRunnable task2;
	Thread threadServer1;
	Thread threadServer2;

	// Constructor
	roomServer(int port) {
		
		System.out.println("\tWaiting the player . . . ");
		
		try {
			
			
			while(numPlayer != 4){
				// Create thread to take responsibility about roomServer
				
				if(numPlayer == 1){
					server = new ServerSocket(port);
					socket = server.accept(); // Waiting for clients connecting
					task1 = new ServerRunnable(socket);
					threadServer1 = new Thread(task1);
					System.out.println("Player1 is in the room");
					numPlayer+=1;
				}
				if(numPlayer == 2){
					server = new ServerSocket(port);
					socket = server.accept();
					task2 = new ServerRunnable(socket);
					threadServer2 = new Thread(task2);
					System.out.println("Player2 is in the room");
					numPlayer+=1;
				}
				if(numPlayer == 3 ){
					threadServer1.start();
					threadServer2.start();
					System.out.println("Players are in the room");
					numPlayer+=1;
				}
			}
		}catch(Exception error) {
			System.out.println(error);
		}
	}



	// The end of roomServer class
}

// About thread
class ServerRunnable implements Runnable {
	// variable in game
	static int score;
	static int life = 3;
	static ArrayList<String> word_random = new ArrayList<String>(5);


	// variable
	private static boolean stateRoom = true;
	Socket soc;

	DataInputStream input = null;
	DataOutputStream output = null;


	// Constructor
	ServerRunnable(Socket socket) {
		this.soc = socket;
	}

	@Override // Run method
	public void run() {
		try {
			input = new DataInputStream(soc.getInputStream()); // for receiving
			output = new DataOutputStream(soc.getOutputStream()); // for sending
			
			while(true){
				// get words to play

				for(int i = 0 ; i < word_random.size() ; i++ ){
					output.writeUTF(word_random.get(i));
					System.out.println(word_random.get(i));
				}
		
				break;
			}
		} catch (Exception error) {
			System.out.println(error);
		}
		System.out.println("Finish");

	}
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
		score += 1;
	}

	
	// check letter
	public static void checkLetter(int turn, String letterUser) {

		String word = word_random.get(turn);

		if (word.contains(letterUser)) {
			int count = 0;
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == letterUser.charAt(0)) {
					upScore();
				}
			}
		}

		else {
			//roomServer.damageLife();
		}
	}





}
