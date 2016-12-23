import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ScoreTable extends JFrame {
	public ScoreTable() {
		setSize(200,300);
		JPanel container = new JPanel(new BorderLayout());
		boolean done = false;
		ArrayList<String> names = new ArrayList<String> ();
		ArrayList<Integer> scores = new ArrayList<Integer> ();
		int size = 0;
		File file = new File("resources/scores.txt");
		if (!file.exists()) {
			container.add(new JLabel("No scores to display."));
		}
		else {
			Scanner in = null;
			try {
				in = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (in.hasNextLine()) {
				String line = in.nextLine();
				Scanner lineScan = new Scanner(line);
				String name = lineScan.next();
				int score = lineScan.nextInt();
				names.add(name);
				scores.add(score);
				size++;
			}
		}
		//sort
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size-1; j++) {
				if (scores.get(j) < scores.get(j + 1)) {
					Collections.swap(scores, j, j+1);
					Collections.swap(names, j, j+1);
				}
			}
		}
		
		Object[][] rowData = new Object[size][2];
		for (int i = 0; i < size; i++) {
			rowData[i][0] = names.get(i);
			rowData[i][1] = scores.get(i);
		}
		Object[] columnNames = {"Name", "Score"};
		JTable table = new JTable(rowData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		container.add(scrollPane);
		table.setFillsViewportHeight(true); 
		//container.add(table);
		add(container, BorderLayout.CENTER);
		
	}
}
