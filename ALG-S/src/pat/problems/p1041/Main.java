package pat.problems.p1041;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

//		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
//			
//			
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
		
		int[] records = new int[10001];
		int n = readInt();
		int[] xs = new int[n];
		for(int i = 0; i < n; i++) {
			int x = readInt();
			records[x] += 1;
			xs[i] = x;
		}
		
		for(int i = 0; i < n; i++) {
			int x = xs[i];
			if(records[x] == 1) {
				System.out.println(x);
				break;
			}
		}
	}
	
	public static int readInt() throws IOException {
		int x = 0;
		int r = System.in.read();
		while(r != ' ' && r != '\n') {
			x = x * 10 + (r - '0');
			r = System.in.read();
		}
		return x;
	}
}
