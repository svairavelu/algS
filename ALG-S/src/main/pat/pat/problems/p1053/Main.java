package pat.problems.p1053;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

	public static void main(String[] args) throws IOException {
		int n = readInt();
		int m = readInt();
		int s = readInt();
		int[] ws = new int[n];
		for(int i = 0; i < n; i++) {
			ws[i] = readInt();
		}
		
		Tree tree = new Tree(n, ws);
		for(int i = 0; i < m; i++) {
			int nx = readInt();
			int size = readInt();
			Integer[] cs = new Integer[size];
			for(int j = 0; j < size; j++) {
				cs[j] = readInt();
			}
			tree.link(nx, size, cs);
		}
		tree.find(s);
	}

	private static int readInt() throws IOException {
		InputStream in = System.in;
		int s = 0;
		int c = in.read();
		while(c != ' ' && c != '\r' && c != '\n') {
			s = s * 10 + (c - '0');
			c = in.read();
		}
		
		if(c == '\r') {
			in.read();
		}
		return s;
	}
}

class Tree {
	class Node {
		final int label;
		final int weight;
		Integer[] children;
		public Node(int label, int weight) {
			super();
			this.label = label;
			this.weight = weight;
		}
	}
	final int n;
	final int[] ws;
	final Node[] nodes;
	public Tree(int n, int[] ws) {
		super();
		this.n = n;
		this.ws = ws;
		this.nodes = new Node[n];
		for(int i = 0; i < n; i++) {
			nodes[i] = new Node(i, ws[i]);
		}
	}
	
	public void link(int nx, int size, Integer[] cx) {
		nodes[nx].children = cx;
	}
	
	private void printPath(int[] path, int size) {
		System.out.printf("%2d", nodes[path[0]].weight);
		for(int i = 1; i < size; i++) {
			System.out.printf(" %2d", nodes[path[i]].weight);
		}
		System.out.println();
	}
	
	private void find(int nx, int s, int[] path, int pos) {
		Node node = nodes[nx];
		path[pos] = nx;
		if(node.weight == s && node.children == null) {
			//find one path
			printPath(path, pos + 1);
			return;
		} else if(node.weight > s) {
			//a wrong path;
			return;
		} else if(node.weight < s && node.children != null) {
			//sort its children according to the weight;
			Arrays.sort(node.children, new Comparator<Integer>(){

				@Override
				public int compare(Integer i, Integer j) {
					Node x = nodes[i];
					Node y = nodes[j];
					if(x.weight > y.weight) {
						return -1;
					} else if(x.weight < y.weight) {
						return 1;
					}
					return 0;
				}});
			
			for(int nc : node.children) {
				find(nc, s - node.weight, path, pos + 1);
			}
		}
	}
	
	public void find(int s) {
		find(0, s, new int[n], 0);
	}
}
