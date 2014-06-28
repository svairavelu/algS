/**
 * 
 */
package pat.problems.p1077.kuchiguse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Blues
 * 
 */
public class Main {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int n = Integer.parseInt(br.readLine());

			Line[] lines = new Line[n];
			for (int i = 0; i < n; i++) {
				String line = br.readLine();
				lines[i] = new Line(line.length() - 1, line);
			}

			Arrays.sort(lines);

			Line line0 = lines[0];

			while (line0.pos >= 0) {
				char x = line0.current();
				int i = 1;
				for (i = 1; i < n; i++) {
					char y = lines[i].current();
					if (x != y) {
						break;
					}
					lines[i].pos -= 1;
				}
				if (i < n) {
					break;
				}
				line0.pos -= 1;
			}

			if (line0.pos + 1 < line0.line.length()) {
				System.out.println(line0.line.substring(line0.pos + 1));
			} else {
				System.out.println("nai");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	static class Line implements Comparable<Line> {
		int pos;
		final String line;

		public Line(int pos, String line) {
			super();
			this.pos = pos;
			this.line = line;
		}

		public char current() {
			return this.line.charAt(this.pos);
		}

		public int compareTo(Line that) {
			if (that.line.length() > this.line.length()) {
				return -1;
			} else if (that.line.length() < this.line.length()) {
				return 1;
			}
			return 0;
		}

	}
}
