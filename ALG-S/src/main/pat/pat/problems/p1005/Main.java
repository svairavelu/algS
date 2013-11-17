/**
 * 
 */
package pat.problems.p1005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * @author Blues
 * 
 */
public class Main {

	private static final String[] words = { "zero", "one", "two", "three",
			"four", "five", "six", "seven", "eight", "nine" };

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line = br.readLine();
			char[] chars = line.toCharArray();

			int sum = 0;
			for (int i = 0; i < chars.length; i++) {
				sum += chars[i] - '0';
			}
			if (sum == 0) {
				System.out.println(words[0]);
			} else {
				int temp = sum;
				Stack<Integer> stk = new Stack<Integer>();
				while (temp > 0) {
					int left = temp % 10;
					stk.push(left);
					temp = temp / 10;
				}

				StringBuffer sb = new StringBuffer();
				sb.append(words[stk.pop()]);
				while (!stk.isEmpty()) {
					sb.append(" ");
					sb.append(words[stk.pop()]);
				}

				System.out.println(sb.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}

	}

}
