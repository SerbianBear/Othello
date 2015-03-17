package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.Model;
import view.View;

public class Controller implements ActionListener, KeyListener {

	private Model model;
	private View view;
	private int boardSize;

//	private ArrayList<Location> visitedNodes = new ArrayList<Location>();
	private final int[][] secondHeuristicWeights = 
		{{120, -20,  20,   5,   5,  20, -20, 120},
			{-20, -40,  -5,  -5,  -5,  -5, -40, -20},
			{20,  -5,  15,   3,   3,  15,  -5,  20},
			{5,  -5,   3,   3,   3,   3,  -5,   5},
			{5,  -5,   3,   3,   3,   3,  -5,   5},
			{20,  -5,  15,   3,   3,  15,  -5,  20},
			{-20, -40,  -5,  -5,  -5,  -5, -40, -20},
			{120, -20,  20,   5,   5,  20, -20, 120}};

	public Controller(){
		this.boardSize = 8;
		initializeGame();
	}

	private void initializeGame(){
		model = new Model(this.boardSize);
		view = new View(this, this);
		model.setView(view);
		JOptionPane.showMessageDialog(view.getFrame(), "Black goes first!");
		view.getFrame().setTitle("Black Turn");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String pressedButton = null;
		if(e.getSource() instanceof JButton)
			pressedButton = ((JButton)e.getSource()).getToolTipText();
		else
			return;
		System.out.println(pressedButton + " has been pressed!");
		model.placePiece(Integer.parseInt(pressedButton));
		if(model.isGameOver()){
			switch(model.getWinner()){
			case Model.BLACK_CHARACTER:
				JOptionPane.showMessageDialog(view.getFrame(), "Black Wins!");
				break;
			case Model.WHITE_CHARACTER:
				JOptionPane.showMessageDialog(view.getFrame(), "White Wins!");
				break;
			default:
				JOptionPane.showMessageDialog(view.getFrame(), "It is a tie!");
			}
			view.getFrame().dispatchEvent(new WindowEvent(view.getFrame(), WindowEvent.WINDOW_CLOSING));
			initializeGame();
		}
		switch(model.getTurn()){
		case Model.BLACK_CHARACTER:
			view.getFrame().setTitle("Black Turn");
			break;
		case Model.WHITE_CHARACTER:
			view.getFrame().setTitle("White Turn");
			break;
		default:

		}
	}

	/**
	 * The first heuristic will base the value of the moves on how many pieces will be 
	 * flipped based on that action. This method explores the future possibilities 
	 * using the above heuristic and the minMax search method.
	 */
	private Location minMaxSearchFirstHeuristic(char player, Model localModel, int alpha, int beta, int maxDepth){
		char[][] possibleMoves = localModel.getPossibleMovesBoard();
		ArrayList<Location> nextMoves = new ArrayList<Location>();
		for(int i = 0; i < possibleMoves.length; i++){
			for(int j = 0; j < possibleMoves[i].length; j++){
				if(possibleMoves[i][j] == Model.POSSIBLE_MOVE){
					nextMoves.add(new Location(j, i));
				}
			}
		}

		return null;
	}

	/**
	 * The second heuristic will measure the value of putting a piece in a square based
	 * on that squares proximity to the 1) corners of the board, and 2) the edges of the
	 * board. As the squares move closer to the center of the board, their value decreases.
	 * This method explores the future possibilities using the above heuristic and the
	 * minMax search method.
	 */
	private Location minMaxSearchSecondHeuristic(char player, Model localModel, int alpha, int beta, int maxDepth){
		char[][] possibleMoves = localModel.getPossibleMovesBoard();
		ArrayList<Location> nextMoves = new ArrayList<Location>();
		for(int i = 0; i < possibleMoves.length; i++){
			for(int j = 0; j < possibleMoves[i].length; j++){
				if(possibleMoves[i][j] == Model.POSSIBLE_MOVE){
					nextMoves.add(new Location(j, i));
				}
			}
		}
		int value = -41;
		Location bestLocation = null;
		for(Location location : nextMoves){
			int weightedValue = secondHeuristicWeights[location.getY()][location.getX()];
			System.out.println("Weighted Value: " + weightedValue);
			if(weightedValue > value){
				value = weightedValue;
				bestLocation = location;
			}
		}
		System.out.println("Best Value: " + value);
		return bestLocation;
	}

