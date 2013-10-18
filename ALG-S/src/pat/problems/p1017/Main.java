/**
 * 
 */
package pat.problems.p1017;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

/**
 * @author Blues
 * 
 */
public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			String[] line0 = br.readLine().split("\\s+");
			int n = Integer.valueOf(line0[0]);
			int k = Integer.valueOf(line0[1]);
			Bank bank = new Bank(k);

			C[] cs = new C[n];
			for (int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				cs[i] = new C(line[0], Integer.valueOf(line[1]));
			}

			Arrays.sort(cs);

			bank.serve(cs);

			int total = 0;
			int totalWaitingTime = 0; // in seconds;
			for (int i = 0; i < n; i++) {
				C c = cs[i];
				if (!c.before17()) {
					break;
				}
				total += 1;
				totalWaitingTime += c.waitingTimeInSeconds();
			}
			if (total > 0) {
				MathContext mc = new MathContext(4);
				BigDecimal averageWaitingTime = new BigDecimal(totalWaitingTime)
						.divide(new BigDecimal(60 * total), mc);
				System.out.printf("%.1f\n", averageWaitingTime.doubleValue());
			} else {
				System.out.println("0.0");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

class C implements Comparable<C> {

	private int[] arriveTime;

	final int txTime;
	int startTime = -1;

	public C(String ts, int txTime) {
		String[] parts = ts.split(":");
		arriveTime = new int[parts.length];
		for (int i = 0; i < parts.length; i++) {
			arriveTime[i] = Integer.valueOf(parts[i]);
		}
		this.txTime = txTime;
	}

	public boolean before17() {
		return arriveTime[0] < 17
				|| (arriveTime[0] == 17 && arriveTime[1] == 0 && arriveTime[2] == 0);
	}

	public int arriveTime() {
		return arriveTime[0] * 60 * 60 + arriveTime[1] * 60 + arriveTime[2];
	}

	public int waitingTimeInSeconds() {
		return startTime - arriveTime();
	}

	@Override
	public int compareTo(C o) {
		for (int i = 0; i < arriveTime.length; i++) {
			int t1 = this.arriveTime[i];
			int t2 = o.arriveTime[i];
			if (t1 < t2) {
				return -1;
			} else if (t1 > t2) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return "C [arriveTime=" + Arrays.toString(arriveTime) + ", txTime="
				+ txTime + ", startTime=" + (startTime) + "]";
	}
}

class Bank {
	private W[] ws;

	public Bank(int k) {
		ws = new W[k];
		for (int i = 0; i < k; i++) {
			ws[i] = new W();
		}
	}

	private int max(int x, int y) {
		if (x > y) {
			return x;
		} else {
			return y;
		}
	}

	public void serve(C[] cs) {
		for (int i = 0; i < cs.length; i++) {
			if(!cs[i].before17()) {
				break;
			}
			int minFinishTime = Integer.MAX_VALUE;
			int minFinishIndex = -1;
			for (int j = 0; j < ws.length; j++) {
				W w = ws[j];
				if (w.finishTime < minFinishTime) {
					minFinishTime = w.finishTime;
					minFinishIndex = j;
				}
			}

			W minW = ws[minFinishIndex];
			cs[i].startTime = max(minW.finishTime, cs[i].arriveTime());
			minW.finishTime = cs[i].startTime + cs[i].txTime * 60;
		}
	}

	class W {
		int finishTime = 8 * 60 * 60;
	}

}