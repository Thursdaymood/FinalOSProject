import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * play1,2 จะมารวมกัน
 * เชื่อข้อมูล wordChallenge
 * ลดหัวใจ
 * เปลี่ยนhidden
 * ผลแพ้ชนะ
 * ใครwin
 * เปลี่ยนround
 */

public class game extends JFrame implements ActionListener {

    //store wordChallenge export form server
    private ArrayList<String> wordChallenge = new ArrayList<String>();
    private roomServer server;
    private String word ; //แก้การเข้าถึงกำหนดindex
    private int host,roomId,
                lifeOfPlayer1 = 3 , 
                lifeOfPlayer2 = 3 ,
                scorePlayer1 = 0,
                scorePlayer2 = 0,
                count=0; 
    private JLabel hiddeWordsLabel,play1,play2,roundLabel,displayScore1,displayScore2,turnLabel;

    private int round = 1, turn = 1;
    private final int WIDTH = 800;
    private final int HEIGHT = 500;
    static int margin = 30;
    static final int size_heart = 30;
    static Color TEXT_COLOR = Color.WHITE;
    static Color BACKGROUND = Color.GRAY;
    static Color PRIMARY_COLOR = Color.WHITE;
    static Color SECONDARY_COLOR = Color.decode("#FCA311");
    private Font customFont;
    private JButton[] letterButtons;
    private String answer = "";

