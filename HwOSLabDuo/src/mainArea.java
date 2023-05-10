
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.print.attribute.AttributeSetUtilities;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class mainArea {

	private static int[][] ports = { { 5001, 5002 }, { 5003, 5004 }, { 5005, 5006 }, { 5007, 5008 } }; // Just 4 rooms

	public static void main(String[] args) throws FontFormatException, IOException {

		frameGame frameStart = new frameGame();
		
		// Button
		JButton btn1 = new JButton("Room1");
		btn1.setBounds(0, 0, 100, 50);
		frameStart.add(btn1);
		
//		//Label
//		JLabel gameName = new JLabel("Hangman HangChai");
//		//Font fontBold = Font.createFont(Font.TRUETYPE_FONT, new File("resc//Raleway-Bold.tff"));
//		gameName.setBounds(300, 125, 200, 50);
//		//gameName.setFont(fontBold);
//		gameName.setForeground(Color.green);
//		gameName.setBackground(Color.WHITE);
//		gameName.setOpaque(true);
//		frameStart.add(gameName);
		
		frameStart.setVisible(true); // Don't move this line
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


class frameGame extends JFrame{
	// Constructor //The default setup
	frameGame() {
		// Set size and position
		this.setResizable(false);
		this.setSize(800, 500);
		this.setLocationRelativeTo(null); // set the location to the center of the screen
		this.setLayout(new BorderLayout());

		// Set visibility
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setTitle("Hangman HangChai");

		// Icon
		ImageIcon imageIcon = new ImageIcon("resc//idea.png");
		this.setIconImage(imageIcon.getImage());
		// BG
		this.getContentPane().setBackground(new Color(0, 0, 0));
	}
}
