
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		double start = System.currentTimeMillis();
		Scanner init = new Scanner(new File(args[0]));
		PrintStream write = new PrintStream(new File(args[1]));
		String input = init.nextLine();
		input = input.replace("-", " ");
		Scanner read = new Scanner(input);
		ArrayList<Integer> initBoard = new ArrayList<Integer>(9);
		while (read.hasNext()) {
			initBoard.add(read.nextInt());
		}
		if (initBoard.size() > 25) {
			BonusNode root = new BonusNode(initBoard, initBoard.size(), 0);
			bonusBfs(root, write);
		} else {
			Node root = new Node(initBoard, initBoard.size());
			bfs(root, write);
		}
		init.close();
		read.close();
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void bfs(Node root, PrintStream write) {
		Queue<Node> queue = new LinkedList<Node>();
		HashSet<Node> alreadyProcessed = new HashSet<Node>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node current = queue.poll();
			alreadyProcessed.add(current);
			if (current.isFinished()) {
				Stack<String> path = new Stack<String>();
				while (current.parent != null) {
					path.push(current.resultOf);
					current = current.parent;
				}
				while (!path.isEmpty()) {
					write.print(path.pop());
				}
				return;
			} else {
				current.doAllMoves();
				Iterator<Node> it = current.children.iterator();
				for (int i = 0; i < current.children.size(); i++) {
					Node currentChild = it.next();
					if (!alreadyProcessed.contains(currentChild)) {
						queue.add(currentChild);
					}
				}
			}
		}
		write.print("N");
	}

	public static void bonusBfs(BonusNode root, PrintStream write) {
		PriorityQueue<BonusNode> queue = new PriorityQueue<BonusNode>();
		Queue<Integer> depthQueue = new LinkedList<Integer>();
		HashSet<BonusNode> alreadyProcessed = new HashSet<BonusNode>();
		queue.add(root);
		depthQueue.add(0);
		while (!queue.isEmpty()) {
			BonusNode current = queue.poll();
			int depth = depthQueue.poll();
			if (current.isFinished()) {
				Stack<String> path = new Stack<String>();
				while (current.parent != null) {
					path.push(current.resultOf);
					current = current.parent;
				}
				while (!path.isEmpty()) {
					write.print(path.pop());
				}
				return;
			} else {
				current.doAllMoves(depth + 1);
				Iterator<BonusNode> it = current.children.iterator();
				alreadyProcessed.add(current);
				for (int i = 0; i < current.children.size(); i++) {
					BonusNode currentChild = it.next();
					if (!alreadyProcessed.contains(currentChild)) {
						queue.add(currentChild);
						depthQueue.add(depth + 1);
					}
				}
			}
		}
		write.print("N");
	}
}