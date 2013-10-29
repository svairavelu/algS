package pat.problems.p1050;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author e518417
 *
 */
public class Main {

	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			char[] xs = br.readLine().toCharArray();
			char[] cs = br.readLine().toCharArray();
			boolean[] bs = new boolean[256];
			for(int i = 0; i < cs.length; i++) {
				int c = cs[i];
				bs[c] = true;
			}
			
			for(int i = 0; i < xs.length; i++) {
				int x = xs[i];
				if(!bs[x]) {
					System.out.printf("%c", x);
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
