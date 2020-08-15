package com.game.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.game.code.Position;

class PositionTest {

	private Position position;

	@BeforeEach
	public void initializePosition() {
		this.position = new Position();
	}

	@AfterEach
	public void clearPosition() {
		this.position = null;
	}

	@Test
	@DisplayName("Testing new position")
	void testNewPosition() throws Exception {
		assertEquals('x', position.playerTurn);
		assertEquals("         ", position.toString());
	}

	@Test
	@DisplayName("Testing move by x at index 1")
	void testMove() throws Exception {
		position = position.move(1);
		assertEquals('o', position.playerTurn);
		assertEquals(" x       ", position.toString());
	}

	@Test
	@DisplayName("Testing unmove by x at index 1")
	public void testUnmove() throws Exception {
		position = position.move(1).unmove(1);
		assertEquals('x', position.playerTurn);
		assertEquals("         ", position.toString());
	}

	@Test
	@DisplayName("Testing possible moves")
	public void testPossibleMoves() throws Exception {
		List<Integer> listOfInt = new ArrayList<Integer>();
		for (int i = 0; i < position.BOARDSIZE; i++) {
			listOfInt.add(i);
		}
		listOfInt.remove(new Integer(1));
		listOfInt.remove(new Integer(2));
		assertEquals(listOfInt, position.move(1).move(2).possibleMoves());
	}

	@Test
	@DisplayName("Testing if game is won as per possible moves")
	public void testIsGameWonBy() throws Exception {

		assertFalse(position.isGameWonBy('x'));
		// testing for win xxx in a row
		assertTrue(new Position("xxx      ", 'x').isGameWonBy('x'));
		// testing for win xxx in column
		assertTrue(new Position("x  " + "x  " + "x  ", 'x').isGameWonBy('x'));
		// testing for win ooo in diagnoal
		assertTrue(new Position("o  " + " o " + "  o", 'x').isGameWonBy('o'));
		// testing for win ooo in diagnoal
		assertTrue(new Position("  o" + " o " + "o  ", 'x').isGameWonBy('o'));
	}

	@Test
	public void testMinMax() throws Exception {
		// return how many blank spaces are
		assertEquals(6, new Position("xxx      ", 'x').movesRequiredToWinGameByEitherPlayer());
		// negative value if win for o
		assertEquals(-6, new Position("ooo      ", 'o').movesRequiredToWinGameByEitherPlayer());
		// zero if draw
		assertEquals(0, new Position("xoxxoxoxo", 'x').movesRequiredToWinGameByEitherPlayer());
		// test recursive cases
		assertEquals(6, new Position("xx       ", 'x').movesRequiredToWinGameByEitherPlayer());
		// negative value if win for o
		assertEquals(-6, new Position("oo       ", 'o').movesRequiredToWinGameByEitherPlayer());
	}

	@Test
	public void testBestMove() throws Exception {
		assertEquals(2, new Position("xx       ", 'x').bestMove());
		assertEquals(2, new Position("oo       ", 'o').bestMove());
	}

	@Test
	public void testIsGameOver() throws Exception {
		assertFalse(new Position().isGameOver());
		assertTrue(new Position("xxx      ", 'x').isGameOver());
		assertTrue(new Position("ooo      ", 'x').isGameOver());
		assertTrue(new Position("xoxxoxoxo", 'x').isGameOver());
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
