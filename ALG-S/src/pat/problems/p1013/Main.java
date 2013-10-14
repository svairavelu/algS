package pat.problems.p1013;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] line1 = br.readLine().split("\\s+");
			int n = Integer.valueOf(line1[0]);
			int m = Integer.valueOf(line1[1]);
			int k = Integer.valueOf(line1[2]);
			int[][] matrix = new int[n][n];
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					matrix[i][j] = -1;
				}
			}
			
			for(int i = 0; i < m; i++) {
				String[] line = br.readLine().split("\\s+");
				int x = Integer.valueOf(line[0]) - 1;
				int y = Integer.valueOf(line[1]) - 1;
				matrix[x][y] = 1;
				matrix[y][x] = 1;
			}
			
			Alg alg = new Alg(n, matrix);
			String[] lineK = br.readLine().split("\\s+");
			for(int i = 0; i < k; i++) {
				int x = Integer.valueOf(lineK[i]) - 1;
				int r = alg.check(x);
				System.out.println(r - 1);
			}
				
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}


class Alg {
	private int n;
	private int[][] matrix;
	
	private boolean[] visited;
	
	public Alg(int n, int[][] matrix) {
		this.n = n;
		this.matrix = matrix;
		this.visited = new boolean[n];
		for(int i = 0; i < n; i++) {
			this.visited[i] = false;
		}
	}

	private void dfs(int x) {
		visited[x] = true;
		for(int i = 0; i < n; i++) {
			if(!visited[i] && matrix[x][i] > 0) {
				dfs(i);
			}
		}
	}
	
	public int check(int x) {
		for(int i = 0; i < n; i++) {
			this.visited[i] = false;
		}
		
		int num = 0;
		visited[x] = true;
		for(int i = 0; i < n; i++) {
			if(!visited[i]) {
				dfs(i);
				num += 1;
			}
		}
		return num;
	}
}