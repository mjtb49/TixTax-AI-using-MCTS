import java.util.InputMismatchException;
import java.util.Scanner;

public class BoardTest {

	public static void main(String[] args) {
		Scanner yo = new Scanner(System.in);
		Board thing = new Board();
		Node granddad;
		while (thing.getResult() == 2) {
			/*boolean work = false; //code to play against bot
			while (work == false) {
				try {
					int x = yo.nextInt();
					int y = yo.nextInt();
					xzPair move = new xzPair(x, y);
					thing.playMove(move);
					work = true;
				} catch (IllegalArgumentException | InputMismatchException k) { //haha this doesn't work
					System.out.println(k);
					work = false;
				} 
			}
			*/
			granddad = new Node(thing);
			thing.printBoard();
			int count = 0;
			long n = System.currentTimeMillis();
			while (System.currentTimeMillis()-n < 10000) { //fix memory issues at 25+ seconds
				granddad.expand();
				count++;
			}
			System.out.println(count);
			int visits = granddad.getBestBoard().visits; //this is part of the memory issues
			System.out.println(visits/(double)count);
			System.out.println(granddad.getBestBoard().score/visits);
			//System.out.println(granddad.getBestBoard().move);
			thing.playMove(granddad.getBestBoard().move);
		}
		thing.printBoard();
		yo.close();

	}

}

