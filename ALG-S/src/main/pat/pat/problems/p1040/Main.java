package pat.problems.p1040;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {

		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			char[] cs = br.readLine().toCharArray();
			
			int len = cs.length; 
			for(int l = len; l >= 1; l--) {
				boolean found = false;
				for(int i = 0; i < len - l + 1 && !found; i++) {
					found = symmetric(cs, i, i + l);
				}
				
				if(found) {
					System.out.println(l);
					break;
				}
			}
			
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static boolean symmetric(char[] cs, int start, int end) {
		for(int i = start, j = end - 1; i <= j; i++, j--) {
			if(cs[i] != cs[j]) {
				return false;
			}
		}
		return true;
	}

}
