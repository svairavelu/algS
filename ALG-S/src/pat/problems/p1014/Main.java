package pat.problems.p1014;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int n = Integer.valueOf(firstLine[0]);
			int m = Integer.valueOf(firstLine[1]);
			int k = Integer.valueOf(firstLine[2]);
			int q = Integer.valueOf(firstLine[3]);
			Bank bank = new Bank(n, m);
			int[] customers = new int[k];
			String[] secondLine = br.readLine().split("\\s+");
			for (int i = 0; i < k; i++) {
				customers[i] = Integer.valueOf(secondLine[i]);
			}

			bank.open(customers);
			bank.process();
			String[] line3 = br.readLine().split("\\s+");
			for (int i = 0; i < q; i++) {
				int index = Integer.valueOf(line3[i]);
				String res = bank.check(index);
				System.out.println(res);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

class Bank {
	private C[] cs;
	private W[] ws;
	private int n, m;
	public Bank(int n, int m) {
		super();
		this.ws = new W[n];
		for (int i = 0; i < n; i++) {
			ws[i] = new W(m);
		}
		
		this.n = n;
		this.m = m;
	}

	public void open(int[] customers) {
		this.cs = new C[customers.length];
		for (int i = 0; i < cs.length; i++) {
			cs[i] = new C(customers[i]);
		}
	}

	public void process() {
		int currentIndex;
		int wIndex = 0;
		for(currentIndex = 0; currentIndex < n * m && currentIndex < cs.length; currentIndex ++) {
			C c = cs[currentIndex];
			C lastC = ws[wIndex].last();
			ws[wIndex].moreCustomer(cs[currentIndex]);
			if(lastC != null) {
				c.startTime = lastC.endTime;
				c.endTime = lastC.endTime + c.txTime;
			} else {
				c.startTime = 0;
				c.endTime = c.txTime;
			}
			wIndex = (wIndex + 1) % n;
		}
		
		for(int i = currentIndex; i < cs.length; i++) {
			int firstFinishIndex = 0;
			int firstFinishTime = Integer.MAX_VALUE;
			for(int j = 0; j < n; j++) {
				W w = ws[j];
				int time = w.finishTime();
				if(time < firstFinishTime) {
					firstFinishTime = time;
					firstFinishIndex = j;
				}
			}
			C lastC = ws[firstFinishIndex].last();
			C c = cs[i];
			c.startTime = lastC.endTime;
			c.endTime = lastC.endTime + c.txTime;
			ws[firstFinishIndex].serve();
			ws[firstFinishIndex].moreCustomer(c);
		}
	}

	public String check(int index) {
		if (index < 1 || index > cs.length) {
			return "Sorry";
		}

		C c = cs[index - 1];
		return c.leaveTime();
	}

	static final int START_HOUR = 8;
	static final int END_HOUR = 17;

	class C {
		int txTime;
		int endTime;
		int startTime;
		public C(int txTime) {
			super();
			this.txTime = txTime;
		}

		private String str(int x) {
			if (x < 10) {
				return "0" + x;
			} else {
				return "" + x;
			}
		}

		public String leaveTime() {
			int hour = this.startTime / 60;
			if (hour + START_HOUR >= END_HOUR) {
				return "Sorry";
			} else {
				hour = this.endTime / 60;
				int mins = this.endTime % 60;
				return str(hour + START_HOUR) + ":" + str(mins);
			}
		}

		@Override
		public String toString() {
			return "C [txTime=" + txTime + ", endTime=" + endTime + "]";
		}
	}

	class W {

		C[] queue;
		int start, end = -1;
		int m;
		int size;

		public W(int m) {
			this.queue = new C[m];
			this.m = m;
			this.size = 0;
		}

		public boolean hasCapcity() {
			return size < m;
		}

		public boolean hasCapacity() {
			return size < m;
		}

		public boolean moreCustomer(C c) {
			if (hasCapacity()) {
				end = (end + 1) % m;
				queue[end] = c;
				size += 1;
				return true;
			}
			return false;
		}

		public C serve() {
			if (size == 0) {
				return null;
			}

			C c = cs[start];
			start = (start + 1) % m;
			size -= 1;
			return c;
		}

		public int finishTime() {
			if (size == 0) {
				return 0;
			} else {
				return queue[start].endTime;
			}
		}

		public C last() {
			if (size == 0) {
				return null;
			} else {
				return queue[end];
			}
		}
		
		public int size() {
			return size;
		}

	}
}