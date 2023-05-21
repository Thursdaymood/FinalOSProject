
import java.net.InetAddress;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class client {

	int[][] ports = { { 2221, 2222 }, { 2223, 2224 }, { 2225, 2226 }, { 2227, 2228 } };

	// Constructor
	public client(int player, int room) {

		// Create thread to take responsibility about client
		if (player == 1 && room != -1) {
			player1Runnable task1 = new player1Runnable(ports[room - 1][0]);
			Thread player1 = new Thread(task1);
			player1.start();
		}
		if (player == 2 && room != -1) {
			player2Runnable task2 = new player2Runnable(ports[room - 1][1]);
			Thread player2 = new Thread(task2);
			player2.start();
		}
		if (player == 2 && room == -1) {
			introFrame guiplayer2 = new introFrame(2);
		}

	}

	public static void main(String[] args) {
		System.out.println("Start game ...");
		printLine(20);
		client player2 = new client(2, -1);

	}

	private static void printLine(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("-");
		}
		System.out.println();

	}

// The the end of client class	
}

// About thread
class player1Runnable implements Runnable {

	// network
	Socket socket = null;
	DataInputStream input = null;
	DataOutputStream output = null;
	private int port = 0;
	boolean stateRoom = false;
	private game guiPlay;

	// Game variable
	static ArrayList<String> wordsPlay = new ArrayList<String>();

	// Constructor
	player1Runnable(int portUser) {
		this.port = portUser;

	}

	@Override
	public void run() {

		try {

			// Connect the server in the same socket by choosing ip of server and port
			InetAddress localHost = InetAddress.getLocalHost();
			String ipAddress = localHost.getHostAddress();

			socket = new Socket(ipAddress, port);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			System.out.println("\tPlayer1 is ready ...");

			// Receive the vocabulary from server
			boolean receiveVocab = true;
			while (receiveVocab) {
				String tmp = input.readUTF();

				if (tmp.equals("end")) {
					break;
				} else {
					// System.out.println("Player1 got "+tmp);
					wordsPlay.add(tmp);
				}

			}
			// ------------------In game-----------------------------
			// GUI game
			this.guiPlay = new game(wordsPlay, output, 1);
			guiPlay.setVisible();
			int round = 1;
			System.out.println("Player 1 received word sucessfully");

			// Close
			socket.close();
			input.close();
			output.close();
			stateRoom = true;

		} catch (Exception error) {
			System.out.println(error);
			System.out.println("Player1's Runnable section");
		}

	}

	private void closeRoom() {
		this.stateRoom = true;
	}

	private static boolean checkLetter(int turn, String letter) {

		String tmpWord = wordsPlay.get(turn - 1);

		if (tmpWord.contains(tmpWord)) {
			return true;
		}
		return false;
	}

}

class player2Runnable implements Runnable {
	// Network
	Socket socket = null;
	DataInputStream input = null;
	DataOutputStream output = null;
	boolean stateRoom = false;
	private int port = 0;
	private game guiPlay;

	// Game variable
	static ArrayList<String> wordsPlay = new ArrayList<String>();

	// Constructor
	public player2Runnable(int port) {
		this.port = port;

	}

	@Override
	public void run() {
		try {
			// IP Address
			InetAddress localHost = InetAddress.getLocalHost();
			String ipAddress = localHost.getHostAddress();

			// Create socket
			socket = new Socket(ipAddress, port);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			System.out.println("\tPlayer2 is ready ...");

			// ------------------In game-----------------------------
			// Receive the vocabulary from server
			boolean receiveVocab = true;
			while (receiveVocab) {
				String tmp = input.readUTF();
				// System.out.println("Player2 got "+tmp);
				if (tmp.equals("end")) {
					break;
				} else {
					wordsPlay.add(tmp);
				}

			}
			// GUI game
			this.guiPlay = new game(wordsPlay, output, 2);
			guiPlay.setVisible();
			System.out.println("Player 2 received word sucessfully");

		} catch (Exception error) {
			System.out.println(error);
			System.out.println("Player2's Runnable section");
		}

	}

	private void closeRoom() {
		this.stateRoom = true;
	}

}