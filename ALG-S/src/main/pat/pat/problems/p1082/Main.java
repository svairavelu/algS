/**
 * 
 */
package pat.problems.p1082;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Blues
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(read(line));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final String[] nums = new String[] { "ling", "yi", "er",
			"san", "si", "wu", "liu", "qi", "ba", "jiu" };

	private static final String[] exps = new String[] { "Yi", "Qian", "Bai",
			"Shi", "", "Qian", "Bai", "Shi", "" };

	private static String read(String line) {
		if (line.charAt(0) == '-') {
			return "Fu " + read(line.substring(1));
		}

		char[] xs = new char[9];
		Arrays.fill(xs, ' ');
		int n = line.length();
		for (int i = 0; i < n; i++) {
			xs[8 - i] = line.charAt(n - 1 - i);
			int j = 8 - i;
			if (j < 8 && xs[j] == '0' && (xs[j + 1] == ' ' || xs[j + 1] == '0')) {
				xs[j] = ' ';
			}
		}

		return doRead(xs, 0, false).trim();
	}

	private static String doRead(char[] xs, int i, boolean start) {
		if(!start && i == 8 && xs[i] == '0') {
			return "ling";
		}
		start = start || (xs[i] != ' ' && xs[i] != '0');
		if(!start) {
			return doRead(xs, i + 1, start);
		}
		
		if (i == 8 && xs[i] == '0') {
			return "";
		} else if (i == 8) {
			return nums[xs[i] - '0'];
		} else if (i == 4 && (xs[i] == '0' || xs[i] == ' ')) {
			return "Wan " + doRead(xs, i + 1, start);
		} else if (i == 4) {
			return nums[xs[i] - '0'] + " Wan " + doRead(xs, i + 1, start);
		} else if (xs[i] == '0') {
			return "ling " + doRead(xs, i + 1, start);
		} else if (xs[i] == ' ') {
			return doRead(xs, i + 1, start);
		} else {
			return nums[xs[i] - '0'] + " " + exps[i] + " " + doRead(xs, i + 1, start);
		}

	}

}
