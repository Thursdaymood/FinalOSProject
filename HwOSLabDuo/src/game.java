import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
 * play1,2 จะมารวมกัน
 * เชื่อข้อมูล wordChallenge 
 * เปลี่ยนhidden /
 * ผลแพ้ชนะ /
 * ใครwin /
 * link หน้าประกาศผล
 * เมื่อroundเปลี่ยนให้เริ่มต้นเกมใหม่ /
 * ส่งผลการกดแป้นพิมพ์
 * แยกplayer1 player2
 * give up เปลี่ยนเป็นอย่างอื่น
 * life round /
 * arraylistเก็บcommand /
 */

public class game extends JFrame implements ActionListener {

	// store wordChallenge export form server
	private ArrayList<String> wordChallenge = new ArrayList<String>();
	private String word; // แก้การเข้าถึงกำหนดindex
	private int roomId, lifeOfPlayer1 = 5, lifeOfPlayer2 = 5, scorePlayer1 = 0, scorePlayer2 = 0, count = 0;
	private JLabel hiddeWordsLabel, play1, play2, roundLabel, displayScore1, displayScore2, turnLabel, life1, life2,
			resultMessage,space;

	private int round = 0, turn = 1;
	private final int WIDTH = 800;
	private final int HEIGHT = 500;
	static int margin = 30;
	static final int size_heart = 30;
	static Color TEXT_COLOR = Color.WHITE;
	static Color BACKGROUND = Color.GRAY;
	static Color PRIMARY_COLOR = Color.WHITE;
	static Color SECONDARY_COLOR = Color.decode("#FCA311");
	private Font customFont;
	private ArrayList<String> listInput = new ArrayList<String>();
	private JButton[] letterButtons;
	private String answer = "";
	private int who,winner;
	private Boolean receiveInput = true;
	
	DataOutputStream output;


	public static String hiddeWords(String word) {
		String hiddenWord = "";
		for (int i = 0; i < word.length(); i++) {
			if (!(word.charAt(i) == ' ')) {
				hiddenWord += "_ ";
			} else {
				hiddenWord += " ";
			}
		}

		return hiddenWord;

	}

	private Font createFont(String path) {
		Font customFont = null;

		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}

