import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class game extends JFrame {

    //store wordChallenge exmport form roomserver
    private String[] wordChallenge = new String[] {"art","acer","action","acid","alot"};

    private roomServer server;
    private String word ; //แก้การเข้าถึงกำหนดindex
    private int lifeOfPlayer1 =3 , lifeOfPlayer2 = 3;
    private JLabel hiddeWordsLabel,play1,play2,round,displayScore;

    private int turn = 1;
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

    private void createHeart(int life, int player){
        int pos_x = margin;
         for (int i = 0; i < life; i++) {
             ImageIcon image = new ImageIcon("HwOSLabDuo/resc/heart.png");
             Image smallImage = image.getImage().getScaledInstance(size_heart, size_heart, Image.SCALE_SMOOTH);
             ImageIcon smallIcon = new ImageIcon(smallImage);
             JLabel heart = new JLabel(smallIcon);
             heart.setBounds(pos_x, 65 + ((player-1)*(HEIGHT/2-30)), size_heart, size_heart);
             getContentPane().add(heart);

             pos_x += size_heart+10;
         }
         System.out.println(margin);

    }

    private void displayScore(int height){
        displayScore = new JLabel("SCORE: 10 ");
        displayScore.setFont(customFont.deriveFont(20f));
        displayScore.setBounds(margin, height , 120, 30);
        displayScore.setForeground(TEXT_COLOR);
        getContentPane().add(displayScore);
    }

    public game(){
        super("Hangman Game (Java Ed.)");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        //setResizable(false);
        getContentPane().setBackground(BACKGROUND);

        // init vars
        server = new roomServer();
        letterButtons = new JButton[26];
        customFont = createFont("HwOSLabDuo/resc/Raleway-SemiBold.ttf");
        word = wordChallenge[turn];
        //wordChallenge = server.word_random();

        // ImageIcon imageIcon = new ImageIcon("HwOSLabDuo/resc/bgPlay.jpg");
        // Image image = imageIcon.getImage();
        // Image scaledImage = image.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        // ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        // JLabel background = new JLabel(scaledImageIcon);
        // background.setBounds(0, 0, WIDTH, HEIGHT);
        // add(background);

        addGuiComponents();
        ++ turn ;
    }
    private void addGuiComponents(){
        //display
        play1 = new JLabel("PLAYER 1");
        play1.setBounds(margin, 30, 120, 30);
        play1.setFont(customFont.deriveFont(25f));
        play1.setForeground(TEXT_COLOR);
        createHeart(lifeOfPlayer1,1);
        displayScore(100);

        play2 = new JLabel("PLAYER 2");
        play2.setBounds(margin, HEIGHT/2, 120, 30);
        play2.setFont(customFont.deriveFont(25f));
        play2.setForeground(TEXT_COLOR);
        createHeart(lifeOfPlayer2,2);
        displayScore(HEIGHT/2+70);

        round = new JLabel("TURN: "+ (turn+1) + " / 5");
        round.setForeground(TEXT_COLOR);
        round.setBounds(WIDTH - 150, 30, 150, 30);
        round.setFont(customFont.deriveFont(20f));

        //hidden word
        hiddeWordsLabel = new JLabel(hiddeWords(word));
        hiddeWordsLabel.setFont(customFont.deriveFont(60f));
        hiddeWordsLabel.setForeground(TEXT_COLOR);
        hiddeWordsLabel.setBounds(WIDTH/2-hiddeWordsLabel.getWidth()/2,(HEIGHT-160)/2,WIDTH-190,100);
        //hiddeWordsLabel.setBounds(200+(WIDTH-200)/2-70,HEIGHT-300,WIDTH-190,100);
 
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
            //button.addActionListener(this);

            // using ASCII values to caluclate the current index
            int currentIndex = c - 'A';

            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);
        }

        // // reset button
        // JButton resetButton = new JButton("Reset");
        // resetButton.setFont(customFont.deriveFont(15f));
        // resetButton.setForeground(BACKGROUND);
        // resetButton.setBackground(BACKGROUND);
        // resetButton.setOpaque(true);
        // //resetButton.addActionListener(this);
        // buttonPanel.add(resetButton);

        // quit button
        JButton giveUpButton = new JButton("Give Up");
        giveUpButton.setFont(customFont.deriveFont(13f));
        giveUpButton.setForeground(BACKGROUND);
        giveUpButton.setBackground(BACKGROUND);
        giveUpButton.setOpaque(true);
        //giveUpButton.addActionListener(this);
        buttonPanel.add(giveUpButton);

        
        getContentPane().add(play1);
        getContentPane().add(play2);
        getContentPane().add(round);
        getContentPane().add(buttonPanel);
        getContentPane().add(hiddeWordsLabel);

    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();

        // disable button
        JButton button = (JButton) e.getSource();
        button.setEnabled(false);

        if (wordChallenge[1].contains(command)) {
            button.setBackground(Color.GREEN);
        }
        else{
            button.setBackground(Color.RED);
        }
        
    }
    
    public static void main(String[] args) {
        new game().setVisible(true);
    }

}