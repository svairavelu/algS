package pat.problems.p1080.graduateadmission;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String[] firstLine = br.readLine().split("\\s+");
			int n = Integer.parseInt(firstLine[0]);
			int m = Integer.parseInt(firstLine[1]);
			int k = Integer.parseInt(firstLine[2]);

			String[] secondLine = br.readLine().split("\\s+");
			S[] ss = new S[m];
			for (int i = 0; i < m; i++) {
				ss[i] = new S(Integer.parseInt(secondLine[i]));
			}

			A[] as = new A[n];
			for (int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				int ge = Integer.parseInt(line[0]);
				int gi = Integer.parseInt(line[1]);
				int[] xs = new int[k];
				for (int j = 0; j < k; j++) {
					xs[j] = Integer.parseInt(line[j + 2]);
				}
				as[i] = new A(i, ge, gi, xs);
			}

			process(as, ss);

			for (int i = 0; i < m; i++) {
				System.out.println(ss[i].output());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static void process(A[] as, S[] ss) {
		Arrays.sort(as);
		Queue<A> unchecked = new LinkedList<A>();
		for (int i = as.length - 1; i >= 0; i--) {
			A a = as[i];
			unchecked.offer(a);
		}

		while (!unchecked.isEmpty()) {
			A a = unchecked.poll();
			int[] xs = a.ss;
			for (int i = 0; i < xs.length; i++) {
				int rt = ss[xs[i]].checkAndProcess(a);
				if (rt >= 0) {
					A b = as[rt];
					unchecked.offer(b);
					break;
				} else if (rt == -1) {
					break;
				}

			}
		}
	}

	// school
	static class S {
		final int limit;
		L<A> as = Empty.instance;
		int size;

		public S(int limit) {
			this.limit = limit;
		}

		public int checkAndProcess(A a) {
			if (size < limit || a.compareTo(as.head()) >= 0) {
				as = as.insertAndSort(a);
				size += 1;
				if (as.head().compareTo(a) < 0 && size > limit) {
					A head = as.head();
					as = as.tail();
					size -= 1;
					return head.index;
				}
				return -1;
			}
			return -2;
		}

		public String output() {
			List<A> list = as.collect();
			int[] cs = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				cs[i] = list.get(i).index;
			}
			Arrays.sort(cs);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < cs.length; i++) {
				sb.append(cs[i]).append(" ");
			}
			return sb.toString().trim();
		}
	}

	static class A implements Comparable<A> {
		final int index;
		final int ge, gi;
		final double g;
		final int[] ss;

		public A(int index, int ge, int gi, int[] ss) {
			super();
			this.index = index;
			this.ge = ge;
			this.gi = gi;
			this.g = 1.0 * (ge + gi) / 2;
			this.ss = ss;
		}

		public int compareTo(A that) {
			if (this.g > that.g) {
				return 1;
			} else if (this.g < that.g) {
				return -1;
			} else {
				if (this.ge > that.ge) {
					return 1;
				} else if (this.ge < that.ge) {
					return -1;
				}
				return 0;
			}
		}

		public String toString() {
			return "" + index;
		}

	}

	interface L<T extends Comparable<T>> {
		public L<T> insertAndSort(T x);

		public List<T> collect();

		public L<T> tail();

		public T head();
	}

	static class Empty<T extends Comparable<T>> implements L<T> {
		static Empty instance = new Empty();

		public L<T> insertAndSort(T x) {
			return new LI<T>(x);
		}

		public T head() {
			throw new RuntimeException("Not supported!");
		}

		public L<T> tail() {
			throw new RuntimeException("Not supported!");
		}

		public List<T> collect() {
			return new ArrayList<T>();
		}
	}

	static class LI<T extends Comparable<T>> implements L<T> {
		public L<T> tail;
		public T value;

		public LI(T value) {
			this.value = value;
			this.tail = Empty.instance;
		}

		public LI(T value, L<T> tail) {
			this.value = value;
			this.tail = tail;
		}

		public L<T> insertAndSort(T x) {
			if (x.compareTo(value) <= 0) {
				return new LI<T>(x, this);
			} else {
				this.tail = tail.insertAndSort(x);
				return this;
			}
		}

		public T head() {
			return value;
		}

		public L<T> tail() {
			return tail;
		}

		public List<T> collect() {
			List<T> list = tail.collect();
			list.add(value);
			return list;
		}

	}
}
