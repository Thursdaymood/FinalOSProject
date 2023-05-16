
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class mainArea {

	public static void main(String[] args) throws FontFormatException, IOException {
		
		System.out.println("Start game ...");
		printLine(20);
		introFrame frameStart = new introFrame();

	}
	private static void printLine(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("-");
		}
		System.out.println();

	}
}

class introFrame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 250;
	// Picture
	private ImageIcon imageIcon = new ImageIcon("resc//idea.png");
	private ImageIcon bg = new ImageIcon("resc//pixelSky.gif");
	
	// Structure
	private JFrame frame;
	private JLabel labelMain;	
	
	// Constructor
	introFrame() {
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
				createRoom();
			}

		});

		btn2.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello 2");
			}

		});

		btn3.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello 3");
			}

		});

		btn4.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello 4");
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
	private static void createRoom() {
		printLine(20);
		System.out.println("\tCreate room");
		roomServer room = new roomServer();
	}

	private static void joinRoom() {
		printLine(20);
		System.out.println("\tJoin room");
		client user = new client();
	}

	private static void printLine(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("-");
		}
		System.out.println();

	}
	
	

}

