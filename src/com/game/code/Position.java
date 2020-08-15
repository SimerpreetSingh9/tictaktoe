package com.game.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Position {

	public static final int DIMENSION = 3;
	public static final int BOARDSIZE = DIMENSION*DIMENSION;
	public char playerTurn;
	public char[] ticTacToeBoard;
	private Map<Integer, Integer> cache = new HashMap<Integer, Integer>();

	public Position() {
		this.playerTurn = 'x';
		ticTacToeBoard = new char[BOARDSIZE];
		initializeTicTacToeBoard();
	}


	private void initializeTicTacToeBoard() {
		for(int i=0 ; i< BOARDSIZE; i++)
		{
			ticTacToeBoard[i]=' ';
		}
	}
	
	
	public Position(String board, char turn) {
		this.ticTacToeBoard = board.toCharArray();
		this.playerTurn = turn;
	}


	public Position move(int index)
	{
		ticTacToeBoard[index] = playerTurn;
		playerTurn = playerTurn == 'x' ? 'o' : 'x';
		return this;
	}
	
	@Override
	public String toString()
	{
		return new String(ticTacToeBoard);
	}

	public Position unmove(int index) {
		ticTacToeBoard[index] = ' ';
		playerTurn = playerTurn == 'x' ? 'o' : 'x';
		return this;
	}
	
	public List<Integer> possibleMoves() {
		List<Integer> listOfInt = new ArrayList<Integer>();
		for (int i = 0; i < ticTacToeBoard.length; i++)
		{
			if (ticTacToeBoard[i] == ' ')
				listOfInt.add(i);
		}
		return listOfInt;
	}

	public boolean isGameWonBy(char turn) {
		boolean isWin = false;
		// test win for xxx in row
		for(int i=0; i< BOARDSIZE ; i+= DIMENSION) {
			isWin = isWin || MatchALine(turn, i, i+DIMENSION , 1);
		}
		// test win for xxx in column
		for(int i=0; i< DIMENSION ; i++) {
			isWin = isWin || MatchALine(turn, i, BOARDSIZE , DIMENSION);
		}
		// test for diagonal
		isWin = isWin || MatchALine(turn, 0, BOARDSIZE, DIMENSION+1);
		// test for diagonal
		isWin = isWin || MatchALine(turn, DIMENSION-1, BOARDSIZE-1, DIMENSION-1);
		return isWin;
	}


	private boolean MatchALine(char turn, int startIndex, int endIndex, int step) {
		for(int i= startIndex; i < endIndex; i+= step)
		{
			if(ticTacToeBoard[i] != turn)
				return false;
		}
		return true;
	}

	public int blanks() {
		int total = 0;
		for(int i=0; i< BOARDSIZE; i++)
		{
			if(ticTacToeBoard[i] == ' ')
				total++;
		}
	return total;
	}
	public int minmax() {
		// implemnting caching
		Integer key = code();
		Integer value = cache.get(key);
		if(value != null) return value;
		
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
		value = playerTurn == 'x' ? Collections.max(list) : Collections.min(list);
		cache.put(key, value);
		return value;
	}
	public int code()
	{
		int value = 0;
		for(int i = 0; i< BOARDSIZE; i++)
		{
			value = value*3; 
			if(ticTacToeBoard[i]=='x')
				value +=1;
			else if(ticTacToeBoard[i] == 'o')
				value+=2;
		}
		return value;
	}


	public int bestMove() {
		Comparator<Integer> cmp = new Comparator<Integer>() {
			public int compare(Integer first, Integer second) {
				int a = move(first).minmax();
				unmove(first);
				int b = move(second).minmax();
				unmove(second);
				return a-b;
			}
		};
		List<Integer> list = possibleMoves();
		return playerTurn == 'x' ? Collections.max(list, cmp) : Collections.min(list, cmp);
		
	}


	public boolean isGameOver() {
		return isGameWonBy('x') || isGameWonBy('o') || blanks() == 0;
	}
}
