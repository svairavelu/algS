package pat.problems.p1003;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int n = Integer.valueOf(firstLine[0]);
			int m = Integer.valueOf(firstLine[1]);
			int s = Integer.valueOf(firstLine[2]);
			int t = Integer.valueOf(firstLine[3]);
			String[] secondLine = br.readLine().split("\\s+");
			int[] capacity = new int[n];
			for(int i = 0; i < n; i++) {
				capacity[i] = Integer.valueOf(secondLine[i]);
			}
			
			int[][] g = new int[n][n];
			for(int i = 0; i < m; i++) {
				String[] line = br.readLine().split("\\s+");
				int x = Integer.valueOf(line[0]);
				int y = Integer.valueOf(line[1]);
				int z = Integer.valueOf(line[2]);
				g[x][y] = z;
				g[y][x] = z;
			}
			
			BFS bfs = new BFS(g, s, t, capacity, n);
			System.out.println(bfs.stn() + " " + bfs.take());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}

class BFS {
	private int[][] g;
	private int s, t;
	private int[] capacity;
	private int[] edgeTo;
	private int[] distance;
	private boolean[] marked;
	private int[] take;
	private int[] stn;
	public BFS(int[][] g, int s, int t, int[] capacity, int n) {
		this.g = g;
		this.s = s;
		this.t = t;
		this.capacity = capacity;
		this.edgeTo = new int[n];
		this.distance = new int[n];
		this.marked = new boolean[n];
		this.take = new int[n];
		this.stn = new int[n];
		for(int i = 0; i < n; i++) {
			this.edgeTo[i] = -1;
			this.distance[i] = Integer.MAX_VALUE;
			this.marked[i] = false;
			this.take[i] = 0;
			this.stn[i] = 1;
		}
		this.distance[s] = 0;
		this.take[s] = capacity[s];
		
		bfs();
	}
	
	private void bfs() {
		while(true) {
			int min = Integer.MAX_VALUE;
			int v = -1;
			
			for(int i = 0; i < distance.length; i++) {
				if(!marked[i] && distance[i] < min) {
					min = distance[i];
					v = i;
				}
			}
			
			if(v == -1) {
				break;
			}
			marked[v] = true;
			if(v == t) {
				break;
			}
			
			int[] row = g[v];
			for(int i = 0; i < row.length; i++) {
				if(row[i] == 0 || marked[i]) {
					continue;
				}
				
				if(distance[v] + row[i] <= distance[i]) {
					if(distance[v] + row[i] < distance[i]) {
						stn[i] = stn[v];
					} else {
						stn[i] += stn[v];
					}
					
					distance[i] = distance[v] + row[i];
					int tk = take[v] + capacity[i];
					if(tk > take[i]) {
						take[i] = tk;
						edgeTo[i] = v;
					}
				}
			}
		}
	}
	
	public int stn() {
		if(s == t) {
			return 1;
		} else {
			return stn[t];
		}
	}
	
	public int take() {
		if(s == t) {
			return capacity[s];
		} else {
			return take[t];
		}
	}
}