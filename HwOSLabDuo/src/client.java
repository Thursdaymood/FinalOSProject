
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
		if (player == 1 && room != -1){
			player1Runnable task1 = new player1Runnable(ports[room-1][0]);
			Thread player1 = new Thread(task1);
			player1.start();
		}
		if (player == 2 && room != -1) {
			player2Runnable task2 = new player2Runnable(ports[room-1][1]);
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
	
	// Game variable
	ArrayList<String> wordsPlay = new ArrayList<String>();
	
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
			
			// ------------------In game-----------------------------	
			while (!stateRoom) {
				
				// Receive the vocabulary from server
				String vocab = "";
				while(vocab.equals("end")){
					vocab = input.readUTF();
					wordsPlay.add(vocab);
				}
				// GUI game
				game guiPlay = new game();
				guiPlay.addWords(wordsPlay);
				
				// (loop: play <-> wait)
				boolean inPlaySection = true;
				while(inPlaySection){
					
					// Playing -> 1 letter
					boolean playTurn = true;
					while(playTurn){
						String ans = guiPlay.getLetter();
						output.writeUTF(ans);
						
						
						
						
					}
					
					// Waiting
					// waiting until receive "your turn"
					boolean waitTurn = true;
					while(waitTurn){
						
					}
					
				}
				
				
				
				
				
				
				stateRoom = true;
			}

			// Close
			socket.close();
			input.close();
			output.close();



		} catch (Exception error) {
			System.out.println(error);
			System.out.println("Player1's Runnable section");
		}

	}
	private void closeRoom() {
		this.stateRoom = true;
	}
}

class player2Runnable implements Runnable {
	// Network
	Socket socket = null;
	DataInputStream input = null;
	DataOutputStream output = null;
	boolean stateRoom = false;
	private int port = 0;
	
	// Game variable
	ArrayList<String> wordsPlay = new ArrayList<String>();

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
			while (!stateRoom) {
				
				// Receive the vocabulary from server
				String tmp = "";
				while(!tmp.equals("end")){
					tmp = input.readUTF();
					wordsPlay.add(tmp);
				}
				// GUI game
				game guiPlay = new game();
				guiPlay.addWords(wordsPlay);
				
				// (loop: wait <-> play)
				boolean inPlaySection = true;
				while(inPlaySection){

					// Waiting
					// waiting until receive "your turn"
					boolean waitTurn = true;
					while(waitTurn){
						
						
						
						
					}
					
					// Playing -> 1 letter
					boolean playTurn = false;
					while(!playTurn){
						
					}
					
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				stateRoom = true;
			}
			// Close
			socket.close();
			input.close();
			output.close();

		}catch (Exception error) {
			System.out.println(error);
			System.out.println("Player2's Runnable section");
		}

	}
	 

	private void closeRoom() {
		this.stateRoom = true;
	}

}