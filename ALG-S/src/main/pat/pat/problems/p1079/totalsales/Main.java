package pat.problems.p1079.totalsales;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String[] firstLine = br.readLine().split("\\s+");

			int n = Integer.parseInt(firstLine[0]);
			double p = Double.parseDouble(firstLine[1]);
			double r = Double.parseDouble(firstLine[2]) / 100.0 + 1.0;
			Node[] nodes = new Node[n];
			for (int i = 0; i < n; i++) {
				nodes[i] = new Node(i);
			}

			for (int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				int m = Integer.parseInt(line[0]);
				if (m == 0) {
					nodes[i].num = Integer.parseInt(line[1]);
				} else {
					for (int j = 0; j < m; j++) {
						int y = Integer.parseInt(line[j + 1]);
						nodes[y].parent = nodes[i];
					}
				}
			}
			nodes[0].price = p;
			double sum = 0.0;
			for (int i = 0; i < n; i++) {
				if (nodes[i].num > 0) {
					double price = price(nodes[i], r);
					sum += price * nodes[i].num;

				}
			}

			System.out.println(String.format("%.1f", sum));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static double price(Node node, double rate) {
		Stack<Node> stack = new Stack<Node>();
		Node x = node;
		while (x != null && x.price == 0.0) {
			stack.push(x);
			x = x.parent;
		}

		while (!stack.isEmpty()) {
			x = stack.pop();
			x.price = x.parent.price * rate;
		}
		return node.price;
	}

	static class Node {
		public Node parent;
		public int num;
		public double price;

		final int index;

		public Node(int index) {
			this.index = index;
		}

		@Override
		public String toString() {
			return "Node [index=" + index + "]";
		}

	}
}
