import java.util.*;
public class Node {
	static Board state;
	static Board position;
	private Node parent=null;
	private ArrayList<Node> childArray;
	int turn;
	int visits;
	double score;
	xzPair move;
	public Node(Board board) {
		state = new Board(board);
		position = new Board(board);
		turn = board.getTurn();
		childArray = null;
	}
	public Node(xzPair duud, Node parent) {
		this.move = duud;
		this.parent = parent;
		turn = position.getTurn();
	}
	public double getUCT() {
		if (visits == 0)
			return Integer.MAX_VALUE;
		return (score/visits+1.41*Math.sqrt(Math.log(parent.visits)/(visits)));
	}
	public void expand() {
		if (position.getResult()==2) {
			if (childArray==null) {
				childArray = new ArrayList<Node>(0);
				for (xzPair aMove : position.getLegalMoves()) {
					childArray.add(new Node(aMove, this));
				}
			}
			Node bestUCT = childArray.get(0);
			double bestUCTval = bestUCT.getUCT();
			for (Node m:childArray) {
				if (m.getUCT()>bestUCTval) {
					bestUCT = m;
					bestUCTval = bestUCT.getUCT();
				}
			}
			position.playMove(bestUCT.move);
			bestUCT.expand();
		}
		else {
			this.backpropagate((this.turn*position.getResult()));
			position = new Board(state);
		}	
	}
	public void backpropagate(double score) {
		++visits;
		this.score+=score;
		if (parent!=null) {
			parent.backpropagate(-1*score);
		}
		
	}
	public Node getBestBoard() {
		Node best = childArray.get(0);
		double bestVal = best.visits;
		for (Node m:childArray) {
			if (m.visits>bestVal) {
				best = m;
				bestVal = m.visits;
			}
		}
		return best;
	}
}
