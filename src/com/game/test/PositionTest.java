package com.game.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.game.code.Position;

class PositionTest {

	private static Position position;
	
	@BeforeAll
	static void init()
	{
		position = new Position();
	}
	
	@Test
	void testNewPosition() throws Exception {
		assertEquals('x', position.turn);
		assertEquals("         ", position.toString());
	}
	
	@Test
	@DisplayName("Testing move by x at index 1")
	void testMove() throws Exception {
		position = position.move(1);
		assertEquals('o', position.turn);
		assertEquals(" x       ", position.toString());
	}
	
}
