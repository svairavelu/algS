package pat.probolems.p1030;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int n = Integer.valueOf(firstLine[0]);
			int m = Integer.valueOf(firstLine[1]);
			int s = Integer.valueOf(firstLine[2]);
			int t = Integer.valueOf(firstLine[3]);
			int[][] paths = new int[n][n];
			int[][] costs = new int[n][n];
			
			for(int i = 0;i < m; i++) {
				String[] line = br.readLine().split("\\s+");
				int x = Integer.valueOf(line[0]);
				int y = Integer.valueOf(line[1]);
				int d = Integer.valueOf(line[2]);
				int c = Integer.valueOf(line[3]);
				paths[x][y] = d;
				paths[y][x] = d;
				costs[x][y] = c;
				costs[y][x] = c;
			}
			
			Travel plan = new Travel(n, paths, costs, s, t);
			int[] p = plan.path();
			for(int i = p.length - 1; i >= 0; i--) {
				System.out.print(p[i] + " ");
			}
			
			System.out.print(plan.dist() + " ");
			System.out.println(plan.cost());
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}

class Travel {
	private int n;
	private int[][] paths;
	private int[][] costs;
	private int s, t;
	
	private int[] dist, cost, edgeTo;
	
	public Travel(int n, int[][] paths, int[][] costs, int s, int t) {
		this.n = n;
		this.paths = paths;
		this.costs = costs;
		this.s = s;
		this.t = t;
		this.dist = new int[n];
		this.edgeTo = new int[n];
		edgeTo[s] = -1;
		this.cost = new int[n];
		for(int i = 0; i < n; i++) {
			dist[i] = Integer.MAX_VALUE;
			cost[i] = Integer.MAX_VALUE;
		}
		dist[s] = 0;
		cost[s] = 0;
		dijikstra();
	}
	
	private void dijikstra() {
		boolean[] marked = new boolean[n];
		while(true) {
			int x = -1;
			int minDist = Integer.MAX_VALUE;
			for(int i = 0; i < n; i++) {
				if(!marked[i] && dist[i] < minDist) {
					minDist = dist[i];
					x = i;
				}
			}
			if(x == -1 || x == t) {
				break;
			}
			
			marked[x] = true;
			
			int[] adj = paths[x];
			for(int i = 0; i < n; i++) {
				if(adj[i] > 0 && !marked[i]) {
					if(dist[x] + adj[i] <= dist[i]) {
						if(dist[x] + adj[i] < dist[i] || cost[x] + costs[x][i] < cost[i]) {
							dist[i] = dist[x] + adj[i];
							edgeTo[i] = x;
							cost[i] = cost[x] + costs[x][i];
						}
					}
				}
			}
			
		}
	}
	
	public int[] path() {
		int[] p = new int[n];
		int idx = 0;
		int x = t;
		while(x != -1) {
			p[idx++] = x;
			x = edgeTo[x];
		}
		
		return Arrays.copyOf(p, idx);
	}
	
	public int dist() {
		return dist[t];
	}
	
	public int cost() {
		return cost[t];
	}
}
