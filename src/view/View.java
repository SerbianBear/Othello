package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import model.Model;

public class View implements Observer{
	private JFrame frame;
	private JButton[] buttons;
	private int boardPixelDimension = 440;
	private final ImageIcon black = new ImageIcon("C:/Users/Nikola/workspace/Othello/src/view/black.png");
	private final ImageIcon white = new ImageIcon("C:/Users/Nikola/workspace/Othello/src/view/white.png");
	private final ImageIcon empty = new ImageIcon("C:/Users/Nikola/workspace/Othello/src/view/blank.png");

	public View (ActionListener actionListener, KeyListener keyListener){
		this.frame = new JFrame();
		frame.setSize(new Dimension(boardPixelDimension,boardPixelDimension));
		frame.setVisible(true);
		frame.setLayout(new GridLayout(Model.boardSize,Model.boardSize));
		frame.addKeyListener(keyListener);

		buttons = new JButton[Model.boardSize * Model.boardSize];

		for(int i = 0; i < buttons.length; i ++){
			buttons[i] = new JButton();
			frame.add(buttons[i]);
			buttons[i].addActionListener(actionListener);
			buttons[i].setToolTipText(i + "");
			buttons[i].setFocusable(false);
		}
	}

	@Override
	public void update(Observable observable, Object object) {
		char[][] board = (char[][])object;

		int twoDToOneDArrayIndex;
		if(board[0][0] == Model.IMPOSSIBLE_MOVE || board[0][0] == Model.POSSIBLE_MOVE){
			for(int i = 0; i < board.length; i++){
				for(int j = 0; j < board[i].length; j++){
					twoDToOneDArrayIndex = i*Model.boardSize + j;
					switch(board[i][j]){
					case Model.IMPOSSIBLE_MOVE:
						buttons[twoDToOneDArrayIndex].setEnabled(false);
						break;
					case Model.POSSIBLE_MOVE:
						buttons[twoDToOneDArrayIndex].setEnabled(true);
						break;
					default:
						buttons[twoDToOneDArrayIndex].setEnabled(false);
						break;
					}
				}
			}
		}else {
			for(int i = 0; i < board.length; i++){
				for(int j = 0; j < board[i].length; j++){
					twoDToOneDArrayIndex = i*Model.boardSize + j;
					switch(board[i][j]){
					case Model.BLACK_CHARACTER:
						buttons[twoDToOneDArrayIndex].setIcon(black);
						break;
					case Model.WHITE_CHARACTER:
						buttons[twoDToOneDArrayIndex].setIcon(white);
						break;
					default:
						buttons[twoDToOneDArrayIndex].setIcon(empty);
						break;
					}
				}
			}
		}

		frame.repaint();
	}

	public JFrame getFrame(){
		return this.frame;
	}
}
