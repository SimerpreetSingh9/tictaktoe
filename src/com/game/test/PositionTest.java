package com.game.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.game.code.Position;

class PositionTest {

	private static Position position;
	@Test
	void testNewPosition() throws Exception {
		assertEquals('x', position.turn);
		assertEquals("         ", position.toString());
	}
	
	@BeforeAll
	static void init()
	{
		position = new Position();
	}

}
