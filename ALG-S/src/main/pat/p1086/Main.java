package p1086;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = Integer.parseInt(scanner.nextLine());
		Node root = new Node("");
		Node[] stack = new Node[n + 1];
		int index = -1;
		stack[++index] = root;
		Node current = root;
		for (int i = 0; i < 2 * n; i++) {
			String[] line = scanner.nextLine().split("\\s+");
			if ("Push".equals(line[0])) {
				Node x = new Node(line[1]);
				stack[++index] = x;
				current.addNode(x);
				current = x;
			} else if ("Pop".equals(line[0])) {
				current = stack[index];
				index--;
			}
		}
		StringBuffer buf = new StringBuffer();
		root.output(buf);

		System.out.println(buf.toString().trim());
	}

	private static class Node {
		final String label;
		Node left, right;

		public Node(String label) {
			this.label = label;
		}

		public void output(StringBuffer buf) {
			if (left != null) {
				left.output(buf);
			}
			if (right != null) {
				right.output(buf);
			}
			buf.append(" ").append(label);
		}

		public void addNode(Node x) {
			if (left == null) {
				left = x;
			} else {
				right = x;
			}
		}

		public String toString() {
			StringBuffer buf = new StringBuffer(label);
			buf.append("{");
			if (left != null) {
				buf.append(left.toString());
			}

			if (right != null) {
				buf.append(right.toString());
			}
			buf.append("}");
			return buf.toString();
		}
	}
}
