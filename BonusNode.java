
import java.util.ArrayList;
import java.util.Iterator;

public class BonusNode implements Comparable<BonusNode> {
	ArrayList<Integer> board;
	BonusNode parent;
	ArrayList<BonusNode> children = new ArrayList<BonusNode>(4);
	int size;
	int edge;
	String resultOf;
	int depth;
	int manhattan;

	public BonusNode(ArrayList<Integer> board, int size, int depth) {
		this.board = board;
		this.size = size;
		this.edge = (int) Math.sqrt(size);
		this.depth = depth;
		this.manhattan = depth + manhattan();
	}

	void moveUp(int index0, int depth) {
		if ((this.resultOf == null || (this.resultOf != null && !this.resultOf.equals("D"))) && index0 >= edge) {
			ArrayList<Integer> modified = new ArrayList<Integer>(size);
			modified.addAll(board);
			int temp = modified.get(index0 - edge);
			modified.set(index0 - edge, 0);
			modified.set(index0, temp);
			BonusNode child = new BonusNode(modified, size, depth);
			child.resultOf = "U";
			child.parent = this;
			children.add(child);
		}
	}

	void moveRight(int index0, int depth) {
		if ((this.resultOf == null || (this.resultOf != null && !this.resultOf.equals("L")))
				&& index0 % edge != edge - 1) {
			ArrayList<Integer> modified = new ArrayList<Integer>(size);
			modified.addAll(board);
			int temp = modified.get(index0 + 1);
			modified.set(index0 + 1, 0);
			modified.set(index0, temp);
			BonusNode child = new BonusNode(modified, size, depth);
			child.resultOf = "R";
			child.parent = this;
			children.add(child);
		}
	}

	void moveDown(int index0, int depth) {
		if ((this.resultOf == null || (this.resultOf != null && !this.resultOf.equals("U"))) && index0 < size - edge) {
			ArrayList<Integer> modified = new ArrayList<Integer>(size);
			modified.addAll(board);
			int temp = modified.get(index0 + edge);
			modified.set(index0 + edge, 0);
			modified.set(index0, temp);
			BonusNode child = new BonusNode(modified, size, depth);
			child.resultOf = "D";
			child.parent = this;
			children.add(child);
		}
	}

	void moveLeft(int index0, int depth) {
		if ((this.resultOf == null || (this.resultOf != null && !this.resultOf.equals("R"))) && index0 % edge != 0) {
			ArrayList<Integer> modified = new ArrayList<Integer>(size);
			modified.addAll(board);
			int temp = modified.get(index0 - 1);
			modified.set(index0 - 1, 0);
			modified.set(index0, temp);
			BonusNode child = new BonusNode(modified, size, depth);
			child.resultOf = "L";
			child.parent = this;
			children.add(child);
		}
	}

	void doAllMoves(int depth) {
		int index0 = board.indexOf(0);
		moveRight(index0, depth);
		moveDown(index0, depth);
		moveLeft(index0, depth);
		moveUp(index0, depth);
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
		if (!(obj instanceof BonusNode)) {
			return false;
		}
		BonusNode other = (BonusNode) obj;
		return this.board.equals(other.board);
	}

	public int hashCode() {
		return board.hashCode();
	}

	@Override
	public int compareTo(BonusNode other) {
		if (this.manhattan < other.manhattan) {
			return -1;
		} else if (this.manhattan == other.manhattan) {
			return 0;
		} else {
			return 1;
		}
	}

	int manhattan() {
		int manhattan = 0;
		for (int i = 0; i < board.size(); i++) {
			int parsed = board.get(i);
			if (parsed != 0) {
				int currentX = i % edge;
				int currentY = i / edge;
				int targetX = (parsed - 1) % edge;
				int targetY = (parsed - 1) / edge;
				manhattan += Math.abs(currentX - targetX) + Math.abs(currentY - targetY);
			}
		}
		return manhattan;
	}
}
