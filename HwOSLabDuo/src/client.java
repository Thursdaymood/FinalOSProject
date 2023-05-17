
import java.net.InetAddress;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class client {

	// Constructor
	public client(int player, int port) {
		// Create thread to take responsibility about client
		if (player == 1 && port != 0) {
			player1Runnable task1 = new player1Runnable(port);
			Thread player1 = new Thread(task1);
			player1.start();
		}
		if (player == 2 && port != 0) {
			player2Runnable task2 = new player2Runnable(port);
			Thread player2 = new Thread(task2);
			player2.start();
		}
		if (player == 2 && port == 0) {
			introFrame guiplayer2 = new introFrame(2);
		}

	}

	public static void main(String[] args) {
		System.out.println("Start game ...");
		printLine(20);
		client player2 = new client(2, 0);

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
	private static int port = 0;
	boolean stateRoom = false;
	
	private static Scanner scan = new Scanner(System.in);

	// Constructor
	player1Runnable(int portUser) {

		this.port = portUser;
	}

	@Override
	public void run() {

		try {
			System.out.println("\tPlayer1 is ready ...");
			// 1) Connect the server in the same socket by choosing ip of server and port
			// number
			InetAddress localHost = InetAddress.getLocalHost();
			String ipAddress = localHost.getHostAddress();
			socket = new Socket(ipAddress, port);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			String message;
			// 2) Communicate
			while (!stateRoom) {

				message = scan.nextLine();
				output.writeUTF("\t" + message);
				String tmp = input.readUTF();
				System.out.println(tmp);

				if (message.toLowerCase().equals("close")) {
					closeRoom();
				}
			}

			// Close
			socket.close();
			input.close();
			output.close();



		} catch (Exception error) {
			System.out.println(error);
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

	// Picture
	private ImageIcon imageIcon = new ImageIcon("resc//idea.png");
	private ImageIcon bg = new ImageIcon("resc//pixelSky.gif");

	// Structure
	private JFrame frame;
	private JLabel labelMain;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 250;

	Scanner scan = new Scanner(System.in);

	// Constructor
	public player2Runnable(int port) {
		this.port = port;

	}

	@Override
	public void run() {
		try {
			System.out.println("\tPlayer2 is ready ...");
			InetAddress localHost = InetAddress.getLocalHost();
			String ipAddress = localHost.getHostAddress();
			socket = new Socket(ipAddress, port);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			String message;
			while (!stateRoom) {

				message = scan.nextLine();
				output.writeUTF("\t" + message);
				String tmp = input.readUTF();
				System.out.println(tmp);
				

				if (message.toLowerCase().equals("close")) {
					closeRoom();
				}
			}

			socket.close();
			input.close();
			output.close();

		} catch (Exception error) {
			System.out.println(error);
		}

	}

	private void closeRoom() {
		this.stateRoom = true;
	}

}