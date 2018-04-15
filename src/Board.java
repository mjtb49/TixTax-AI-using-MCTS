import java.util.*;
public class Board {
	private int[][] board;
	private int[][] game;
	private int turn;
	private xzPair activeSector;
	private int isWon=2;
	public Board() {
		turn =1;
		board = new int[9][9];
		game = new int[3][3];
	}
	public int getResult() {
		return isWon;
	}
	public int getTurn() {
		return turn;
	}
	public Board(int[][] position, int turn) {
		this.turn = turn;
		board = position;
	}
	public Board(Board board2) {
		game = new int[3][3];
		board = new int[9][9];
		isWon = board2.isWon;
		turn = board2.turn;
		if (board2.activeSector != null)
			activeSector = new xzPair(board2.activeSector.x,board2.activeSector.y);
		for (int i=0;i<3;i++)
			for (int j=0;j<3;j++)
				game[i][j] = board2.game[i][j];
		for (int i=0;i<9;i++)
			for (int j=0;j<9;j++)
				board[i][j] = board2.board[i][j];;

	}
	public ArrayList<xzPair> getLegalMoves() {
		ArrayList<xzPair> moveList = new ArrayList<xzPair>(0);
		if (activeSector == null) {
			for(int i=0;i<9;i++) {
				for(int j=0;j<9;j++) {
					if (board[i][j]==0) {
					moveList.add(new xzPair(i,j));
					}
				}
			}
		}
		else {
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if (board[i+3*activeSector.x][j+3*activeSector.y]==0) {
					moveList.add(new xzPair(i+3*activeSector.x,j+3*activeSector.y));
					}
				}
			}
		}
		return moveList;
	}
	private boolean sectorViable(xzPair sector) {
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if (board[sector.x*3+i][sector.y*3+j] == 0 )
					return true;
			}
		}
		return false;
	}
	public void playMove(xzPair a) {
		if (activeSector!=null) {
			if (a.getSector().equals(activeSector)) {
				setSquare(a);
				checkSectorForResult(a.getSector());
				turn *= -1;
				if (sectorViable(a.getNextSector())) {
					activeSector = a.getNextSector();
				}
				else
					activeSector=null;
				return;
			}
			System.out.println(activeSector);
			this.printBoard();
			for (xzPair k: this.getLegalMoves())
				System.out.println(k);
			throw new IllegalArgumentException("Wrong Sector");
		}
		setSquare(a);
		checkSectorForResult(a.getSector());
		turn *= -1;
		if (sectorViable(a.getNextSector())) {
			activeSector = a.getNextSector();
		}
	}

	private void checkSectorForResult(xzPair s) {
		//the mother of all if statements
		if (       3==Math.abs(board[3*s.x+0][3*s.y+0]+board[3*s.x+0][3*s.y+1]+board[3*s.x+0][3*s.y+2]) // first column
				|| 3==Math.abs(board[3*s.x+1][3*s.y+0]+board[3*s.x+1][3*s.y+1]+board[3*s.x+1][3*s.y+2]) //second column
				|| 3==Math.abs(board[3*s.x+2][3*s.y+0]+board[3*s.x+2][3*s.y+1]+board[3*s.x+2][3*s.y+2]) //third column
				|| 3==Math.abs(board[3*s.x+2][3*s.y+0]+board[3*s.x+1][3*s.y+1]+board[3*s.x+0][3*s.y+2]) //minor diagonal
				|| 3==Math.abs(board[3*s.x+0][3*s.y+0]+board[3*s.x+1][3*s.y+1]+board[3*s.x+2][3*s.y+2]) //major diagonal
				|| 3==Math.abs(board[3*s.x+0][3*s.y+0]+board[3*s.x+1][3*s.y+0]+board[3*s.x+2][3*s.y+0]) //first row
				|| 3==Math.abs(board[3*s.x+0][3*s.y+1]+board[3*s.x+1][3*s.y+1]+board[3*s.x+2][3*s.y+1]) //second row
				|| 3==Math.abs(board[3*s.x+0][3*s.y+2]+board[3*s.x+1][3*s.y+2]+board[3*s.x+2][3*s.y+2]) //third row
				) {
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					board[3*s.x+i][3*s.y+j] = turn;
				}
			}
			game[s.x][s.y]=turn;
			gameOver();
		}
		else {
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					if (board[3*s.x+i][3*s.y+j]==0)
						return;
				}
			}
			game[s.x][s.y]=6;
			gameOver();
		}
	}
	private void gameOver() {
		if (       3==Math.abs(game[0][0]+game[0][1]+game[0][2]) // first column
				|| 3==Math.abs(game[1][0]+game[1][1]+game[1][2]) //second column
				|| 3==Math.abs(game[2][0]+game[2][1]+game[2][2]) //third column
				|| 3==Math.abs(game[2][0]+game[1][1]+game[0][2]) //minor diagonal
				|| 3==Math.abs(game[0][0]+game[1][1]+game[2][2]) //major diagonal
				|| 3==Math.abs(game[0][0]+game[1][0]+game[2][0]) //first row
				|| 3==Math.abs(game[0][1]+game[1][1]+game[2][1]) //second row
				|| 3==Math.abs(game[0][2]+game[1][2]+game[2][2]) //third row
				) {
			isWon = turn;	
		}
		else {
			for (int[]i:game)
				for(int j:i)
					if (j==0) {return;}
			isWon = 0;
		}
	}
	private void setSquare(xzPair j) {
		if (board[j.x][j.y]==0) {
		board[j.x][j.y]=turn;
		return;
		}
		this.printBoard();
		throw new IllegalArgumentException("alreadyFilledSquare");
	
	}
	private void clearSquare(xzPair j) {
		if (board[j.x][j.y]!=0) {
		board[j.x][j.y]=turn;
		return;
		}
		this.printBoard();
		throw new IllegalArgumentException("Tried To Clear Empty Square");
	
	}
	public void printBoard() {
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				if (board[i][j] == 0)
					System.out.print("_");
				if (board[i][j] == 1)
					System.out.print("X");
				if (board[i][j] == -1)
					System.out.print("O");
				if (j%3==2)
					System.out.print(" ");
			}
			System.out.println("");
			if (i%3==2)
				System.out.println("");
		}
	}
	
}
