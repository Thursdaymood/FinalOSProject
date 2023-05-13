
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

public class mainArea {

	private static int[][] ports = { { 5001, 5002 }, { 5003, 5004 }, { 5005, 5006 }, { 5007, 5008 } }; // Just 4 rooms

	public static void main(String[] args) throws FontFormatException, IOException {

		frameGame frameStart = new frameGame("start");

	}

	// Create room method
	private static void createRoom(int port) {
		printLine(20);
		System.out.println("\tCreate room");
		roomServer room = new roomServer(port);
	}

	private static void joinRoom(int port, String ip) {
		printLine(20);
		System.out.println("\tJoin room");
		client user = new client(port, ip);
	}

	private static void printLine(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("-");
		}
		System.out.println();

	}

}

class frameGame extends JFrame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	static final int margin = 15;
	static final int size_heart = 30;
	static Color white = Color.WHITE;

	// Constructor
	// The default setup
	frameGame(String frame) {
		// Set size and position
		this.setResizable(false);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null); // set the location to the center of the screen
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		// Set visibility
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setTitle("Hangman HangChai");

		// Icon
		ImageIcon imageIcon = new ImageIcon("resc//idea.png");
		this.setIconImage(imageIcon.getImage());
		// BG
		this.getContentPane().setBackground(new Color(0, 0, 0));

		// StartFrame
		if (frame.toLowerCase().equals("start")) {
			startFrame();
		}
		if (frame.toLowerCase().equals("game")) {

		}
		this.setVisible(true); // Don't move this line
	}

	private void startFrame() {

		JPanel panelBtn = new JPanel(); // for buttons
		panelBtn.setPreferredSize(new Dimension(800,400));
		panelBtn.setBackground(Color.black);
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		// Button1
		JButton btn1 = new JButton("Room1");
		panelBtn.add(btn1);

		// Even Click -> btn1
		btn1.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello 1");
			}

		});

		// Button2
		JButton btn2 = new JButton("Room2");
		panelBtn.add(btn2);

		// Even Click -> btn1
		btn2.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello 2");
			}

		});

		// Button3
		JButton btn3 = new JButton("Room3");
		panelBtn.add(btn3);

		// Even Click -> btn1
		btn3.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello 3");
			}

		});

		// Button3
		JButton btn4 = new JButton("Room4");
		panelBtn.add(btn4);

		// Even Click -> btn1
		btn4.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello 4");
			}
		});
		
		// Space
		JPanel space2 = new JPanel();
		space2.setBackground(Color.black);
		space2.setPreferredSize(new Dimension(800,50));
		JPanel space1 = new JPanel();
		space1.setBackground(Color.black);
		space1.setPreferredSize(new Dimension(800,20));

		
		JPanel panelHead = new JPanel(); // for texts
		//panelHead.setOpaque(false);

		// Game's name
		JLabel name = new JLabel("// NAME //");
		name.setOpaque(false);
		name.setFont(getExternalFont("resc/Raleway-Bold.ttf"));
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		name.setForeground(white);
		panelHead.add(name);
		
		// Don't move these lines below
		this.add(space1);
		this.add(name);
		this.add(space2);
		this.add(panelBtn); 
		
	}
	
	private Font getExternalFont(String path) {
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(20f); // change size
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        
        return customFont;
        
	}

	private void playFrame() {
		// line
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D line = (Graphics2D) g;

				line.setColor(white);
				line.drawLine(200, getHeight() / 2 + 100, 900, getHeight() / 2 + 100);

				line.setColor(white);
				line.drawLine(200, 0, 200, 700);

			}
		};

		panel.setBackground(Color.BLACK);
		this.add(panel);
		// PLAY1
		JLabel play1 = new JLabel("PLAY1");
		play1.setBounds(15, 30, 100, 30);
		play1.setFont(new Font("Arial", Font.BOLD, 18));
		play1.setForeground(Color.WHITE);

		// heart
		int pos_x = margin;
		for (int i = 0; i < 3; i++) {
			ImageIcon image = new ImageIcon("resc/heart.png");
			Image smallImage = image.getImage().getScaledInstance(size_heart, size_heart, Image.SCALE_SMOOTH);
			ImageIcon smallIcon = new ImageIcon(smallImage);
			JLabel heart = new JLabel(smallIcon);
			heart.setBounds(pos_x, 65, size_heart, size_heart);
			panel.add(heart);

			pos_x += size_heart + 10;
		}

		// score
		JLabel score1 = new JLabel("Score:");
		score1.setForeground(Color.WHITE);
		score1.setBounds(15, 100, 1000, 30);
		score1.setFont(new Font("Arial", Font.BOLD, 18));

		panel.setLayout(null);
		panel.add(play1);
		panel.add(score1);

		// turn
		JLabel turn = new JLabel("TURN");
		turn.setForeground(Color.WHITE);
		turn.setBounds(WIDTH - 150, 30, 100, 30);
		turn.setFont(new Font("Arial", Font.BOLD, 18));
		panel.add(turn);

	}
}
