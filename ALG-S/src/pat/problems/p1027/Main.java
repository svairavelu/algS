package pat.problems.p1027;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	private static final String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C"};

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] rgb = br.readLine().split("\\s+");
			int r = Integer.valueOf(rgb[0]);
			int g = Integer.valueOf(rgb[1]);
			int b = Integer.valueOf(rgb[2]);
			
			System.out.println("#" + convert(r) + convert(g) + convert(b));
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String convert(int x) {
		int left = x % 13;
		int divided = x / 13;
		return numbers[divided] + numbers[left];
	}
}
