package pat.problems.p1018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

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
			
			G g = new G(matrix, n + 1);
			BFS bfs = new BFS(g, 0, t, capacity, full);
			int sent = bfs.sent();
			int back = bfs.back();
			int[] path = bfs.path();
			System.out.print(sent + " ");
			int i = path.length - 1;
			System.out.print(path[i]);
			for(i = i - 1; i >= 0; i--) {
				System.out.print("->" + path[i]);
			}
			System.out.println(" " + back);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}


class G {
	private int[][] matrix;
	final int n;
	public G(int[][] matrix, int n) {
		this.matrix = matrix;
		this.n = n;
	}
	
	public int[] adj(int v) {
		return matrix[v];
	}
}

class BFS {
	private G g;
	private int s, t;
	private int[] edgeTo;
	private Node[] nodes;
	int perfect;
	public BFS(G g, int s, int t, int[] capacity, int full) {
		this.g = g;
		this.s = s;
		this.t = t;
		this.edgeTo = new int[g.n];
		this.edgeTo[s] = -1;
		nodes = new Node[g.n];
		for(int i = 0; i < g.n; i++) {
			nodes[i] = new Node(i, Integer.MAX_VALUE, capacity[i]);
		}
		nodes[s].distance = 0;
		this.perfect = full >> 1;
		
		bfs();
	}
	
	
	private void bfs() {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.offer(nodes[s]);
		while(!pq.isEmpty()) {
			Node v = pq.poll();
			if(v.marked) {
				continue;
			}
			
			v.marked = true;
			if(v.index == t) {
				break;
			}
			
			int[] adj = g.adj(v.index);
			for(int i = 0; i < adj.length; i++) {
				Node w = nodes[i];
				if(adj[i] == 0 || w.marked) {
					continue;
				}
				if(v.distance + adj[i] <= w.distance) {
					//a new shortest path found;
					int send = 0, back = 0;
					if(w.capacity > perfect) {
						back = v.back + (w.capacity -perfect);
						send = v.send;
					} else if(w.capacity < perfect) {
						if(v.back < (perfect - w.capacity)) {
							back = 0;
							send = (perfect - w.capacity) - v.back + v.send;
						} else {
							back = v.back - (perfect - w.capacity);
							send = v.send;
						}
					} else {
						send = v.send;
						back = v.back;
					}
					
					if(v.distance + adj[i] < w.distance || (send < w.send || (send == w.send && back > w.back))) {
						//update the new path;
						edgeTo[i] = v.index;
						w.send = send;
						w.back = back;
						w.distance = v.distance + adj[i];
					} 
				} 
				
				pq.offer(w);
			}
		}
	}
	
	public int sent() {
		Node e = nodes[t];
		return e.send;
	}
	
	public int back() {
		Node e = nodes[t];
		return e.back;
	}
	
	public int[] path() {
		int[] path = new int[edgeTo.length];
		int x = t;
		int i = 0;
		while(x != -1) {
			path[i++] = x;
			x = edgeTo[x];
		}
		return Arrays.copyOf(path, i);
	}
	
	class Node implements Comparable<Node>{
		int distance, index, capacity;
		boolean marked;
		int send, back;
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
					+ ", capacity=" + capacity + ", marked=" + marked
					+ ", send=" + send + ", back=" + back + "]";
		}
		
	}
}