package uk.ac.man.cs.puzzle.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import uk.ac.man.cs.puzzle.logic.Model;

public class GUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Model puzzleModel;
	private GraphicsPanel puzzleGraphics;
	private Timer gameTimer;
	private int ROWS;
	private int COLS;
	public static JLabel currentMovesLabel = new JLabel();

	public GUI(int rows, int cols) {
		// Create a button. Add a listener to it.
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new NewGameAction());

		// Create game timer components
		JLabel timerLabel = new JLabel("Time: ", JLabel.LEADING);
		final JLabel currentTimeLabel = new JLabel(" __ ", JLabel.CENTER);
		currentTimeLabel.setText(String.valueOf("0"));
		JLabel unitsLabel = new JLabel(" seconds", JLabel.LEADING);

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

		// Create game timer panel
		JPanel gameTimerPanel = new JPanel();
		gameTimerPanel.setLayout(new FlowLayout());
		gameTimerPanel.add(timerLabel);
		gameTimerPanel.add(currentTimeLabel);
		gameTimerPanel.add(unitsLabel);

		// Set the layout and add the components
		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(puzzleGraphics, BorderLayout.CENTER);
		this.add(gameTimerPanel, BorderLayout.WEST);
		
		this.add(currentMovesLabel, BorderLayout.SOUTH);
		currentMovesLabel.setText("0");

		// Set up the Swing timer
		gameTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (puzzleModel.gameOver()) {
					if (gameTimer.isRunning())
						gameTimer.stop();
				} else {
					puzzleModel.incrementGameTime();
					currentTimeLabel.setText(String.valueOf(puzzleModel.getGameTime()));
					unitsLabel.setText(puzzleModel.getGameTime() == 1 ? " second" : " seconds");
				}
			}
		});

		// Start the timer for the first game round
		gameTimer.start();

	}

	Model getPuzzleModel() {
		return puzzleModel;
	}

	GraphicsPanel getGraphicsPanel() {
		return puzzleGraphics;
	}
	
	

	Timer getGameTimer() {
		return gameTimer;
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
			gameTimer.restart();
		}
	}
}
