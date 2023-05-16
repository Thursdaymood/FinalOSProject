
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class mainArea {

	public static void main(String[] args) throws FontFormatException, IOException {
		
		System.out.println("Start game ...");
		printLine(20);
		introFrame frameStart = new introFrame(1);

	}
	private static void printLine(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("-");
		}
		System.out.println();

	}
}

class introFrame {

	// Picture
	private ImageIcon imageIcon = new ImageIcon("resc//idea.png");
	private ImageIcon bg = new ImageIcon("resc//pixelSky.gif");
	
	// Structure
	private JFrame frame;
	private JLabel labelMain;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 250;
	private client player1;
	private client player2;
	private int num = 0;
	
	// Constructor
	public introFrame(int numUser) {
		this.num = numUser;
		// Default setup
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

		startPage();
	}

	private void startPage() {
		// buttons
		JButton btn1 = new JButton("Room 1");
		JButton btn2 = new JButton("Room 2");
		JButton btn3 = new JButton("Room 3");
		JButton btn4 = new JButton("Room 4");

		// Position
		btn1.setBounds((WIDTH / 2) - 200 - (10 * 2), (HEIGHT / 2) - 30, 100, 50);
		btn2.setBounds((WIDTH / 2) - 100 - 10, (HEIGHT / 2) - 30, 100, 50);
		btn3.setBounds((WIDTH / 2) + (10 * 1), (HEIGHT / 2) - 30, 100, 50);
		btn4.setBounds((WIDTH / 2) + 100 + (10 * 2), (HEIGHT / 2) - 30, 100, 50);

		// Set focusable
		btn1.setFocusable(false);
		btn2.setFocusable(false);
		btn3.setFocusable(false);
		btn4.setFocusable(false);

		// Create buttons
		labelMain.add(btn1);
		labelMain.add(btn2);
		labelMain.add(btn3);
		labelMain.add(btn4);

		// function btn
		btn1.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("User enter room1");
				if(num == 1) {
					createRoom(5011);
					player1 = new client(1, 5011);
				}
				if(num ==2){
					joinRoom(5011);
					player2 = new client(2, 5011);
				}

				frame.setVisible(false);
			}

		});

		btn2.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("User enter room2");
				if(num == 1) {
					createRoom(5022);
					player1 = new client(1, 5022);
				}
				if(num ==2) {
					joinRoom(5022);
					player2 = new client(2, 5022);
				}

				frame.setVisible(false);
			}

		});

		btn3.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("User enter room3");
				if(num == 1){
					createRoom(5033);
					player1 = new client(1, 5033);
					
				}if(num ==2) {
					joinRoom(5033);
					player2 = new client(2, 5033);
				}

				frame.setVisible(false);
			}

		});

		btn4.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("User enter room4");
				if(num == 1){
					createRoom(5044);
					player1 = new client(1,5044);
				}if(num == 2){
					joinRoom(5044);
					player2 = new client(2, 5044);
				}
				frame.setVisible(false);
			}
		});

		// ----------------------------------------------
		// Game's name
		JLabel name = new JLabel("// NAME //");
		name.setOpaque(false);
		name.setFont(getExternalFont20("resc/Raleway-Bold.ttf"));
		name.setForeground(Color.black); // font color
		name.setBounds((WIDTH / 2) - 50, (HEIGHT / 10) - 5, WIDTH, 50);
		labelMain.add(name);
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
	
	// Create room method
	private static void createRoom(int port){
		printLine(20);
		System.out.println("\tCreate room");
		roomServer room = new roomServer(port);

	}

	// Join room
	private static void joinRoom(int port) {
		printLine(20);
		System.out.println("\tJoin room ...");
		client player2 = new client(2,port);

	}
	private static void printLine(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("-");
		}
		System.out.println();

	}
	
}