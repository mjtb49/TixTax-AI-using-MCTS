import java.util.InputMismatchException;
import java.util.Scanner;

public class BoardTest {

	public static void main(String[] args) {
		Scanner yo = new Scanner(System.in);
		Board thing = new Board();
		while (thing.getResult() == 2) {
			boolean work = false;
			while (work == false) {
				try {
					int x = yo.nextInt();
					int y = yo.nextInt();
					xzPair move = new xzPair(x, y);
					thing.playMove(move);
					work = true;
				} catch (IllegalArgumentException j) {
					work = false;
				} catch (InputMismatchException k) {
					work = false;
				}
			}
			
			Node granddad = new Node(thing);
			thing.printBoard();
			for (int i = 0; i < 50000; i++) {
				granddad.expand();
			}
			System.out.println(granddad.getBestBoard().visits/50000.0);
			//System.out.println(granddad.getBestBoard().move);
			thing.playMove(granddad.getBestBoard().move);
			thing.printBoard();

		}
		yo.close();

	}

}

