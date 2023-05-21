import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Winner extends JFrame {

    private JLabel message;

    private final int WIDTH = 800;
    private final int HEIGHT = 250;
    static Color TEXT_COLOR = Color.WHITE;
    static Color BACKGROUND = Color.BLACK;
    static Color PRIMARY_COLOR = Color.WHITE;
    private String result ;
    

    public Winner(){
        super("");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
       
        ImageIcon imageIcon = new ImageIcon("HwOSLabDuo/resc/BG.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledImageIcon);
        background.setBounds(0, 0, WIDTH, HEIGHT);
        add(background);

        insetResult(background);

    }
    private void insetResult(JLabel background) {
        
        if (result == "win") {
            message = new JLabel("YOU WIN!", SwingConstants.CENTER);
        } else if (result == "lost") {
            message = new JLabel("YOU LOST!", SwingConstants.CENTER);
        }
        message.setFont(getExternalFont20("Mali-SemiBoldItalic.ttf"));        
        message.setForeground(TEXT_COLOR);
        message.setBounds(0, HEIGHT/2 - message.getHeight()/2, WIDTH, HEIGHT);
        background.setLayout(new BorderLayout());
        background.add(message, BorderLayout.CENTER);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setVerticalAlignment(SwingConstants.CENTER);
        setVisible(true);
    }

    private Font getExternalFont20(String path) {
		Font customFont = null;
		
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(70f); // change size
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}

		return customFont;

	}
    public static void main(String[] args) {
        new Winner().setVisible(true);
    }

}