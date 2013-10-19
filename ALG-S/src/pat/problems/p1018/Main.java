package pat.problems.p1018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int full = Integer.valueOf(firstLine[0]);
			int n = Integer.valueOf(firstLine[1]);
			int t = Integer.valueOf(firstLine[2]);
			int m = Integer.valueOf(firstLine[3]);
			
			int[] capacity = new int[n + 1];
			String[] secondLine = br.readLine().split("\\s+");
			for(int i = 1; i <= n; i++) {
				capacity[i] = Integer.valueOf(secondLine[i - 1]);
			}
			
			int[][] matrix = new int[n + 1][n + 1];
			for(int i = 0; i < n + 1; i++) {
				for(int j = 0; j < n + 1; j++) {
					matrix[i][j] = 0;
				}
			}
			
			for(int i = 0; i < m; i++) {
				String[] line = br.readLine().split("\\s+");
				int v = Integer.valueOf(line[0]);
				int w = Integer.valueOf(line[1]);
				int l = Integer.valueOf(line[2]);
				matrix[v][w] = l;
				matrix[w][v] = l;
			}
			
//			G g = new G(matrix, n + 1);
			BFS bfs = new BFS(matrix, n + 1, 0, t, capacity, full);
			int sent = bfs.sent();
			int back = bfs.back();
			int[] path = bfs.path();
			System.out.print(sent + " ");
			System.out.print(path[0]);
			for(int i = 1; i < path.length; i++) {
				System.out.print("->" + path[i]);
			}
			System.out.println(" " + back);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}

class BFS {
	private int[][] g;
	private int n;
	private int s, t;
	private int[][] pc; //parent-child relationship if pc[i][j], 1 for j is i's child, 0 for no relation, -1 for i is j's parent 
	private Node[] nodes;
	private int[] path;
	int perfect;
	int sent = Integer.MAX_VALUE, back;
	public BFS(int[][] g, int n, int s, int t, int[] capacity, int full) {
		this.g = g;
		this.n = n;
		this.s = s;
		this.t = t;
		nodes = new Node[n];
		for(int i = 0; i < n; i++) {
			nodes[i] = new Node(i, Integer.MAX_VALUE, capacity[i]);
		}
		nodes[this.s].distance = 0;
		this.perfect = full >> 1;
		nodes[this.s].capacity = perfect;
		this.pc = new int[n][n];
		this.path = new int[n];
		bfs();
		dfs(s, 0, 0, new int[n], 0);
	}
	
	
	private void bfs() {
		while(true) {
			//find the nearest node
			Node v = null;
			int minDistance = Integer.MAX_VALUE;
			for(int i = 0; i < n; i++) {
				Node node = nodes[i];
				if(!node.marked && node.distance < minDistance) {
					minDistance = node.distance;
					v = node;
				}
			}
			
			if(v == null) {
				break;
			}
			
			if(v.index == t) {
				break;
			}
			v.marked = true;
			int[] adj = g[v.index];
			for(int i = 0; i < adj.length; i++) {
				if(nodes[i].marked || g[v.index][i] == 0) {
					continue;
				}
				
				Node w = nodes[i];
				if(v.distance + adj[i] <= w.distance) {
					if(v.distance + adj[i] < w.distance) {
						int[] ps = pc[i];
						for(int k = 0; k < ps.length; k++) {
							if(ps[k] < 0) {
								//i is k's child
								pc[i][k] = 0;
								pc[k][i] = 0;
								//then no relation
							}
						}
					}
					pc[v.index][i] = 1;
					pc[i][v.index] = -1;
					w.distance = v.distance + adj[i];
					//update the relation
				} 
			}
		}
	}
	
	private void dfs(final int x, final int sent, final int back, final int[] path, final int pos) {
		path[pos] = x;
		Node v = nodes[x];
		int sentToNext = 0;
		int backToNext = 0;
		if(back + v.capacity < perfect) {
			backToNext = 0;
			sentToNext = sent + (perfect - (back + v.capacity));
		} else if(back + v.capacity >= perfect) {
			backToNext = (back + v.capacity - perfect);
			sentToNext = sent;
		}
		
		if(x == t) {
			if(sentToNext < this.sent || (sentToNext == this.sent && backToNext < this.back)) {
				this.sent = sentToNext;
				this.back = backToNext;
				this.path = Arrays.copyOf(path, pos + 1);
			}
		}
		
		
		int[] children = pc[x];
		for(int i = 0; i < children.length; i++) {
			if(children[i] > 0) {
				dfs(i, sentToNext, backToNext, path, pos + 1);
			}
		}
	}
	
	public int sent() {
		return sent;
	}
	
	public int back() {
		return back;
	}
	
	public int[] path() {
		return this.path;
	}
	
	class Node implements Comparable<Node>{
		int distance, index, capacity;
		boolean marked;
		public Node(int index, int distance, int capacity) {
			this.distance = distance;
			this.index = index;
			this.capacity = capacity;
		}
		@Override
		public int compareTo(Node o) {
			if(this.distance == o.distance) {
				return 0;
			} else if(this.distance > o.distance) {
				return 1;
			} else {
				return -1;
			}
		}
		@Override
		public String toString() {
			return "Node [distance=" + distance + ", index=" + index
					+ ", capacity=" + capacity + ", marked=" + marked + "]";
		}
	}
}