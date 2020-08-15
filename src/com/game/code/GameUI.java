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

	private static final long serialVersionUID = 1L;
	Position position = new Position();
	JButton[] buttons = new JButton[Position.SIZE];
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
		for(int i = 0; i< Position.SIZE; i++)
		{
			final JButton button = createButton();
			buttons[i] = button;
			final int idx = i ;
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					button.setText(Character.toString(position.turn));
					position.move(idx);
					if(!position.isGameOver())
					{
						int bestMoveIdx = position.bestMove();
						buttons[bestMoveIdx].setText(Character.toString(position.turn));
						position.move(bestMoveIdx);
					}
					if(position.isGameOver())
					{
						String message = position.isGameWonBy('x') ? "You won" : 
							position.isGameWonBy('o') ? "Computer won" : "Draw";
						JOptionPane.showMessageDialog(null, message); 
					}
				}
			});
		}
		pack();
		setVisible(true);
	}

	private JButton createButton() {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(100,100));
		button.setFont(new Font(null, Font.PLAIN, 50));
		add(button);
		return button;
	}

}
