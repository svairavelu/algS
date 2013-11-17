package pat.problems.p1063;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader((new InputStreamReader(System.in)))) {
			int n = Integer.valueOf(br.readLine());
			Set[] sets = new Set[n + 1];
			for(int i = 1; i < n + 1; i++) {
				sets[i] = toSet(br.readLine());
			}
			
			int k = Integer.valueOf(br.readLine());
			for(int i = 0; i < k; i++) {
				String[] line = br.readLine().split("\\s+");
				int x = Integer.valueOf(line[0]);
				int y = Integer.valueOf(line[1]);
				
				double r = check(sets[x], sets[y]) * 100;
				System.out.printf("%.1f", r);
				System.out.println("%");
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private static Set toSet(String s) {
		String[] xs = s.split("\\s+");
		int[] ys = new int[Integer.valueOf(xs[0])];
		for(int i = 1; i < xs.length; i++) {
			ys[i - 1] = Integer.valueOf(xs[i]);
		}
		return new Set(ys);
	}
	
	private static double check(Set x, Set y) {
		int[] xs = x.xs;
		int[] ys = y.xs;
		
		int count = 0;
		for(int i = 0, j = 0; i < xs.length && j < ys.length;) {
			if(xs[i] == ys[j]) {
				count += 1;
				i += 1;
				j += 1;
			} else if(xs[i] < ys[j]) {
				i += 1;
			} else {
				j += 1;
			}
		}
		return count * 1.0 / (xs.length + ys.length - count);
	}
}

class Set {
	int[] xs;
	
	public Set(int[] xs) {
		this.xs = xs;
		init();
	}
	
	private void init() {
		Arrays.sort(this.xs);
		int size = 0;
		for(int i = 0; i < xs.length; ) {
			xs[size++] = xs[i];
			while(++i < xs.length && xs[i] == xs[size - 1]) {
				
			}
		}
		
		xs = Arrays.copyOf(xs, size);
	}
}


