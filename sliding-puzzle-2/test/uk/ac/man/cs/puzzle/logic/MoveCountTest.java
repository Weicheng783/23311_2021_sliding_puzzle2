package uk.ac.man.cs.puzzle.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MoveCountTest {

	private Model puzzleModel = new Model(2, 2);

	@Before
	public void setUpPuzzleModel() {
		puzzleModel.reset();

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
	public void shouldReportZeroMovesWhenGameLoads() {
		assertEquals(0, puzzleModel.getMoveCount());
	}

	@Test
	public void shouldIncrementMoveCounterWhenGameMovesAreMade() {
		// Move a tile we know can move in the game we are using
		puzzleModel.moveTile(0, 0);
		assertEquals(1, puzzleModel.getMoveCount());
		puzzleModel.moveTile(0, 1);
		assertEquals(2, puzzleModel.getMoveCount());
		puzzleModel.moveTile(1, 1);
		assertEquals(3, puzzleModel.getMoveCount());
	}

	@Test
	public void shouldNotIncrementMoveCounterWhenMoveNotMade() {
		// Move a tile we know we can't move in the game we are using
		puzzleModel.moveTile(0, 1);
		assertEquals(0, puzzleModel.getMoveCount());
	}

	@Test
	public void shouldNotIncrementMoveCounterWhenShufflingBoard() {
		puzzleModel.moveTile(0, 0);
		puzzleModel.moveTile(0, 1);
		int expectedMoves = 2;
		assertEquals(expectedMoves, puzzleModel.getMoveCount());

		puzzleModel.shuffle();
		assertEquals(expectedMoves, puzzleModel.getMoveCount());
	}

	@Test
	public void shouldResetMovesToZeroWhenGameReset() {
		// Move a tile we know can move in the game we are using for testing
		puzzleModel.moveTile(0, 0);
		assertEquals(1, puzzleModel.getMoveCount());

		puzzleModel.reset();
		assertEquals(0, puzzleModel.getMoveCount());
	}

}
