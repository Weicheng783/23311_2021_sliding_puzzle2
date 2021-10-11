package uk.ac.man.cs.puzzle.logic;

import uk.ac.man.cs.puzzle.gui.GUI;

public class Model {
	private static int ROWS;
	private static int COLS;

	private Tile[][] contents; // All tiles.
	private Tile emptyTile; // The empty space.

	public int moveCount; // Created moveCount.
	
	public int flag = 0;//flag for moveCounter to determine whether to increase.
	public Model(int rows, int cols) {
		ROWS = rows;
		COLS = cols;
		contents = new Tile[rows][cols];
		
		moveCount = 0;
		GUI.currentMovesLabel.setText(String.valueOf(getMoveCount()));
		
		reset();
	}

	// Return the string to display at given row, column.
	public String getFace(int row, int col) {
		return contents[row][col].getFace();
	}

	// Initialise and game model
	public void reset() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				contents[r][c] = new Tile(r, c, "" + (r * COLS + c + 1));
			}
		}

		// Set last tile face to null to mark empty space
		emptyTile = contents[ROWS - 1][COLS - 1];
		emptyTile.setFace(null);
		
		moveCount = 0;
		GUI.currentMovesLabel.setText(String.valueOf(getMoveCount()));
	}

	// Shuffle the tiles around to create a new game.
	public void shuffle() {
		// Mix up the board through a series of legal moves.
		int rand = (int) (Math.random() * 1000);
		for (int i = 0; i < rand; i++) {
			int r = (int) (Math.random() * ROWS);
			int c = (int) (Math.random() * COLS);
			
			flag = 1;
			
			moveTile(r, c);
		}
		
		flag = 0;
	}

	// Move a tile to empty position beside it, if possible.
	// Return true if it was moved, false if not legal.
	public boolean moveTile(int r, int c) {
		// It's a legal move if the empty cell is next to it.
		return checkEmpty(r, c, -1, 0) || checkEmpty(r, c, 1, 0) || checkEmpty(r, c, 0, -1) || checkEmpty(r, c, 0, 1);
	}

	// Check to see if there is an empty position beside tile.
	// Return true and exchange if possible, else return false.
	private boolean checkEmpty(int r, int c, int rdelta, int cdelta) {
		int rNeighbor = r + rdelta;
		int cNeighbor = c + cdelta;
		// Check to see if this neighbour is on board and is empty.
		if (isLegalRowCol(rNeighbor, cNeighbor) && contents[rNeighbor][cNeighbor] == emptyTile) {
			exchangeTiles(r, c, rNeighbor, cNeighbor);
			return true;
		}
		return false;
	}

	// Check for legal row, column
	private boolean isLegalRowCol(int r, int c) {
		return r >= 0 && r < ROWS && c >= 0 && c < COLS;
	}

	// Exchange two tiles.
	private void exchangeTiles(int r1, int c1, int r2, int c2) {
		Tile temp = contents[r1][c1];
		contents[r1][c1] = contents[r2][c2];
		contents[r2][c2] = temp;
		
		if (flag == 1) {
			GUI.currentMovesLabel.setText(String.valueOf(getMoveCount()));
			flag = 0;
//			System.out.println("0");
		}else{
			moveCount ++;
			GUI.currentMovesLabel.setText(String.valueOf(getMoveCount()));
//			System.out.println("1");
		}
	}

	public boolean gameOver() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				Tile trc = contents[r][c];
				if (!trc.isInFinalPosition(r, c))
					return false;
			}
		}

		// Falling through loop means nothing out of place.
		return true;
	}

	public int getRows() {
		return ROWS;
	}

	public int getCols() {
		return COLS;
	}

	public int getMoveCount() {
		// TODO Auto-generated method stub
		return moveCount;
	}
	
	public void resetMoveCounter() {
		// TODO Auto-generated method stub
		moveCount = 0;
		GUI.currentMovesLabel.setText("0");
	}

}