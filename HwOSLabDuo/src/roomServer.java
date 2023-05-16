
// Network
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class roomServer {
	// variable in game
	static int score;
	static int life = 3;
	static ArrayList<String> word_random = new ArrayList<String>(5);

	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	static final int margin = 15;
	static final int size_heart = 30;

	// Picture
	private ImageIcon imageIcon = new ImageIcon("resc//idea.png");
	private ImageIcon bg = new ImageIcon("resc//bgPlay.jpg");

	// Structure
	private JFrame frame;
	private JLabel labelMain;
	private roomServer server;

	// Constructor
	roomServer() {

		// Create thread to take responsibility about roomServer
		ServerRunnable task = new ServerRunnable(5001);
		Thread threadServer = new Thread(task);
		threadServer.start();
		GUIPart();
	}

	public static void main(String[] args) {
		roomServer server = new roomServer();
	}

	private void GUIPart() {

		labelMain = new JLabel(bg);
		labelMain.setSize(WIDTH, HEIGHT);
		frame = new JFrame();
		frame.add(labelMain); // add background to frame
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		// Icon title
		frame.setIconImage(imageIcon.getImage());

		// line
		labelMain.add(new Line1());
		labelMain.add(new Line2());


		// player1
		JLabel play1 = new JLabel("PLAY1");
		play1.setBounds(15, 30, 100, 30);
		play1.setFont(new Font("Arial", Font.BOLD, 18));
		play1.setForeground(Color.WHITE);

//		// heart
//		int pos_x = margin;
//		for (int i = 0; i < 3; i++) {
//			ImageIcon image = new ImageIcon("resc/heart.png");
//			Image smallImage = image.getImage().getScaledInstance(size_heart, size_heart, Image.SCALE_SMOOTH);
//			ImageIcon smallIcon = new ImageIcon(smallImage);
//			JLabel heart = new JLabel(smallIcon);
//			heart.setBounds(pos_x, 65, size_heart, size_heart);
//			panel.add(heart);
//
//			pos_x += size_heart + 10;
//		}
//		// score
//		JLabel score1 = new JLabel("Score:");
//		score1.setForeground(Color.WHITE);
//		score1.setBounds(15, 100, 1000, 30);
//		score1.setFont(new Font("Arial", Font.BOLD, 18));
//
//		panel.setLayout(null);
//		panel.add(play1);
//		panel.add(score1);
//
//		// turn
//		JLabel turn = new JLabel("TURN");
//		turn.setForeground(Color.WHITE);
//		turn.setBounds(WIDTH - 150, 30, 100, 30);
//		turn.setFont(new Font("Arial", Font.BOLD, 18));
//		panel.add(turn);
//
//		labelMain.add(panel);
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

	private Font getExternalFont20(String path) {
		Font customFont = null;
		
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(20f); // change size
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}

		return customFont;

	}

	// The end of roomServer class
}

class Line1 extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D line = (Graphics2D) g;

        // Set the color and thickness of the line
        line.setColor(Color.white);
        line.setStroke(new BasicStroke(3));
        
        // Create
        line.drawLine( 200 ,350, 900, 350);
    }

}
class Line2 extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D line = (Graphics2D) g;

        // Set the color and thickness of the line
        line.setColor(Color.white);
        line.setStroke(new BasicStroke(3));
        
        // Create
        line.drawLine(200, 0, 200, 700);
    }

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
		
		// Network
		private static List<Socket> connectedClients = new ArrayList<>();


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

				// 2) Waiting for clients connecting
				while(connectedClients.size() != 2){
					socket = server.accept();
					System.out.println("Player "+connectedClients.size()+" is in the room");
					connectedClients.add(socket);
				}
				System.out.println("Players are ready to play");
				
				
				
//				
//				socket = server.accept(); // connect
//				System.out.println("\tOpen the room");
//				input = new DataInputStream(socket.getInputStream()); // for receiving
//				output = new DataOutputStream(socket.getOutputStream()); // for sending

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

