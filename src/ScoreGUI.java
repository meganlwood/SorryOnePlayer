import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ScoreGUI extends JDialog{
	public ScoreGUI(int score) {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/kenvector_future.ttf"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		font = font.deriveFont(Font.PLAIN, 20);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		setSize(400,200);
		setLocation(300,300);
		JPanel container = new JPanel();
		JLabel congratulations = new JLabel("Congratulations!");
		font = font.deriveFont(Font.PLAIN, 30);
		congratulations.setFont(font);
		container.add(congratulations);
		font = font.deriveFont(Font.PLAIN, 20);
		JLabel scoreLabel = new JLabel("Your score: " + score);
		JTextField nameField = new JTextField("Enter Your Name");
		scoreLabel.setFont(font);
		nameField.setFont(font);
		JButton confirm = new JButton("confirm");
		confirm.setFont(font);
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileWriter fw = null;
				//BufferedWriter bw = null;
				PrintWriter pw = null;
				try {
					fw = new FileWriter("resources/scores.txt", true);
					pw = new PrintWriter(fw);
					pw.println(nameField.getText() + " " + score);
					pw.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (fw != null)
					try {
						fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if (pw != null) pw.close();

				setVisible(false);
			}
			
		});
		container.add(scoreLabel);
		container.add(nameField);
		container.add(confirm);
		add(container);
	}
}
