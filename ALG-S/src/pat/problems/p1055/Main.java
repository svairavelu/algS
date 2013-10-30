package pat.problems.p1055;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int n = Integer.valueOf(firstLine[0]);
			int k = Integer.valueOf(firstLine[1]);

			KDT t = new KDT(n);
			for (int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				t.insert(line[0], Integer.valueOf(line[1]),
						Integer.valueOf(line[2]));
			}

			for (int i = 0; i < k; i++) {
				String[] line = br.readLine().split("\\s+");
				int m = Integer.valueOf(line[0]);
				int x = Integer.valueOf(line[1]);
				int y = Integer.valueOf(line[2]);
				System.out.println("Case #" + (i + 1) + ":");
				t.findOnKey0(x, y);
				t.printResult(m);;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

class KDT {

	class Node {
		final String name;
		final int[] keys = new int[2];
		Node left, right;
		
		Node(String name, int key0, int key1) {
			this.name = name;
			keys[0] = key0;
			keys[1] = key1;
		}

		int key(int level) {
			return keys[level];
		}

		@Override
		public String toString() {
			return name + " " + keys[0] + " " + keys[1];
		}
	}

	private Node root;
	final int n;
	private Node[] nodes;
	private int idx;
	public KDT(int n) {
		this.n = n;
		this.nodes = new Node[n];
	}
	public void insert(String name, int key0, int key1) {
		this.idx = 0;
		root = insert(root, name, new int[] { key0, key1 }, 0);
	}

	private Node insert(Node node, String name, int[] keys, int level) {
		if (node == null) {
			node = new Node(name, keys[0], keys[1]);
			return node;
		}

		if (keys[level] < node.key(level)) {
			node.left = insert(node.left, name, keys, 1 - level);
		} else {
			// equal or greater go right;
			node.right = insert(node.right, name, keys, 1 - level);
		}

		return node;
	}

	public void findOnKey0(int low, int high) {
		find(new int[] { low, Integer.MIN_VALUE }, new int[] { high,
				Integer.MAX_VALUE });
	}

	public void find(int[] low, int[] high) {
		this.nodes = new Node[n];
		this.idx = 0;
		find(root, low, high, 0);
	}

	private void find(Node node, int[] low, int[] high, int level) {
		if (node == null) {
			return;
		}
		if (low[0] <= node.key(0) && node.key(0) <= high[0]
				&& low[1] <= node.key(1) && node.key(1) <= high[1]) {
			// print(node);
			nodes[idx++] = node;
		}

		// if(key0Rang[0])
		if (low[level] < node.key(level)) {
			find(node.left, low, high, 1 - level);
		}

		if (high[level] >= node.key(level)) {
			find(node.right, low, high, 1 - level);
		}
	}
//
//	private void print(Node node) {
//		System.out.println(node);
//	}
	Comparator<Node> cp = new Comparator<Node>(){

		@Override
		public int compare(Node x, Node y) {
			if(x.key(1) < y.key(1)) {
				return 1;
			} else if(x.key(1) > y.key(1)) {
				return -1;
			} else {
				if(x.key(0) < y.key(0)) {
					return -1;
				} else if(x.key(0) > y.key(0)) {
					return 1;
				} else {
					return x.name.compareTo(y.name);
				}
			}
		}};
	public void printResult(int m) {
		if(idx == 0) {
			System.out.println("None");
			return;
		}
		Arrays.sort(nodes, 0, idx, cp);
		for(int i = 0; i < m && i < idx; i++) {
			System.out.println(nodes[i]);
		}
	}
}
