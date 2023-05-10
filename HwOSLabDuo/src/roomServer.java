
// Network
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class roomServer {

	private static int portNum = 0;

	// variable
	static int score;
	static int life = 3;
	static ArrayList<String> word_random = new ArrayList<String>(5);

	// Constructor
	roomServer(int portNum) {
		this.portNum = portNum;
		// Create thread to take responsibility about roomServer
		ServerRunnable task = new ServerRunnable(portNum);
		Thread threadServer = new Thread(task);
		threadServer.start();

	}

	public static void main(String[] args) {
		randomWords();

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
			roomServer.damageLife();
		}
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

	// Random word in gameFile and insert it in woed_random
	public static void randomWords() {
		Random ran = new Random();
		File file = new File("resc//miniVocab.txt");
		int tmpNum = ran.nextInt(834) + 1;
		String line;
		Scanner scan;
		try {

			for (int i = 0; i < 5; i++) {

				int count = 0;
				scan = new Scanner(file); // must create a new one before start loop
				
				while (scan.hasNextLine()) {

					line = scan.nextLine();
					if (count == tmpNum) {
						System.out.println(line);
					}
					count++;
				}
				tmpNum = ran.nextInt(835) + 1; // generate new number
				// System.out.println(i+": "+tmpNum); //details

			}
		} catch (Exception error) {
			System.out.println(error);
		}

	}

//The end of roomServer class
}

// About thread
class ServerRunnable implements Runnable {

	// variable
	private static boolean stateRoom;
	private static int portUser;
	ServerSocket server = null;
	Socket socket = new Socket();
	DataInputStream input = null;
	DataOutputStream output = null;

	private static String space3 = "   "; // 3 times

	// Constructor
	ServerRunnable(int portNum) {
		this.portUser = portNum;

	}

	@Override // Run method
	public void run() {

		try {
			// 1) Open the socket by specific port
			System.out.println(space3 + "Waiting the player . . . ");
			server = new ServerSocket(portUser);

			// 2) Waiting for client connect
			socket = server.accept(); // connect
			System.out.println("\tOpen the room");
			input = new DataInputStream(socket.getInputStream()); // for receiving
			output = new DataOutputStream(socket.getOutputStream()); // for sending

			// 3) Communicate

			// roomServer connection
			String message = input.readUTF(); // receive string
			System.out.println("\t" + message);

			message = "";
			// test connection
			while (!message.toLowerCase().equals("close")) {

				message = input.readUTF();
				System.out.println("\t" + message);
			}

			// 4) Close
			socket.close();
			input.close();
			output.close();

		} catch (Exception error) {
			System.out.println(error);
		}

	}

	// set method room
	public void setPortRoom(int user) {
		this.portUser = user;
	}

	public void setStateRoom(boolean user) {
		this.stateRoom = user;
	}

	// get method
	public int getPortNum() {
		return portUser;
	}

	public boolean stateRoom() {
		return stateRoom;
	}

}
