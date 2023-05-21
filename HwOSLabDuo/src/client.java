
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

class playFrame {

	// Picture
	private ImageIcon imageIcon = new ImageIcon("resc//idea.png");
	private ImageIcon bg = new ImageIcon("resc//pixelSky.gif");

	// Structure
	private JFrame frame;
	private JLabel labelMain;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 250;

	// Constructor
	playFrame() {

		playGUI();
	}

	private void playGUI() {

	}

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
			while (receiveVocab){
				String tmp = input.readUTF();
				
				if(tmp.equals("end")){
					break;
				}else {
					//System.out.println("Player1 got "+tmp);
					wordsPlay.add(tmp);
				}
				
			}
			// GUI game
			this.guiPlay = new game(wordsPlay);
			guiPlay.setVisible();
			System.out.println(wordsPlay.size());
			int round = 1;
			System.out.println("Player 1 received word sucessfully");
			// ------------------In game-----------------------------
			while (!stateRoom) {
				// (loop: play <-> wait)
				boolean inPlaySection = true;
				while (inPlaySection) {

					// Playing -> 1 letter
					boolean playTurn = true;
					while (playTurn) {

						boolean waitingKeyBoard = true;
						String keyboard = "";
						int life = 0;
						int score = 0;
						System.out.println("Waiting player1 click");
						while (waitingKeyBoard) {
							while (true/* getKeyBoard */) {
								boolean isEmpty = guiPlay.getKeyBoard().isEmpty();
								if(!isEmpty){
									keyboard = guiPlay.getKeyBoard().get(0);
									break;
								}
								
							}
							
							life = guiPlay.getLifePlayer(1); // get life
							score = guiPlay.getScorePlayer(1); // get score
							waitingKeyBoard = false;
						}
						System.out.println("Record player1");
						// Send all data to server, and server send to the player 2
						output.writeUTF(keyboard);
						output.writeInt(life);
						output.writeInt(score);
						playTurn = guiPlay.changeTurn();// changeTurn
					}

					// Waiting
					boolean waitTurn = true;
					while (waitTurn) {
						String keyboard = "";
						int life = 0;
						int score = 0;
						System.out.println("Waiting player2 play");
						while (keyboard.equals("") || life == 0 || score == 0) {
							// wait until get all data from player2 -> break loop
							keyboard = input.readLine();
							life = input.readInt();
							score= input.readInt();
						}
						// set all data of player2
						guiPlay.setScorePlayer(2, score); // set the score
						guiPlay.setLifeOfPlayer(2,life); // set the life
						// guiPlay.addKeyBoardPlay();// set keyboard
						waitTurn = guiPlay.changeTurn();
					}

//					if(// guiPlay.getRound == 4){
//						break;
//					}

				}
				// Close
				socket.close();
				input.close();
				output.close();
				stateRoom = true;
			}
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
			while (receiveVocab){
				String tmp = input.readUTF();
				//System.out.println("Player2 got "+tmp);
				if(tmp.equals("end")){
					break;
				}else {
					wordsPlay.add(tmp);
				}
				
			}
			// GUI game
			this.guiPlay = new game(wordsPlay);
			guiPlay.setVisible();
			System.out.println("Player 2 received word sucessfully");

			while (!stateRoom) {
				// (loop: wait <-> play)
				boolean inPlaySection = true;
				while (inPlaySection) {

					// Waiting
					boolean waitTurn = true;
					while (waitTurn) {
						String keyboard = "";
						int life = 0;
						int score = 0;
						System.out.println("Waiting player1 play");
						while (keyboard.equals("") || life == 0 || score == 0) {
							// wait until get all data from player1 -> break loop
							keyboard = input.readLine();
							life = input.readInt();
							score = input.readInt();
						}
						// set all data of player1
						guiPlay.setScorePlayer(1, score); // set the score
						guiPlay.setLifeOfPlayer(1, life); // set the life
						// guiPlay.addKeyBoardPlayer();// set keyboard

						waitTurn = guiPlay.changeTurn();

					}
					boolean playTurn = false;
					while (playTurn) {

						boolean waitingKeyBoard = true;
						String keyboard = "";
						int life = 0;
						int score = 0;
						System.out.println("Waiting player1 click");
						while (waitingKeyBoard) {
							while (true/* getKeyBoard */) {
								boolean isEmpty = guiPlay.getKeyBoard().isEmpty();
								if(!isEmpty){
									keyboard = guiPlay.getKeyBoard().get(0);
									break;
								}
							}
							life = guiPlay.getLifePlayer(2); // get life
							score = guiPlay.getScorePlayer(2); // get score
							waitingKeyBoard = false;
						}
						System.out.println("Record player2");
						// Send all data to server, and server send to the player 1
						output.writeUTF(keyboard);
						output.writeInt(life);
						output.writeInt(score);
						playTurn = guiPlay.changeTurn();// changeTurn
					}

//					if(// guiPlay.getRound == 4){
//						break;
//					}

				}
				// Close
				socket.close();
				input.close();
				output.close();
				stateRoom = true;
			}

		} catch (Exception error) {
			System.out.println(error);
			System.out.println("Player2's Runnable section");
		}

	}

	private void closeRoom() {
		this.stateRoom = true;
	}

}