package uk.ac.man.cs.puzzle.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.event.MouseEvent;

import org.junit.Before;
import org.junit.Test;

import uk.ac.man.cs.puzzle.logic.Model;

public class MoveCounterTest {

	private static final int ROWS = 2, COLS = 2;
	private GUI gui;
	private GraphicsPanel puzzleGraphics;
	private Model puzzleModel;

	@Before
	public void setUpGame() {
		gui = new GUI(ROWS, COLS);
		puzzleGraphics = gui.getGraphicsPanel();
		puzzleModel = gui.getPuzzleModel();

		// Load an unfinished game one move away from completion, by moving the tile at
		// (1,0) to (1,1). Puzzle state is:
		//
		// 1 2
		// - 3
		puzzleModel.reset();
		puzzleModel.moveTile(1, 0);
		puzzleModel.resetMoveCounter();
	}

	@Test
	public void shouldInitialiseMoveCounterWhenGameLoads() {
		assertEquals("0", gui.currentMovesLabel.getText());
	}

	@Test
	public void shouldUpdateMoveCounterOnGameMove() throws InterruptedException {
		mousePressOnTile(0, 0);
		assertEquals("1", gui.currentMovesLabel.getText());

		mousePressOnTile(0, 1);
		assertEquals("2", gui.currentMovesLabel.getText());

		mousePressOnTile(1, 1);
		assertEquals("3", gui.currentMovesLabel.getText());
	}

	@Test
	public void shouldResetMoveCounterWhenNewGameStarted() {
		mousePressOnTile(0, 0);
		assertEquals("1", gui.currentMovesLabel.getText());

		// Simulate pressing the New Game button
		gui.new NewGameAction().actionPerformed(null);
		assertEquals("0", gui.currentMovesLabel.getText());

	}

	/* Test helper methods */

	// Simulate a mouse press event at coordinates corresponding to the given tile
	private void mousePressOnTile(int row, int col) {
		int cellSize = GraphicsPanel.CELL_SIZE;
		assertTrue("Condition for test to make sense violated: tiles must be bigger than 1 pixal across.",
				cellSize > 1);
		int x = (col * cellSize) + cellSize / 2;
		int y = (row * cellSize) + cellSize / 2;

		MouseEvent dummyMouseEvent = new MouseEvent(puzzleGraphics, 0, 0, 0, x, y, 1, false);
		puzzleGraphics.mousePressed(dummyMouseEvent);
	}

}
