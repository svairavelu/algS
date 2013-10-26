/**
 * 
 */
package pat.problems.p1034;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Blues
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int n = Integer.valueOf(firstLine[0]);
			int threthold = Integer.valueOf(firstLine[1]);

			Map<String, M> members = new HashMap<>();
			Map<Integer, M> idxx = new HashMap<>();
			UF uf = new UF(2 * n);
			int index = 0;
			for (int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				String a = line[0];
				String b = line[1];
				int wgt = Integer.valueOf(line[2]);

				M ma = null;
				if (!members.containsKey(a)) {
					ma = new M(a, index++);
					ma.weight = wgt;
					idxx.put(ma.index, ma);
					members.put(a, ma);
				} else {
					ma = members.get(a);
					ma.weight += wgt;
				}
				M mb = null;
				if (!members.containsKey(b)) {
					mb = new M(b, index++);
					mb.weight = wgt;
					idxx.put(mb.index, mb);
					members.put(b, mb);
				} else {
					mb = members.get(b);
					mb.weight += wgt;
				}

				uf.union(ma.index, mb.index);
			}

			// M[] ms = members.values().toArray(new M[n]);

			// Arrays.sort(ms);

			Map<Integer, M> roots = new HashMap<>();

			for (int i = 0; i < index; i++) {
				int ri = uf.find(i);
				if (roots.containsKey(ri)) {
					M mr = roots.get(ri);
					M mi = idxx.get(i);
					if (mr.weight >= mi.weight) {
						mr.mems += 1;
						mr.total += mi.weight;
					} else {
						mi.mems = mr.mems + 1;
						mi.total = mr.total + mi.weight;
						roots.put(ri, mi);
					}
				} else {
					M mi = idxx.get(i);
					mi.mems = 1;
					mi.total = mi.weight;
					roots.put(ri, mi);
				}
			}

//			M[] ms = roots.values().toArray(new M[] {});
			M[] ms = new M[index];
			index = 0;
			for(M m : roots.values()) {
				if(m.mems > 2 && m.total > 2 * threthold) {
					ms[index++] = m;
				}
			}

			System.out.println(index);
			
			if(index > 0) {
				Arrays.sort(ms, 0, index, new Comparator<M>() {
					public int compare(M x, M y) {
						return x.name.compareTo(y.name);
					}
				});
				
				for(int i = 0; i < index; i++) {
					M m = ms[i];
					System.out.println(m.name + " " + m.mems);
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

class M implements Comparable<M> {
	final String name;
	int weight;
	int mems;
	final int index;
	int total;

	public M(String name, int index) {
		super();
		this.name = name;
		this.index = index;
	}

	@Override
	public int compareTo(M o) {
		if (weight < o.weight) {
			return 1;
		} else if (weight > o.weight) {
			return -1;
		} else {
			return 0;
		}
	}

}

class UF {
	private int[] xs;

	public UF(int n) {
		this.xs = new int[n];
		for (int i = 0; i < n; i++) {
			xs[i] = i;
		}
	}

	public int find(int x) {
		if (x != xs[x]) {
			xs[x] = find(xs[x]);
		}
		return xs[x];
	}

	public boolean union(int a, int b) {
		int ra = find(a);
		int rb = find(b);
		if (ra != rb) {
			xs[b] = ra;
			return true;
		}

		return false;
	}

	public boolean connected(int a, int b) {
		return find(a) == find(b);
	}

	public int members(int x) {
		int rx = find(x);
		int count = 0;
		for (int i = 0; i < xs.length; i++) {
			int ri = find(i);
			if (rx == ri) {
				count += 1;
			}
		}
		return count;
	}
}