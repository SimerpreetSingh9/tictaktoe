package com.game.code;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GameUI extends JFrame	{

	private static final String DRAW = "Its a DRAW";
	private static final String COMPUTER_WIN = "Computer WON the game";
	private static final String YOU_WIN = "You WON the game";
	private static final long serialVersionUID = 1L;
	Position position = new Position();
	JButton[] buttons = new JButton[Position.BOARDSIZE];
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new GameUI();
				
			}
		});
	}
	
	public GameUI() {
		setLayout(new GridLayout(Position.DIMENSION, Position.DIMENSION));
		createLayout();
		pack();
		setVisible(true);
	}

	private void createLayout() {
		for(int i = 0; i< Position.BOARDSIZE; i++)
		{
			final JButton button = createButton();
			buttons[i] = button;
			final int idx = i ;
			bindBtnToTicTakToeLogic(button, idx);
		}
	}

	private void bindBtnToTicTakToeLogic(final JButton button, final int idx) {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				button.setText(Character.toString(position.playerTurn));
				position.move(idx);
				if(!position.isGameOver())
				{
					computeBestMove();
				}
				if(position.isGameOver())
				{
					String message = position.isGameWonBy('x') ? YOU_WIN : 
									 position.isGameWonBy('o') ? COMPUTER_WIN : DRAW;
					JOptionPane.showMessageDialog(null, message); 
				}
			}

			private void computeBestMove() {
				int bestMoveIdx = position.bestMove();
				buttons[bestMoveIdx].setText(Character.toString(position.playerTurn));
				position.move(bestMoveIdx);
			}
		});
	}

	private JButton createButton() {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(100,100));
		button.setFont(new Font(null, Font.PLAIN, 50));
		add(button);
		return button;
	}

}
