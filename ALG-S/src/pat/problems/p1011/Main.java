package pat.problems.p1011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class Main {

	private static String[] tags = { "W", "T", "L" };

	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			E e1 = process(br.readLine());
			E e2 = process(br.readLine());
			E e3 = process(br.readLine());
			BigDecimal gain = e1.odd().multiply(e2.odd()).multiply(e3.odd())
					.multiply(new BigDecimal(0.65)).subtract(BigDecimal.ONE)
					.multiply(new BigDecimal(2));
			gain.setScale(2, BigDecimal.ROUND_HALF_UP);
			double got = gain.doubleValue();
			System.out.print(tags[e1.tag] + " ");
			System.out.print(tags[e2.tag] + " ");
			System.out.print(tags[e3.tag] + " ");
			System.out.printf("%.2f\n", got);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static E process(String line) {
		String[] elems = line.split("\\s+");
		E e = null;
		for (int i = 0; i < elems.length; i++) {
			double v = Double.valueOf(elems[i]);
			if (e == null || e.odd < v) {
				e = new E(i, v);
			}
		}
		return e;
	}

	static class E {
		int tag;
		double odd;

		public E(int tag, double odd) {
			super();
			this.tag = tag;
			this.odd = odd;
		}

		public BigDecimal odd() {
			return new BigDecimal(odd);
		}
	}
}
