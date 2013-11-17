package pat.problems.p1032;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){ 
			String[] xyn = br.readLine().split("\\s+");
			int first = Integer.valueOf(xyn[0]);
			int second = Integer.valueOf(xyn[1]);
			if(first == -1 || second == -1) {
				System.out.println(-1);
				return;
			}
			
			Map<Integer, Integer> map = new HashMap<>();
			int n = Integer.valueOf(xyn[2]);
			for(int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				map.put(Integer.valueOf(line[0]), Integer.valueOf(line[2]));
			}

			int[] xs = new int[n];
			int xsize = 0;
			int x = first;
			while(x > -1) {
				xs[xsize++] = x;
				x = map.get(x);
			}
			
			int[] ys = new int[n];
			int ysize = 0;
			int y = second;
			while(y > -1) {
				ys[ysize++] = y;
				y = map.get(y);
			}
			
			int i = xsize - 1;
			for(int j = ysize - 1; i >= 0 && j >= 0; i--, j--) {
				if(xs[i] != ys[j]) {
					break;
				}
			}
			
			if(i == xsize - 1) {
				System.out.println(-1);
			} else {
				System.out.printf("%05d", xs[i + 1]);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
