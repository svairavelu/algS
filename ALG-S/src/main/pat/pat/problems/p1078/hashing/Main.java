/**
 * 
 */
package pat.problems.p1078.hashing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Blues
 * 
 */
public class Main {

	private static int MAX_T_SIZE = 10012;
	private static boolean[] primeNumbers = new boolean[MAX_T_SIZE];

	static {
		initPrimes();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String[] firstLine = br.readLine().split("\\s+");
			int tableSize = nextPrime(Integer.parseInt(firstLine[0]));
			int[] table = new int[tableSize];
			Arrays.fill(table, -1);
			boolean[] checked = new boolean[tableSize];
			String[] line = br.readLine().split("\\s+");
			String sep = "";
			for (int i = 0; i < line.length; i++) {
				int x = Integer.parseInt(line[i]);
				int y = hash(table, tableSize, x, checked);
				if (y >= 0) {
					System.out.print(sep + y);
				} else {
					System.out.print(sep + "-");
				}
				sep = " ";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static int h(int x, int tableSize) {
		return x % tableSize;
	}
	
	
	private static int hash(int[] table, int tableSize, int x, boolean[] checked) {
		Arrays.fill(checked, false);
		x = h(x, tableSize);
		int pos = x;
		int i = 1;
		while (table[pos] >= 0) {
			checked[pos] = true;
			pos = h(x + i * i, tableSize);
			if (checked[pos]) {
				return -1;
			}
			i += 1;
		}
		table[pos] = 1;
		return pos;
	}

	private static void initPrimes() {
		Arrays.fill(primeNumbers, true);
		primeNumbers[0] = false;
		primeNumbers[1] = false;
		primeNumbers[2] = true;
		primeNumbers[3] = true;

		for (int i = 2; i < MAX_T_SIZE; i++) {
			while (i < MAX_T_SIZE && !primeNumbers[i]) {
				i++;
			}

			if (i < MAX_T_SIZE && primeNumbers[i]) {
				for (int j = 2; j * i < MAX_T_SIZE; j++) {
					primeNumbers[i * j] = false;
				}
			}
		}
	}

	private static int nextPrime(int n) {
		for (int i = n; i < MAX_T_SIZE; i++) {
			if (primeNumbers[i]) {
				return i;
			}
		}
		return 0;
	}

}
