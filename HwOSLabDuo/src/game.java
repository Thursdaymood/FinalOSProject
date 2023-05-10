import javax.swing.*;
import java.awt.*;

public class game extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    static final int margin = 15;
    static final int size_heart = 30;
    static Color white = Color.WHITE;
    public static void createGUI() {
        
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("HANG_MAN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        //line
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D line = (Graphics2D) g;

                line.setColor(white);
                line.drawLine(200, getHeight()/2+100, 900, getHeight()/2+100);

                line.setColor(white);
                line.drawLine(200, 0, 200, 700);
                
            }
        };

        panel.setBackground(Color.BLACK);
        frame.add(panel);
        //PLAY1
        JLabel play1 = new JLabel("PLAY1");
        play1.setBounds(15, 30, 100, 30);
        play1.setFont(new Font("Arial", Font.BOLD, 18));
        play1.setForeground(Color.WHITE);

        //heart
        int pos_x = margin;
        for (int i = 0; i < 3; i++) {
            ImageIcon image = new ImageIcon("resc/heart.png");
            Image smallImage = image.getImage().getScaledInstance(size_heart, size_heart, Image.SCALE_SMOOTH);
            ImageIcon smallIcon = new ImageIcon(smallImage);
            JLabel heart = new JLabel(smallIcon);
            heart.setBounds(pos_x, 65, size_heart, size_heart);
            panel.add(heart);

            pos_x += size_heart+10;
        }
        

        //score
        JLabel score1 = new JLabel("Score:");
        score1.setForeground(Color.WHITE);
        score1.setBounds(15, 100, 1000, 30);
        score1.setFont(new Font("Arial", Font.BOLD, 18));

        
        panel.setLayout(null);
        panel.add(play1);
        panel.add(score1);
        

        //turn
        JLabel turn = new JLabel("TURN");
        turn.setForeground(Color.WHITE);
        turn.setBounds(WIDTH - 150, 30, 100, 30);
        turn.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(turn);

        frame.setVisible(true);
    }

}