package pat.problems.p1007;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String kL = br.readLine();
			int k = Integer.valueOf(kL);
			String secondLine = br.readLine();
			String[] splitted = secondLine.split("\\s+");
			boolean allMinus = true;
			int[] nums = new int[k];
			for(int i = 0; i < splitted.length; i++) {
				int num = Integer.valueOf(splitted[i]);
				nums[i] = num;
				if(num >= 0) {
					allMinus = false;
				}
			}
			
			if(allMinus) {
				System.out.println(0 + " " + nums[0] + " " + nums[k - 1]);
			} else {
				long maxSum = -1;
				long thisSum = 0;
				int start = 0;
				int temp = 0;
				int end = 0;
				for(int i = 0; i < k; i++) {
					thisSum += nums[i];
					if(thisSum > maxSum) {
						maxSum = thisSum;
						end = i;
						start = temp;
					} else if(thisSum < 0) {
						thisSum = 0;
						temp = i + 1;
					}
				}
				
				System.out.println(maxSum + " " + nums[start] + " " + nums[end]);
			}
			
		} finally {
			br.close();
		}
	}
}