	/**
	 * The first heuristic will base the value of the moves on how many pieces will be 
	 * flipped based on that action. This method explores the future possibilities 
	 * using the above heuristic and the alphaBeta search method.
	 */
	private Location alphaBetaSearchFirstHeuristic(char player, Model localModel, int alpha, int beta, int maxDepth){
		char[][] possibleMoves = localModel.getPossibleMovesBoard();
		ArrayList<Location> nextMoves = new ArrayList<Location>();
		for(int i = 0; i < possibleMoves.length; i++){
			for(int j = 0; j < possibleMoves[i].length; j++){
				if(possibleMoves[i][j] == Model.POSSIBLE_MOVE){
					nextMoves.add(new Location(j, i));
				}
			}
		}
		return null;
	}

	/**
	 * The second heuristic will measure the value of putting a piece in a square based
	 * on that squares proximity to the 1) corners of the board, and 2) the edges of the
	 * board. As the squares move closer to the center of the board, their value decreases.
	 * This method explores the future possibilities using the above heuristic and the alphaBeta search method.
	 */
	private Location alphaBetaSearchSecondHeuristic(char player, Model localModel, int alpha, int beta, int maxDepth){
		char[][] possibleMoves = localModel.getPossibleMovesBoard();
		ArrayList<Location> nextMoves = new ArrayList<Location>();
		for(int i = 0; i < possibleMoves.length; i++){
			for(int j = 0; j < possibleMoves[i].length; j++){
				if(possibleMoves[i][j] == Model.POSSIBLE_MOVE){
					nextMoves.add(new Location(j, i));
				}
			}
		}
		int value = -41;
		Location bestLocation = null;
		for(Location location : nextMoves){
			int weightedValue = secondHeuristicWeights[location.getY()][location.getX()];
			if(weightedValue > value){
				value = weightedValue;
				bestLocation = location;
			}
		}
		return bestLocation;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyPressed = e.getKeyCode();
		Location bestLocation;
		JButton button;
		int buttonLocation;
		switch(keyPressed){
		case KeyEvent.VK_F1:
			System.out.println("minMaxSearchFirstHeuristic");
			bestLocation = minMaxSearchFirstHeuristic(model.getTurn(), model, 1, 1, 3);
			button = new JButton();
			button.addActionListener(this);
			buttonLocation = bestLocation.getY() * boardSize+bestLocation.getX();
			button.setToolTipText(buttonLocation + "");
			button.doClick();
			break;
		case KeyEvent.VK_F2:
			System.out.println("minMaxSearchSecondHeuristic");
			bestLocation = minMaxSearchSecondHeuristic(model.getTurn(), model, 1, 1, 3);
			button = new JButton();
			button.addActionListener(this);
			buttonLocation = bestLocation.getY() * boardSize+bestLocation.getX();
			button.setToolTipText(buttonLocation + "");
			button.doClick();
			break;
		case KeyEvent.VK_F3:
			System.out.println("alphaBetaSearchFirstHeuristic");
			bestLocation = alphaBetaSearchFirstHeuristic(model.getTurn(), model, 1, 1, 3);
			button = new JButton();
			button.addActionListener(this);
			buttonLocation = bestLocation.getY() * boardSize+bestLocation.getX();
			button.setToolTipText(buttonLocation + "");
			button.doClick();
			break;
		case KeyEvent.VK_F4:
			System.out.println("alphaBetaSearchSecondHeuristic");
			bestLocation = alphaBetaSearchSecondHeuristic(model.getTurn(), model, 1, 1, 3);
			button = new JButton();
			button.addActionListener(this);
			buttonLocation = bestLocation.getY() * boardSize+bestLocation.getX();
			button.setToolTipText(buttonLocation + "");
			button.doClick();
			break;
		default:

		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
