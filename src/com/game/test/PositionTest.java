package com.game.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.game.code.Position;

class PositionTest {

	@Test
	@DisplayName("Testing new position")
	void testNewPosition() throws Exception {
		Position position = new Position();
		assertEquals('x', position.turn);
		assertEquals("         ", position.toString());
	}
	
	@Test
	@DisplayName("Testing move by x at index 1")
	void testMove() throws Exception {
		Position position = new Position();
		position = position.move(1);
		assertEquals('o', position.turn);
		assertEquals(" x       ", position.toString());
	}
	
	@Test
	@DisplayName("Testing unmove by x at index 1")
	public void testUnmove() throws Exception{
		Position position = new Position();
		position = position.move(1).unmove(1);
		assertEquals('x', position.turn);
		assertEquals("         ", position.toString());
	}
	
	@Test
	@DisplayName("Testing possible moves")
	public void testPossibleMoves() throws Exception {
		List<Integer> listOfInt = new ArrayList<Integer>();
		Position position = new Position();
		for( int i=0; i< position.SIZE;i++) {
			listOfInt.add(i);
		}
		listOfInt.remove(new Integer(1));
		listOfInt.remove(new Integer(2));
		assertEquals(listOfInt, position.move(1).move(2).possibleMoves());
	}	
	
	@Test
	@DisplayName("Testing if game is won as per possible moves")
	public void testIsGameWonBy() throws Exception {

		assertFalse(new Position().isGameWonBy('x'));
		//testing for win xxx in a row
		assertTrue(new Position("xxx      ",'x').isGameWonBy('x'));
		//testing for win xxx in column
		assertTrue(new Position(
				 "x  "
				+"x  "
				+"x  ",'x').isGameWonBy('x'));
		//testing for win ooo in diagnoal
		assertTrue(new Position(
				 "o  "
				+" o "
				+"  o",'x').isGameWonBy('o'));
		//testing for win ooo in diagnoal
		assertTrue(new Position(
				 "  o"
				+" o "
				+"o  ",'x').isGameWonBy('o'));
	}
	@Test
	public void testMinMax() throws Exception
	{
		// return how many blank spaces are
		assertEquals(6, new Position("xxx      ",'x').minmax());
		// negative value if win for o
		assertEquals(-6, new Position("ooo      ",'o').minmax());
		// zero if draw
		assertEquals(0, new Position("xoxxoxoxo",'x').minmax());
		
		// test recursive cases
		assertEquals(6, new Position("xx       ",'x').minmax());
		// neg value if win for o
	   assertEquals(-6, new Position("oo       ",'o').minmax());
	}
	@Test
	public void testBestMove() throws Exception {
		assertEquals(2, new Position("xx       ",'x').bestMove());
		assertEquals(2, new Position("oo       ",'o').bestMove());
	}
	@Test
	public void testIsGameOver() throws Exception
	{
		assertFalse(new Position().isGameOver());
		assertTrue(new Position("xxx      ",'x').isGameOver());
		assertTrue(new Position("ooo      ",'x').isGameOver());
		assertTrue(new Position("xoxxoxoxo",'x').isGameOver());
	}
}
