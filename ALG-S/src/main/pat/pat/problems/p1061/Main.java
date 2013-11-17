package pat.problems.p1061;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {

		String[] days = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			char[] a = br.readLine().toCharArray();
			char[] b = br.readLine().toCharArray();
			char[] c = br.readLine().toCharArray();
			char[] d = br.readLine().toCharArray();

			char day = '0';
			int i = 0;
			for (; i < a.length && i < b.length; i++) {
				if (a[i] == b[i] && a[i] >= 'A' && a[i] <= 'G') {
					day = a[i];
					break;
				}
			}

			String dayx = days[day - 'A'];

			i += 1;
			int hour = 0;
			for (; i < a.length; i++) {
				if (a[i] == b[i] && (a[i] >= 'A' && a[i] <= 'N')) {
					hour = a[i] - 'A' + 10;
					break;
				} else if (a[i] == b[i] && (a[i] >= '0' && a[i] <= '9')) {
					hour = a[i] - '0';
					break;
				}
			}

			int min = 0;
			for (int k = 0; k < c.length && k < d.length; k++) {
				if ((c[k] >= 'a' && c[k] <= 'z')
						|| (c[k] >= 'A' && c[k] <= 'Z')) {
					if (c[k] == d[k]) {
						min = k;
						break;
					}
				}

			}

			System.out.print(dayx + " ");
			if (hour < 10) {
				System.out.print("0" + hour + ":");
			} else {
				System.out.print(hour + ":");
			}

			if (min < 10) {
				System.out.println("0" + min);
			} else {
				System.out.println(min);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
