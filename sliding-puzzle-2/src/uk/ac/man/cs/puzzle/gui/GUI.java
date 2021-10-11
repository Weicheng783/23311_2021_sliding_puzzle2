package uk.ac.man.cs.puzzle.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.man.cs.puzzle.logic.Model;

public class GUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Model puzzleModel;
	private GraphicsPanel puzzleGraphics;
	private int ROWS;
	private int COLS;
	public static JLabel currentMovesLabel = new JLabel();

	public GUI(int rows, int cols) {
		// Create a button. Add a listener to it.
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new NewGameAction());

		// Create control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(newGameButton);
		
		currentMovesLabel = new JLabel("", JLabel.CENTER);
		currentMovesLabel.setText(String.valueOf(0));
//		currentMovesLabel.setText(String.valueOf(puzzleModel.getMoveCount()));
//		controlPanel.add(currentMovesLabel);

		// Create graphics panel
		ROWS = rows;
		COLS = cols;
		puzzleModel = new Model(ROWS, COLS);
		puzzleGraphics = new GraphicsPanel(puzzleModel, rows, cols);

		// Set the layout and add the components
		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(puzzleGraphics, BorderLayout.CENTER);
		this.add(currentMovesLabel, BorderLayout.SOUTH);
		
		currentMovesLabel.setText("0");
		
	}

	Model getPuzzleModel() {
		return puzzleModel;
	}

	GraphicsPanel getGraphicsPanel() {
		return puzzleGraphics;
	}
	
	

	public class NewGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			puzzleModel.reset();
//			System.out.println("Now"+puzzleModel.moveCount);
			puzzleModel.shuffle();
			puzzleModel.flag = 0;
			puzzleGraphics.repaint();
			puzzleGraphics.setBackground(Color.black);
			currentMovesLabel.setText(String.valueOf(puzzleModel.getMoveCount()));
		}
	}
}
