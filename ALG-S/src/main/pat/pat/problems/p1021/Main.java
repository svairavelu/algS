package pat.problems.p1021;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			int n = Integer.valueOf(br.readLine());
			Node[] nodes = new Node[n];
			for (int i = 0; i < n; i++) {
				nodes[i] = new Node(i + 1, n);
			}
			UF uf = new UF(n);
			boolean acyclic = true;
			for (int i = 0; i < n - 1; i++) {
				String[] line = br.readLine().split("\\s+");
				int s = Integer.valueOf(line[0]) - 1;
				int t = Integer.valueOf(line[1]) - 1;
				nodes[s].addEdge(t);
				nodes[t].addEdge(s);
				if (!uf.union(s, t)) {
					acyclic = false;
				}
			}

			int cnum = uf.num();
			if (acyclic == false || cnum > 1) {
				System.out.printf("Error: %d components\n", cnum);
			} else {
				Solution2 solution = new Solution2(n, nodes);
				int[] result = solution.result();
				for (int i : result) {
					System.out.println(i);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

class Node {
	final int index;
	private int[] edges;
	private int size = 0;
	int depth;

	public Node(int index, int n) {
		this.index = index;
		this.edges = new int[n];
	}

	public void addEdge(int x) {
		this.edges[size++] = x;
	}

	public int[] adj() {
		return Arrays.copyOf(edges, size);
	}

	@Override
	public String toString() {
		return "Node [index=" + index + ", size=" + size + ", depth=" + depth
				+ "]";
	}
}

class Solution {
	private Node[] nodes;
	private int n;

	public Solution(int n, Node[] nodes) {
		this.nodes = nodes;
		this.n = n;
		process();
	}

	private int dfs(int p, int x) {
		Node v = nodes[x];
		int[] adj = v.adj();
		if (p != x && adj.length == 1) {
			return 1;
		} else {
			int maxH = 0;
			for (int y : adj) {
				if (y != p) {
					int h = dfs(x, y);
					if (h > maxH) {
						maxH = h;
					}
				}
			}
			return maxH + 1;
		}
	}

	private void process() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].depth = dfs(i, i);
		}

		Arrays.sort(nodes, new Comparator<Node>() {

			@Override
			public int compare(Node x, Node y) {
				if (x.depth > y.depth) {
					return -1;
				} else if (x.depth == y.depth) {
					return 0;
				} else {
					return 1;
				}
			}
		});
	}

	public int[] result() {
		int[] rs = new int[n];
		int max = nodes[0].depth;
		int index = 0;
		for (int i = 0; i < n; i++) {
			if (nodes[i].depth < max) {
				break;
			}

			rs[index++] = nodes[i].index;
		}
		return Arrays.copyOf(rs, index);
	}
}

class Solution2 {
	private Node[] nodes;
	private int n;

	public Solution2(int n, Node[] nodes) {
		this.nodes = nodes;
		this.n = n;
		process();
	}

	private void process() {
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(0);

		boolean[] marked = new boolean[n];
		int x = 0;
		while (!queue.isEmpty()) {
			x = queue.poll();
			marked[x] = true;
			Node v = nodes[x];
			int[] adj = v.adj();
			for (int i = 0; i < adj.length; i++) {
				int y = adj[i];
				if (marked[y]) {
					continue; // parent path
				}
				queue.offer(y);
			}
		}

		// x is the farthest node from 0;

		queue.clear();
		queue.offer(x);
		int maxDepth = 0;
		marked = new boolean[n];
		while (!queue.isEmpty()) {
			int nx = queue.poll();
			marked[nx] = true;
			Node v = nodes[nx];
			if (v.depth > maxDepth) {
				maxDepth = v.depth;
			}
			int[] adj = v.adj();
			for (int i = 0; i < adj.length; i++) {
				int y = adj[i];
				if (marked[y]) {
					continue; // parent path
				}
				queue.offer(y);
				nodes[y].depth = v.depth + 1;
			}
		}
		
		nodes[x].depth = maxDepth;
		Arrays.sort(nodes, new Comparator<Node>() {

			@Override
			public int compare(Node x, Node y) {
				if (x.depth > y.depth) {
					return -1;
				} else if (x.depth == y.depth) {
					return 0;
				} else {
					return 1;
				}
			}
		});
	}

	public int[] result() {
		int[] rs = new int[n];
		int max = nodes[0].depth;
		int index = 0;
		for (int i = 0; i < n; i++) {
			if (nodes[i].depth < max) {
				break;
			}

			rs[index++] = nodes[i].index;
		}
		return Arrays.copyOf(rs, index);
	}
}

class UF {
	private int[] ns;

	public UF(int n) {
		this.ns = new int[n];
		for (int i = 0; i < ns.length; i++) {
			ns[i] = i;
		}
	}

	private int find(int x) {
		if (ns[x] != x) {
			ns[x] = find(ns[x]);
			return ns[x];
		} else {
			return x;
		}
	}

	public boolean union(int x, int y) {
		int rootx = find(x);
		int rooty = find(y);
		if (rootx == rooty) {
			// one cycle generated
			return false;
		} else {
			ns[rooty] = rootx;
			return true;
		}
	}

	public int num() {
		int x = 0;
		for (int i = 0; i < ns.length; i++) {
			if (ns[i] == i) {
				x += 1;
			}
		}
		return x;
	}
}
