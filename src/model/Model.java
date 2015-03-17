package model;

import java.util.Observable;

import view.View;

public class Model extends Observable{

	private char[][] board;
	private char[][] possibleMovesBoard;
	private char turn;
	private char winner;
	private int whitePieces, blackPieces;
	private boolean gameOver;

	
	public static int boardSize;
	public static final char EMPTY_CHARACTER = 'E';
	public static final char WHITE_CHARACTER = 'W';
	public static final char BLACK_CHARACTER = 'B';
	public static final char POSSIBLE_MOVE = 'P';
	public static final char IMPOSSIBLE_MOVE = 'I';

	public Model(int boardSize){
		winner = '\0';
		gameOver = false;
		Model.boardSize = boardSize;
		buildBoard();
		print2DArrayToConsole(board);
		turn = BLACK_CHARACTER;
	}
	
	public Model(Model model){
		this.board = model.getBoard();
		this.possibleMovesBoard = model.getPossibleMovesBoard();
		this.turn = model.getTurn();
		this.winner = model.getWinner();
		this.whitePieces = model.getWhitePieces();
		this.blackPieces = model.getBlackPieces();
		this.gameOver = model.isGameOver();
	}

	public char[][] generateAvailableSquaresBasedOnCurrentTurn (){
		char[][] possibleMoves = new char[Model.boardSize][Model.boardSize];
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				possibleMoves[i][j] = IMPOSSIBLE_MOVE;
			}
		}

		char otherColor = getOtherColor();	
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j] == otherColor){
					int k = i;
					int l = j;
					try{
						if(board[i-1][j-1] == EMPTY_CHARACTER){ // top left corner
							k = i;
							l = j;
							do{
								k++;
								l++;
								if(board[k][l] == turn){
									possibleMoves[i-1][j-1] = POSSIBLE_MOVE;
								}
							}while(board[k][l] == otherColor);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					try{
						if(board[i-1][j] == EMPTY_CHARACTER){	// top middle 
							k = i;
							l = j;
							do{
								k++;
								if(board[k][l] == turn){
									possibleMoves[i-1][j] = POSSIBLE_MOVE;
								}
							}while(board[k][l] == otherColor);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					try{
						if(board[i-1][j+1] == EMPTY_CHARACTER){	// top right corner
							k = i;
							l = j;
							do{
								k++;
								l--;
								if(board[k][l] == turn){
									possibleMoves[i-1][j+1] = POSSIBLE_MOVE;
								}
							}while(board[k][l] == otherColor);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					try{
						if(board[i][j-1] == EMPTY_CHARACTER){	// middle left
							k = i;
							l = j;
							do{
								l++;
								if(board[k][l] == turn){
									possibleMoves[i][j-1] = POSSIBLE_MOVE;
								}
							}while(board[k][l] == otherColor);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					try{
						if(board[i][j+1] == EMPTY_CHARACTER){	// middle right
							k = i;
							l = j;
							do{
								l--;
								if(board[k][l] == turn){
									possibleMoves[i][j+1] = POSSIBLE_MOVE;
								}
							}while(board[k][l] == otherColor);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					try{
						if(board[i+1][j-1] == EMPTY_CHARACTER){	// bottom left corner
							k = i;
							l = j;
							do{
								k--;
								l++;
								if(board[k][l] == turn){
									possibleMoves[i+1][j-1] = POSSIBLE_MOVE;
								}
							}while(board[k][l] == otherColor);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					try{
						if(board[i+1][j] == EMPTY_CHARACTER){	// down middle
							k = i;
							l = j;
							do{
								k--;
								if(board[k][l] == turn){
									possibleMoves[i+1][j] = POSSIBLE_MOVE;
								}
							}while(board[k][l] == otherColor);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					try{
						if(board[i+1][j+1] == EMPTY_CHARACTER){	// bottom right corner
							k = i;
							l = j;
							do{
								k--;
								l--;
								if(board[k][l] == turn){
									possibleMoves[i+1][j+1] = POSSIBLE_MOVE;
								}
							}while(board[k][l] == otherColor);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
				}
			}
		}
		this.possibleMovesBoard = possibleMoves;
		return possibleMoves;
	}

	public void flipPieces(int i, int j){
		char otherColor = getOtherColor();	
		int k = i;
		int l = j;
		try{
			if(board[i-1][j-1] == otherColor){ // top left diagonal
				k = i-1;
				l = j-1;
				do{
					k--;
					l--;
					if(board[k][l] == turn){
						while(true){
							board[k][l] = turn;
							k ++;
							l ++;
							if(k == i && l == j){break;}
						}
					}
				}while(board[k][l] == otherColor);
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			if(board[i-1][j] == otherColor){	// upper middle
				k = i-1;
				l = j;
				do{
					k--;
					if(board[k][l] == turn){
						while(true){
							board[k][l] = turn;
							k ++;
							if(k == i && l == j){break;}
						}
					}
				}while(board[k][l] == otherColor);
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			if(board[i-1][j+1] == otherColor){	// top right diagonal
				k = i-1;
				l = j+1;
				do{
					k--;
					l++;
					if(board[k][l] == turn){
						while(true){
							board[k][l] = turn;
							k ++;
							l --;
							if(k == i && l == j){break;}
						}
					}
				}while(board[k][l] == otherColor);
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			if(board[i][j-1] == otherColor){	// middle left
				k = i;
				l = j-1;
				do{
					l--;
					if(board[k][l] == turn){
						while(true){
							board[k][l] = turn;
							l ++;
							if(k == i && l == j){break;}
						}
					}
				}while(board[k][l] == otherColor);
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			if(board[i][j+1] == otherColor){	// middle right
				k = i;
				l = j+1;
				do{
					l++;
					if(board[k][l] == turn){
						while(true){
							board[k][l] = turn;
							l --;
							if(k == i && l == j){break;}
						}
					}
				}while(board[k][l] == otherColor);
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			if(board[i+1][j-1] == otherColor){	// bottom left diagonal
				k = i+1;
				l = j-1;
				do{
					k++;
					l--;
					if(board[k][l] == turn){
						while(true){
							board[k][l] = turn;
							k --;
							l ++;
							if(k == i && l == j){break;}
						}
					}
				}while(board[k][l] == otherColor);
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			if(board[i+1][j] == otherColor){	// down middle
				k = i+1;
				l = j;
				do{
					k++;
					if(board[k][l] == turn){
						while(true){
							board[k][l] = turn;
							k --;
							if(k == i && l == j){break;}
						}
					}
				}while(board[k][l] == otherColor);
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		try{
			if(board[i+1][j+1] == otherColor){	// bottom right diagonal
				k = i+1;
				l = j+1;
				do{
					k++;
					l++;
					if(board[k][l] == turn){
						while(true){
							board[k][l] = turn;
							k --;
							l --;
							if(k == i && l == j){break;}
						}
					}
				}while(board[k][l] == otherColor);
			}
		}catch(ArrayIndexOutOfBoundsException e){}
	}

	public void placePiece(int pressedButton){
		int i = pressedButton/boardSize;
		int j = pressedButton%boardSize;
		board[i][j] = turn;
		flipPieces(i, j);
		changeTurn();
		updateView(board);
		char[][] possibleMoves = generateAvailableSquaresBasedOnCurrentTurn();
		print2DArrayToConsole(possibleMoves);
		print2DArrayToConsole(board);
		updateView(possibleMoves);

		if(whitePieces + blackPieces == boardSize * boardSize || gameOver){
			System.out.println("GAME OVER!");
			gameOver = true;
			if(whitePieces > blackPieces){
				winner = WHITE_CHARACTER;
			}else if(blackPieces > whitePieces){
				winner = BLACK_CHARACTER;
			}//else the winner stays as '\0'
		}
	}

	private char getOtherColor(){
		return (turn == BLACK_CHARACTER) ? WHITE_CHARACTER : BLACK_CHARACTER;
	}

	private void changeTurn(){
		turn = (turn == BLACK_CHARACTER) ? WHITE_CHARACTER : BLACK_CHARACTER;
	}

	private void buildBoard(){
		board = new char[Model.boardSize][Model.boardSize];
		boolean setStartingPiece;
		int whiteStartingPiece = boardSize/2 - 1;
		int blackStartingPiece = boardSize/2;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				setStartingPiece = false;
				if(i == whiteStartingPiece){
					if(j == whiteStartingPiece){
						board[i][j] = WHITE_CHARACTER;
						setStartingPiece = true;
					}
					else if(j == blackStartingPiece){
						board[i][j] = BLACK_CHARACTER;
						setStartingPiece = true;
					}

				}else if(i == blackStartingPiece){
					if(j == whiteStartingPiece){
						board[i][j] = BLACK_CHARACTER;
						setStartingPiece = true;
					}

					else if(j == blackStartingPiece){
						board[i][j] = WHITE_CHARACTER;
						setStartingPiece = true;
					}
				}
				if(!setStartingPiece){
					board[i][j] = EMPTY_CHARACTER;
				}
			}
		}
	}

	public void print2DArrayToConsole(char[][] array){
		System.out.println("CURRENT BOARD LOOKS LIKE: ");
		int blacks = 0, whites = 0, possible = 0, impossible = 0;
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				switch(array[i][j]){
				case Model.BLACK_CHARACTER:
					blacks ++;
					break;
				case Model.WHITE_CHARACTER:
					whites ++;
					break;
				case Model.POSSIBLE_MOVE:
					possible ++;
					break;
				case Model.IMPOSSIBLE_MOVE:
					impossible ++;
					break;
				}
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		if(blacks > 0 || whites > 0){
			System.out.println("Blacks: " + blacks + " Whites: " + whites);
			whitePieces = whites;
			blackPieces = blacks;
		}else{
			System.out.println("Possible moves: " + possible + " Impossible moves: " + impossible);
			if(possible == 0) gameOver = true;
		}
	}

	public void setView(View view){
		this.addObserver(view);
		this.updateView(board);
		this.updateView(this.generateAvailableSquaresBasedOnCurrentTurn());
	}

	public void updateView(char[][] map){
		this.setChanged();
		this.notifyObservers(map);
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public char getWinner() {
		return winner;
	}
	
	public char getTurn() {
		return turn;
	}
	
	public char[][] getPossibleMovesBoard() {
		return possibleMovesBoard;
	}
	
	public char[][] getBoard(){
		return board;
	}
	
	public int getBlackPieces(){
		return blackPieces;
	}
	
	public int getWhitePieces(){
		return whitePieces;
	}
}
