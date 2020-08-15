package com.game.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	
	public Position(String board, char turn) {
		this.board = board.toCharArray();
		this.turn = turn;
	}


	public Position move(int index)
	{
		board[index] = turn;
		turn = turn == 'x' ? 'o' : 'x';
		return this;
	}
	
	@Override
	public String toString()
	{
		return new String(board);
	}

	public Position unmove(int index) {
		board[index] = ' ';
		turn = turn == 'x' ? 'o' : 'x';
		return this;
	}
	
	public List<Integer> possibleMoves() {
		List<Integer> listOfInt = new ArrayList<Integer>();
		for (int i = 0; i < board.length; i++)
		{
			if (board[i] == ' ')
				listOfInt.add(i);
		}
		return listOfInt;
	}

	public boolean isGameWonBy(char turn) {
		boolean isWin = false;
		// test win for xxx in row
		for(int i=0; i< SIZE ; i+= DIMENSION) {
			isWin = isWin || MatchALine(turn, i, i+DIMENSION , 1);
		}
		// test win for xxx in column
		for(int i=0; i< DIMENSION ; i++) {
			isWin = isWin || MatchALine(turn, i, SIZE , DIMENSION);
		}
		// test for diagonal
		isWin = isWin || MatchALine(turn, 0, SIZE, DIMENSION+1);
		// test for diagonal
		isWin = isWin || MatchALine(turn, DIMENSION-1, SIZE-1, DIMENSION-1);
		return isWin;
	}


	private boolean MatchALine(char turn, int startIndex, int endIndex, int step) {
		for(int i= startIndex; i < endIndex; i+= step)
		{
			if(board[i] != turn)
				return false;
		}
		return true;
	}

	public int blanks() {
		int total = 0;
		for(int i=0; i< SIZE; i++)
		{
			if(board[i] == ' ')
				total++;
		}
	return total;
	}
	public int minmax() {
		if(isGameWonBy('x')) 
			return blanks();
		if(isGameWonBy('o')) 
			return -blanks();
		if(blanks() == 0) 
			return 0;
		// recursive cases
		List<Integer> list = new ArrayList<Integer>();
		for(Integer idx : possibleMoves()) {
			list.add(move(idx).minmax());
			unmove(idx);
		}
		// handle  0 recursion cases
		return turn == 'x' ? Collections.max(list) : Collections.min(list);
	}
}
