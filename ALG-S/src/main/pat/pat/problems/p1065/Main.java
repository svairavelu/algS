package pat.problems.p1065;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			int n = Integer.valueOf(br.readLine());
			for(int i = 0; i < n; i++) {
				String[] abc = br.readLine().split("\\s+");
				String a = abc[0];
				String b = abc[1];
				String c = abc[2];
				BigDecimal ax = new BigDecimal(a);
				BigDecimal bx = new BigDecimal(b);
				BigDecimal cx = new BigDecimal(c);
				int rt = ax.add(bx).compareTo(cx);
				if(rt > 0) {
					System.out.println("Case #" + (i + 1)  + ": true");
				} else {
					System.out.println("Case #" + (i + 1)  + ": false");
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
