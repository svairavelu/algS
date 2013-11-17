package pat.problems.p1006;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String mLine = br.readLine();
			int m = Integer.valueOf(mLine);
			
			String idIn = null;
			String idOut = null;
			int[] inTime = null;
			int[] outTime = null;
			
			for(int i = 0; i < m; i++) {
				String line = br.readLine();
				String[] splitted = line.split("\\s+");
				String id = splitted[0];

				String signinTimeS = splitted[1];
				int[] signinTime = new int[3];
				initTime(signinTimeS.split(":"), signinTime);
				if(inTime == null || earlier(signinTime, inTime)) {
					inTime = signinTime;
					idIn = id;
				}
				
				String signoutTimeS = splitted[2];
				int[] signoutTime = new int[3];
				initTime(signoutTimeS.split(":"), signoutTime);
				if(outTime == null || later(signoutTime, outTime)) {
					outTime = signoutTime;
					idOut = id;
				}
			}
				
			System.out.println(idIn + " " + idOut);
		} finally {
			br.close();
		}
	}
	private static void initTime(String[] strA, int[] timeA) {
		for(int i = 0; i < strA.length; i++) {
			timeA[i] = Integer.valueOf(strA[i]);
		}
	}
	
	private static boolean earlier(int[] x, int[] y) {
		for(int i = 0; i < x.length; i++) {
			if(x[i] < y[i]) {
				return true;
			} else if(x[i] > y[i]) {
				return false;
			} 
		}
		return false;
	}
	
	private static boolean later(int[] x, int[] y) {
		return earlier(y, x);
	}
}
