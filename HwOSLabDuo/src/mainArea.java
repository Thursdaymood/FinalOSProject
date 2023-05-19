
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
		introFrame frameStart = new introFrame(1);
		server room1 = new server(1,1);
		server room2 = new server(2,1);
		server room3 = new server(3,1);
		server room4 = new server(4,1);

		Thread thread1 = new Thread(room1);
		Thread thread2 = new Thread(room2);
		Thread thread3 = new Thread(room3);
		Thread thread4 = new Thread(room4);

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		System.out.println("All servers are created...");

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
	
	// -----------------------GUI/first page Button----------------------------
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
				printLine(20);
				System.out.println("User enter room1");
				if(num == 1) {
					player1 = new client(1, 1);
				}
				if(num ==2){
					joinRoom(1);
				}

				frame.setVisible(false);
			}

		});

		btn2.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printLine(20);
				System.out.println("User enter room2");
				if(num == 1) {
					player1 = new client(1, 2);
				}
				if(num ==2) {
					joinRoom(2);
				}

				frame.setVisible(false);
			}

		});

		btn3.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printLine(20);
				System.out.println("User enter room3");
				if(num == 1){
					player1 = new client(1, 3);
					
				}if(num ==2) {
					joinRoom(3);
				}

				frame.setVisible(false);
			}

		});

		btn4.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printLine(20);
				System.out.println("User enter room4");
				frame.setVisible(false);
				if(num == 1){
					System.out.println("\tCreate room ...");
					player1 = new client(1,4);
				}if(num == 2){
					joinRoom(4);
				}
			}
		});


		// Game's name
		JLabel name = new JLabel("// NAME //");
		name.setOpaque(false);
		name.setFont(getExternalFont20("resc/Raleway-Bold.ttf"));
		name.setForeground(Color.black); // font color
		name.setBounds((WIDTH / 2) - 50, (HEIGHT / 10) - 5, WIDTH, 50);
		labelMain.add(name);
	}
	
	// --------------------Font--------------------------
	private Font getExternalFont20(String path) {
		Font customFont = null;
		
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(20f); // change size
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}

		return customFont;

	}
	
	// Join room
	private static void joinRoom(int room) {
		printLine(20);
		System.out.println("\tJoin room ...");
		client player2 = new client(2,room);

	}
	private static void printLine(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("-");
		}
		System.out.println();

	}
	
}