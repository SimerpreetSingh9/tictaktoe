package com.game.code;

public class Position {

	public static final int DIMENSION = 3;
	public static final int SIZE = DIMENSION*DIMENSION;
	public char turn;
	public char[] board;

	public Position() {
		this.turn = 'x';
		board = new char[SIZE];
		for(short i=0 ; i< SIZE; i++)
		{
			board[i]=' ';
		}
	}

	@Override
	public String toString()
	{
		return new String(board);
	}
}
