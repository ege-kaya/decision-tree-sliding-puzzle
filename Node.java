

import java.util.ArrayList;
import java.util.Iterator;

public class Node {
	ArrayList<Integer> board;
	Node parent;
	ArrayList<Node> children = new ArrayList<Node>(4);
	int size;
	int edge;
	String resultOf;

	public Node(ArrayList<Integer> board, int size) {
		this.board = board;
		this.size = size;
		this.edge = (int) Math.sqrt(size);
	}

	void moveUp(int index0) {
		if (index0 >= edge) {
			ArrayList<Integer> modified = new ArrayList<Integer>(size);
			modified.addAll(board);
			int temp = modified.get(index0 - edge);
			modified.set(index0 - edge, 0);
			modified.set(index0, temp);
			Node child = new Node(modified, size);
			child.resultOf = "U";
			child.parent = this;
			children.add(child);
		}
	}

	void moveRight(int index0) {
		if (index0 % edge != edge - 1) {
			ArrayList<Integer> modified = new ArrayList<Integer>(size);
			modified.addAll(board);
			int temp = modified.get(index0 + 1);
			modified.set(index0 + 1, 0);
			modified.set(index0, temp);
			Node child = new Node(modified, size);
			child.resultOf = "R";
			child.parent = this;
			children.add(child);
		}
	}

	void moveDown(int index0) {
		if (index0 < size - edge) {
			ArrayList<Integer> modified = new ArrayList<Integer>(size);
			modified.addAll(board);
			int temp = modified.get(index0 + edge);
			modified.set(index0 + edge, 0);
			modified.set(index0, temp);
			Node child = new Node(modified, size);
			child.resultOf = "D";
			child.parent = this;
			children.add(child);
		}
	}

	void moveLeft(int index0) {
		if (index0 % edge != 0) {
			ArrayList<Integer> modified = new ArrayList<Integer>(size);
			modified.addAll(board);
			int temp = modified.get(index0 - 1);
			modified.set(index0 - 1, 0);
			modified.set(index0, temp);
			Node child = new Node(modified, size);
			child.resultOf = "L";
			child.parent = this;
			children.add(child);
		}
	}

	void doAllMoves() {
		int index0 = board.indexOf(0);
		moveRight(index0);
		moveDown(index0);
		moveLeft(index0);
		moveUp(index0);
	}

	boolean isFinished() {
		if (board.get(board.size() - 1) != 0) {
			return false;
		}
		Iterator<Integer> it = board.iterator();
		int first = 0;
		int second = 0;
		int count = 0;
		first = it.next();
		while (it.hasNext() && count != size - 2) {
			count++;
			second = it.next();
			if (first > second) {
				return false;
			}
			first = second;
		}
		return true;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		Node other = (Node) obj;
		return this.board.equals(other.board);
	}

	public int hashCode() {
		return board.hashCode();
	}
}