    public static String hiddeWords(String word){
        String hiddenWord = "";
        for(int i = 0; i < word.length(); i++){
            if(!(word.charAt(i) == ' ')){
                hiddenWord += "_ ";
            }else{
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

    private void displayHeart1(int life){
        int pos_x = margin;
        
        for (int i = 0; i < life; i++) {
            ImageIcon image = new ImageIcon("HwOSLabDuo/resc/heart.png");
            Image smallImage = image.getImage().getScaledInstance(size_heart, size_heart, Image.SCALE_SMOOTH);
            ImageIcon smallIcon = new ImageIcon(smallImage);
            JLabel heart = new JLabel(smallIcon);
            heart.setBounds(pos_x, 65 , size_heart, size_heart);
            getContentPane().add(heart);
            for (int j = 0; j < 3-life; j++) {
                getContentPane().remove(heart);
            }
            

            pos_x += size_heart+10;
         }
        revalidate();
        repaint();

    }

    private void displayScore1(int score){

        if (score != 0) {
            getContentPane().remove(displayScore1);
        }
        
        displayScore1 = new JLabel("SCORE: "+ score);
        displayScore1.setFont(customFont.deriveFont(20f));
        displayScore1.setBounds(margin, 100 , 120, 30);
        displayScore1.setForeground(TEXT_COLOR);
        getContentPane().add(displayScore1);    
        
        revalidate();
        repaint();
    }
    private void displayHeart2(int life){
        int pos_x = margin;
        
        for (int i = 0; i < life; i++) {
            ImageIcon image = new ImageIcon("HwOSLabDuo/resc/heart.png");
            Image smallImage = image.getImage().getScaledInstance(size_heart, size_heart, Image.SCALE_SMOOTH);
            ImageIcon smallIcon = new ImageIcon(smallImage);
            JLabel heart = new JLabel(smallIcon);
            heart.setBounds(pos_x, 65 + ((HEIGHT/2-30)), size_heart, size_heart);
            getContentPane().add(heart);
            for (int j = 0; j < 3-life; j++) {
                getContentPane().remove(heart);
            }
            

            pos_x += size_heart+10;
         }
        revalidate();
        repaint();

    }

    private void displayScore2(int score){

        if (score != 0) {
            getContentPane().remove(displayScore2);
        }
        
        displayScore2 = new JLabel("SCORE: "+ score);
        displayScore2.setFont(customFont.deriveFont(20f));
        displayScore2.setBounds(margin, HEIGHT/2+70, 120, 30);
        displayScore2.setForeground(TEXT_COLOR);
        getContentPane().add(displayScore2);    
        
        revalidate();
        repaint();
    }
    private void displayTurn(){

        if(turnLabel != null) {
            getContentPane().remove(turnLabel);
        }
        turnLabel = new JLabel("TURN: PLAYER "+ turn);
        turnLabel.setForeground(TEXT_COLOR);
        turnLabel.setBounds(WIDTH/2-70, 30, 200, 30);
        turnLabel.setFont(customFont.deriveFont(20f));
        getContentPane().add(turnLabel);    
        
        revalidate();
        repaint();
    }

    private void displayRound(){

        if(turnLabel != null) {
            getContentPane().remove(roundLabel);
        }
        roundLabel = new JLabel("Round: "+ round + " / 5");
        roundLabel.setForeground(TEXT_COLOR);
        roundLabel.setBounds(WIDTH - 150, 30, 150, 30);
        roundLabel.setFont(customFont.deriveFont(20f));
        getContentPane().add(roundLabel);    
        
        revalidate();
        repaint();
    }

    public void updateHiddenwords(String letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) ==  letter.charAt(0)) {
                getContentPane().remove(hiddeWordsLabel);

            hiddeWordsLabel = new JLabel(hiddeWords(word));
            hiddeWordsLabel.setFont(customFont.deriveFont(60f));
            hiddeWordsLabel.setForeground(TEXT_COLOR);
            hiddeWordsLabel.setBounds(WIDTH/2-hiddeWordsLabel.getWidth()/2,(HEIGHT-160)/2,WIDTH-190,100);
            }
            
        }
        
    }
    public void createroom(){
        setTitle("Hangman Game Room"+roomId);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND);
    }

    public game(){

        // init vars
        letterButtons = new JButton[26];
        customFont = createFont("HwOSLabDuo/resc/Raleway-SemiBold.ttf");
        word = wordChallenge.get(round);

        //wordChallenge = server.word_random();
        //สร้างloop
        createroom();
        addGuiComponents();
        
    }
    private void addGuiComponents(){

        /******************      PLAYER       ******************/
        play1 = new JLabel("PLAYER 1");
        play1.setBounds(margin, 30, 120, 30);
        play1.setFont(customFont.deriveFont(25f));
        play1.setForeground(TEXT_COLOR);
        displayHeart1(lifeOfPlayer1);
        displayScore1(scorePlayer1);

        play2 = new JLabel("PLAYER 2");
        play2.setBounds(margin, HEIGHT/2, 120, 30);
        play2.setFont(customFont.deriveFont(25f));
        play2.setForeground(TEXT_COLOR);
        displayHeart2(lifeOfPlayer2);
        displayScore2(scorePlayer2);



        /******************      DISPLAY GAME       ******************/
        displayRound();
        displayTurn();

        /******************      DISPLAY GAME       ******************/
        roundLabel = new JLabel("Round: "+ (round+1) + " / 5");
        roundLabel.setForeground(TEXT_COLOR);
        roundLabel.setBounds(WIDTH - 150, 30, 150, 30);
        roundLabel.setFont(customFont.deriveFont(20f));

        // turnLabel = new JLabel("TURN: PLAYER "+ turn);
        // turnLabel.setForeground(TEXT_COLOR);
        // turnLabel.setBounds(WIDTH/2-70, 30, 200, 30);
        // turnLabel.setFont(customFont.deriveFont(20f));
        displayTurn();


        //hidden word
        hiddeWordsLabel = new JLabel(hiddeWords(word));
        hiddeWordsLabel.setFont(customFont.deriveFont(60f));
        hiddeWordsLabel.setForeground(TEXT_COLOR);
        hiddeWordsLabel.setBounds(WIDTH/2-hiddeWordsLabel.getWidth()/2,(HEIGHT-160)/2,WIDTH-190,100);
 
        //layout
        GridLayout gridLayout = new GridLayout(3,9);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds( 190,HEIGHT-190,WIDTH-197,150);
        buttonPanel.setLayout(gridLayout);
        buttonPanel.setBorder(null);

        // create the letter buttons
        for(char c = 'A'; c <= 'Z'; c++){
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


        // Give up button
        JButton giveUpButton = new JButton("Give Up");
        giveUpButton.setFont(customFont.deriveFont(13f));
        giveUpButton.setForeground(BACKGROUND);
        giveUpButton.setBackground(BACKGROUND);
        giveUpButton.setOpaque(true);
        giveUpButton.addActionListener(this);
        buttonPanel.add(giveUpButton);

        
        getContentPane().add(play1);
        getContentPane().add(play2);
        getContentPane().add(turnLabel);
        getContentPane().add(buttonPanel);
        getContentPane().add(hiddeWordsLabel);

    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand().toLowerCase();

        // disable button
        JButton button = (JButton) e.getSource();
        button.setEnabled(false);
        if (command.equals("give up")) {
            System.out.println("Stop");
            System.out.println();
            System.out.println();
            
        } else {
            if (word.contains(command)) {
                button.setBackground(Color.cyan);
                ++count;
                System.out.print(count);
                if (turn==1) {
                    scorePlayer1 += 1;
                    displayScore1(scorePlayer1);
                } else {
                    scorePlayer2 += 1;
                    displayScore2(scorePlayer1);
                }
                
                
            }
            else{
                lifeOfPlayer1 -= 1;
                displayHeart1(lifeOfPlayer1);
                button.setBackground(Color.PINK);
            }
        }

        if (count == word.length()) {
            ++round;
            count = 0;
            displayRound();
            System.out.println("Turn"+ round);
            System.out.println("FINISH");
        } else {
            
        }
        //set turn
        if (turn ==1) {
            turn = 2;
            
        } else {
            turn = 1;
        }displayTurn();
        

    }
    // public void actionPerformed(ActionEvent e) {
    //     // handle button click event
    //     System.out.println("Button clicked!");
    // }
    
    // set the ArrayList that store the vocabulary
    public void addWords(ArrayList<String> user) {
    	this.wordChallenge = user;
    }
    public void deleteLife(int player){
    	if(player == 1){
    		this.lifeOfPlayer1 -=1;
    	}
    	if(player == 2){
    		this.lifeOfPlayer1 -=1;
    	}
    }
    public void addScore(int player){
    	if(player == 1){
    		this.lifeOfPlayer1 +=1;
    	}if(player == 2){
    		this.lifeOfPlayer2 +=1;
    	}
    	
    };
    public boolean checkLetter(boolean answer){
    	return answer;
    }
    public String getLetter(){
    	return this.answer;
    }
    public void setAnswer(String letter) {
    	this.answer = letter;
    }
    public boolean changeTurn() {
    	return false;
    }
    
    
    
    public static void main(String[] args) {
        // int host = Integer.parseInt(args[0]);
        // String room = args[1];
        new game().setVisible(true);
    }

}