		return customFont;

	}

	private void displayLife1(int life) {
		// int pos_x = margin;
		// for (int i = 0; i < life; i++) {
		// ImageIcon image = new ImageIcon("HwOSLabDuo/resc/heart.png");
		// Image smallImage = image.getImage().getScaledInstance(size_heart, size_heart,
		// Image.SCALE_SMOOTH);
		// ImageIcon smallIcon = new ImageIcon(smallImage);
		// JLabel heart = new JLabel(smallIcon);
		// heart.setBounds(pos_x, 65 , size_heart, size_heart);
		// getContentPane().add(heart);
		// for (int j = 0; j < 3-life; j++) {
		// getContentPane().remove(heart);
		// }
		// pos_x += size_heart+10;
		// }

		life1 = new JLabel("Life: " + life);
		life1.setFont(customFont.deriveFont(20f));
		life1.setForeground(TEXT_COLOR);
		life1.setBounds(margin, 65, 120, 30);
		getContentPane().add(life1);

	}

	private void displayScore1(int score) {

		if (score != 0) {
			getContentPane().remove(displayScore1);
		}

		displayScore1 = new JLabel("SCORE: " + score);
		displayScore1.setFont(customFont.deriveFont(20f));
		displayScore1.setBounds(margin, 100, 120, 30);
		displayScore1.setForeground(TEXT_COLOR);
		getContentPane().add(displayScore1);

		revalidate();
		repaint();
	}

	private void displayLife2(int life) {

		life2 = new JLabel("Life: " + life);
		life2.setFont(customFont.deriveFont(20f));
		life2.setForeground(TEXT_COLOR);
		life2.setBounds(margin, 65 + ((HEIGHT / 2 - 30)), 120, 30);
		getContentPane().add(life2);

	}

	private void displayScore2(int score) {

		if (score != 0) {
			getContentPane().remove(displayScore2);
		}

		displayScore2 = new JLabel("SCORE: " + score);
		displayScore2.setFont(customFont.deriveFont(20f));
		displayScore2.setBounds(margin, HEIGHT / 2 + 70, 120, 30);
		displayScore2.setForeground(TEXT_COLOR);
		getContentPane().add(displayScore2);

		revalidate();
		repaint();
	}

	private void displayTurn() {

		if (turnLabel != null) {
			getContentPane().remove(turnLabel);
		}
		turnLabel = new JLabel("TURN: PLAYER " + turn);
		turnLabel.setForeground(TEXT_COLOR);
		turnLabel.setBounds(WIDTH / 2 - 70, 30, 200, 30);
		turnLabel.setFont(customFont.deriveFont(20f));
		getContentPane().add(turnLabel);

		revalidate();
		repaint();
	}

	private void displayRound() {

		if (turnLabel != null) {
			getContentPane().remove(roundLabel);
		}
		roundLabel = new JLabel("Round: " + (round + 1) + " / 5");
		roundLabel.setForeground(TEXT_COLOR);
		roundLabel.setBounds(WIDTH - 150, 30, 150, 30);
		roundLabel.setFont(customFont.deriveFont(20f));
		getContentPane().add(roundLabel);
		// up date roundLabel
		revalidate();
		repaint();
	}

	public void createResultDialog(int player) {
		JDialog resultDialog = new JDialog();
		resultDialog.setSize(300, 200);
		resultDialog.getContentPane().setBackground(BACKGROUND);
		resultDialog.setForeground(TEXT_COLOR);
		resultDialog.setLocationRelativeTo(null);
		resultDialog.setResizable(false);
		resultDialog.setLayout(new GridLayout(4, 1));
		resultDialog.setVisible(true);

		resultMessage = new JLabel("PLAYER "+ player +" WIN");
		if (player == 3) {
			resultMessage.setText("Due");
		}
		resultMessage.setFont(customFont.deriveFont(20f));
		resultMessage.setForeground(TEXT_COLOR);
		resultMessage.setHorizontalAlignment(SwingConstants.CENTER);

		space = new JLabel();
        space.setForeground(Color.WHITE);
        space.setHorizontalAlignment(SwingConstants.CENTER);

		JButton resultButton = new JButton("EXIT");
		resultButton.setForeground(BACKGROUND);
        resultButton.setBackground(PRIMARY_COLOR);
        resultButton.addActionListener(this);

		resultDialog.add(space);
		resultDialog.add(resultMessage);
		resultDialog.add(space);
		resultDialog.add(resultButton);

	}

	public void updateHiddenwords(String letter) {
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == letter.charAt(0)) {
				getContentPane().remove(hiddeWordsLabel);

				hiddeWordsLabel = new JLabel(hiddeWords(word));
				hiddeWordsLabel.setFont(customFont.deriveFont(60f));
				hiddeWordsLabel.setForeground(TEXT_COLOR);
				hiddeWordsLabel.setBounds(WIDTH / 2 - hiddeWordsLabel.getWidth() / 2, (HEIGHT - 160) / 2, WIDTH - 190,
						100);
			}

		}
	}

	public game(ArrayList<String> tmp, DataOutputStream output,int whoPlayer) {
		this.wordChallenge = tmp;
		this.who = whoPlayer;
		this.output = output;
		
		for(int i = 0 ; i < wordChallenge.size() ; i++){
			System.out.println(wordChallenge.get(i));
		}

		// init vars
		letterButtons = new JButton[26];
		customFont = createFont("resc/Mali-SemiBoldItalic.ttf"); //"HwOSLabDuo/resc/Mali-SemiBoldItalic.ttf"
		word = wordChallenge.get(round);

		//start game
		createroom();
		addGuiComponents();

	}

	private void resetGame() {

		// load new challenge
		word = wordChallenge.get(round);

		// update hiddenWord
		String hiddenWord = hiddeWords(word);
		hiddeWordsLabel.setText(hiddenWord);

		// enable all buttons again
		for (int i = 0; i < letterButtons.length; i++) {
			letterButtons[i].setEnabled(true);
			letterButtons[i].setBackground(PRIMARY_COLOR);
			letterButtons[i].setBackground(BACKGROUND);
			letterButtons[i].setFont(customFont.deriveFont(15f));
			letterButtons[i].setForeground(BACKGROUND);
			letterButtons[i].setOpaque(true);
		}

		// update life
		lifeOfPlayer1 = 5;
		lifeOfPlayer2 = 5;
		life1.setText("Life: " + lifeOfPlayer1);
		life2.setText("Life: " + lifeOfPlayer2);

	}

	public void createroom() {
		setTitle("Hangman Game Room" + roomId);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		getContentPane().setBackground(BACKGROUND);
	}

	private void addGuiComponents() {

		/****************** PLAYER ******************/
		play1 = new JLabel("PLAYER 1");
		play1.setBounds(margin, 30, 120, 30);
		play1.setFont(customFont.deriveFont(25f));
		play1.setForeground(TEXT_COLOR);
		displayLife1(lifeOfPlayer1);
		displayScore1(scorePlayer1);

		play2 = new JLabel("PLAYER 2");
		play2.setBounds(margin, HEIGHT / 2, 120, 30);
		play2.setFont(customFont.deriveFont(25f));
		play2.setForeground(TEXT_COLOR);
		displayLife2(lifeOfPlayer2);
		displayScore2(scorePlayer2);

		/****************** DISKPLAY GAME ******************/
		displayRound();
		displayTurn();

		// hidden word
		hiddeWordsLabel = new JLabel(hiddeWords(word));
		hiddeWordsLabel.setFont(customFont.deriveFont(60f));
		hiddeWordsLabel.setForeground(TEXT_COLOR);
		hiddeWordsLabel.setBounds(WIDTH / 2 - hiddeWordsLabel.getWidth() / 2, (HEIGHT - 160) / 2, WIDTH - 190, 100);

		// layout
		GridLayout gridLayout = new GridLayout(3, 9);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(190, HEIGHT - 190, WIDTH - 197, 150);
		buttonPanel.setLayout(gridLayout);
		buttonPanel.setBorder(null);

		// create the letter buttons
		for (char c = 'A'; c <= 'Z'; c++) {
			JButton button = new JButton(Character.toString(c));
			button.setBackground(BACKGROUND);
			button.setFont(customFont.deriveFont(15f));
			button.setForeground(BACKGROUND);
			button.setOpaque(true);
			button.addActionListener(this);

			// using ASCII values to caluclate the current index
			int currentIndex = c - 'A';

			letterButtons[currentIndex] = button;
			buttonPanel.add(letterButtons[currentIndex]);
		}

		// set turn
		if (turn == 1) {
			turn = 2;

		} else {
			turn = 1;
		}
		displayTurn();
		listInput.clear();

		// Give up button
		JButton giveUpButton = new JButton(" ");
		giveUpButton.setFont(customFont.deriveFont(13f));
		giveUpButton.setForeground(BACKGROUND);
		giveUpButton.setBackground(BACKGROUND);
		giveUpButton.setOpaque(true);
		// giveUpButton.addActionListener(this);
		buttonPanel.add(giveUpButton);

		getContentPane().add(play1);
		getContentPane().add(play2);
		getContentPane().add(turnLabel);
		getContentPane().add(buttonPanel);
		getContentPane().add(hiddeWordsLabel);

	}

	// set the ArrayList that store the vocabulary
	public void addWords(ArrayList<String> user) {
		this.wordChallenge = user;
	}

	public void deleteLife1(int player) {
		if (player == 1) {
			this.lifeOfPlayer1 -= 1;
		}
		if (player == 2) {
			this.lifeOfPlayer1 -= 1;
		}
	}

	public int getScorePlayer(int player) {
		if (player == 1) {
			return this.scorePlayer1;
		} else {
			return this.scorePlayer2;
		}
	}
	public int getWinner() {
		return this.winner;
	}

	public int getLifePlayer(int player) {
		if (player == 1) {
			return this.lifeOfPlayer1;
		} else {
			return this.lifeOfPlayer2;
		}
	}
	public int getRound() {
		return this.round;
	}

	public void setScorePlayer(int player, int num) {
		if (player == 1) {
			this.scorePlayer1 = num;
		}
		if (player == 2) {
			this.scorePlayer2 = num;
		}
	}

	public void setLifeOfPlayer(int player, int num) {
		if (player == 1) {
			this.lifeOfPlayer1 = num;
		}
		if (player == 2) {
			this.lifeOfPlayer2 = num;
		}
	}

	public void addKeyBoardPlay(String keyboardInput) {
		ActionEvent input = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, keyboardInput);
		actionPerformed(input);

	}

	public ArrayList<String> getKeyBoard() {
		return this.listInput;
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		// disable button
		JButton button = (JButton) e.getSource();
		button.setEnabled(false);

		if (command.equals("EXIT")) {
			System.out.println("Stop");
			System.exit(0);

		} else {
			if (receiveInput) {
				listInput.add(command);
			System.out.println("Input: " + listInput);
			if (word.contains(command.toLowerCase())) {
				button.setBackground(Color.cyan);

				if (turn == 1) {
					scorePlayer1 += 1;
					displayScore1(scorePlayer1);
				} else if (turn == 2) {
					scorePlayer2 += 1;
					displayScore2(scorePlayer2);
				}

				// store input and change hiddenLabel,so update the hidden text.
				char[] hiddenWord = hiddeWordsLabel.getText().toCharArray();
				System.out.println(hiddenWord);

				for (int i = 0; i < word.length(); i++) {
					// update _ to correct letter
					if (word.charAt(i) == command.toLowerCase().charAt(0)) {

						// นับletterที่ถูก
						++count;
						if (i != 0) {
							hiddenWord[2 * i] = command.charAt(0);
						} else {
							hiddenWord[i] = command.charAt(0);
						}

					}

				}
				// update hiddenspace
				hiddeWordsLabel.setText(String.valueOf(hiddenWord));
				System.out.println(hiddenWord);

			} else {
				if (turn == 1) {
					lifeOfPlayer1 -= 1;
					life1.setText("Life: " + lifeOfPlayer1);
				} else if (turn == 2) {
					lifeOfPlayer2 -= 1;
					life2.setText("Life: " + lifeOfPlayer2);
				}
				button.setBackground(Color.PINK);
			}
			}
			
		}
		// finish round check who win.
		System.out.println("round: " + round);
		if (count == word.length() || lifeOfPlayer1 <1 || lifeOfPlayer2 <1 ) {
			if (round < 4) {
				++round;
				count = 0;
				displayRound();
				System.out.println("Turn " + round);
				System.out.println("FINISH");
				resetGame();
			} else {
				receiveInput = false;
				if (scorePlayer1 > scorePlayer2) {
					winner = 1 ;
					System.out.println("Player1");
					try {
						output.writeUTF("Player1");
					} catch (IOException e1) {
						System.out.println(e1);
					}
				} else if (scorePlayer1 < scorePlayer2) {
					winner = 2;
					System.out.println("Player2");
					try {
						output.writeUTF("Player2");
					} catch (IOException e1) {
						System.out.println(e1);
					}
				}
				else{
					winner = 3 ;
					System.out.println("Due");
				}
				System.out.println("End Game..........");
				createResultDialog(winner);
			}

		}


		// set turn
		if (turn == 1) {
			turn = 2;

		} else {
			turn = 1;
		}

		displayTurn();
		listInput.clear();

	}

	public void deleteLife(int player) {
		if (player == 1) {
			this.lifeOfPlayer1 -= 1;
		}
		if (player == 2) {
			this.lifeOfPlayer1 -= 1;
		}
	}

	public boolean checkLetter(boolean answer) {
		return answer;
	}

	public String getLetter() {
		return this.answer;
	}

	public void setAnswer(String letter) {
		this.answer = letter;
	}

	public boolean changeTurn() {
		return false;
	}

	public void setVisible() {
		this.setVisible(true);
	}

	public static void main(String[] args) {
		// for test
		ArrayList<String> tmp = new ArrayList<String>();
		tmp.add("ab");
		tmp.add("ac");
		tmp.add("abc");
		tmp.add("acct");
		tmp.add("acr");

		// new game(tmp,1).setVisible(true);
	}

}