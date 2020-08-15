package com.game.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Position {

	public static final int DIMENSION = 3;
	public static final int BOARDSIZE = DIMENSION * DIMENSION;
	public char playerTurn;
	public char[] ticTacToeBoard;

	public Position() {
		this.playerTurn = 'x';
		ticTacToeBoard = new char[BOARDSIZE];
		initializeTicTacToeBoard();
	}

	private void initializeTicTacToeBoard() {
		for (int i = 0; i < BOARDSIZE; i++) {
			ticTacToeBoard[i] = ' ';
		}
	}

	public Position(String board, char turn) {
		this.ticTacToeBoard = board.toCharArray();
		this.playerTurn = turn;
	}

	public Position move(int index) {
		ticTacToeBoard[index] = playerTurn;
		playerTurn = playerTurn == 'x' ? 'o' : 'x';
		return this;
	}

	public Position unmove(int index) {
		ticTacToeBoard[index] = ' ';
		playerTurn = playerTurn == 'x' ? 'o' : 'x';
		return this;
	}

	public List<Integer> possibleMoves() {
		List<Integer> listOfInt = new ArrayList<Integer>();
		for (int i = 0; i < ticTacToeBoard.length; i++) {
			if (ticTacToeBoard[i] == ' ')
				listOfInt.add(i);
		}
		return listOfInt;
	}

	public boolean isGameWonBy(char turn) {
		boolean isWin = false;
		return consecutiveMatchInRow(turn, isWin) || consecutiveMatchInColumn(turn, isWin)
				|| consecutiveMatchLeftDiagonal(turn, isWin) || consecutiveMatchRightDiagonal(turn, isWin);
	}

	private boolean consecutiveMatchRightDiagonal(char turn, boolean isWin) {
		isWin = isWin || MatchALine(turn, DIMENSION - 1, BOARDSIZE - 1, DIMENSION - 1);
		return isWin;
	}

	private boolean consecutiveMatchLeftDiagonal(char turn, boolean isWin) {
		isWin = isWin || MatchALine(turn, 0, BOARDSIZE, DIMENSION + 1);
		return isWin;
	}

	private boolean consecutiveMatchInColumn(char turn, boolean isWin) {
		for (int i = 0; i < DIMENSION; i++) {
			isWin = isWin || MatchALine(turn, i, BOARDSIZE, DIMENSION);
		}
		return isWin;
	}

	private boolean consecutiveMatchInRow(char turn, boolean isWin) {
		for (int i = 0; i < BOARDSIZE; i += DIMENSION) {
			isWin = isWin || MatchALine(turn, i, i + DIMENSION, 1);
		}
		return isWin;
	}

	private boolean MatchALine(char turn, int startIndex, int endIndex, int step) {
		for (int i = startIndex; i < endIndex; i += step) {
			if (ticTacToeBoard[i] != turn)
				return false;
		}
		return true;
	}

	public int blanksOnBoard() {
		int totalBlanksOnBoard = 0;
		for (int i = 0; i < BOARDSIZE; i++) {
			if (ticTacToeBoard[i] == ' ')
				totalBlanksOnBoard++;
		}
		return totalBlanksOnBoard;
	}

	public int movesRequiredToWinGameByEitherPlayer() {
		if (isGameWonBy('x'))
			return blanksOnBoard();
		if (isGameWonBy('o'))
			return -blanksOnBoard();
		if (blanksOnBoard() == 0)
			return 0;
		// recursive cases
		List<Integer> list = new ArrayList<Integer>();
		for (Integer idx : possibleMoves()) {
			list.add(move(idx).movesRequiredToWinGameByEitherPlayer());
			unmove(idx);
		}
		return playerTurn == 'x' ? Collections.max(list) : Collections.min(list);
	}

	public int bestMove() {
		Comparator<Integer> cmp = new Comparator<Integer>() {
			public int compare(Integer firstIndex, Integer secondIndex) {
				int moveNeededAtFirstIndex = move(firstIndex).movesRequiredToWinGameByEitherPlayer();
				unmove(firstIndex);
				int moveNeededAtSecondIndex = move(secondIndex).movesRequiredToWinGameByEitherPlayer();
				unmove(secondIndex);
				return moveNeededAtFirstIndex - moveNeededAtSecondIndex;
			}
		};
		List<Integer> list = possibleMoves();
		return playerTurn == 'x' ? Collections.max(list, cmp) : Collections.min(list, cmp);

	}

	public boolean isGameOver() {
		return isGameWonBy('x') || isGameWonBy('o') || blanksOnBoard() == 0;
	}

	@Override
	public String toString() {
		return new String(ticTacToeBoard);
	}
}
