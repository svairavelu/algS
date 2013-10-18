package pat.problems.p1016;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			
			String[] line0 = br.readLine().split("\\s+");
			int[] rules = new int[line0.length];
			for(int i = 0; i < rules.length; i++) {
				rules[i] = Integer.valueOf(line0[i]);
			}
			
			int n = Integer.valueOf(br.readLine());
			Map<String, P> ps = new HashMap<String, P>();
			
			for(int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				String name = line[0];
				P p = ps.get(name);
				if(p == null) {
					p = new P(name);
					ps.put(name, p);
				}
				
				p.readT(line[1], line[2]);
			}
			
			P[] px = ps.values().toArray(new P[]{});
//			
//			for(P p : px) {
//				p.process();
//			}
			
			Arrays.sort(px, new Comparator<P>(){
				public int compare(P o1, P o2) {
					return o1.name.compareTo(o2.name);
				}});
			
			for(P p : px) {
				TP[] tps = p.process();
				if(tps.length == 0) {
					continue;
				}
				System.out.println(p.name + " " + p.month);
				int total = 0;
				for(TP tp : tps) {
					int bill = tp.charge(rules);
					total += bill;
					System.out.print(tp.on + " " + tp.off + " " + tp.last());
					System.out.printf(" $%.2f\n", (bill / 100d));
				}
				System.out.printf("Total amount: $%.2f\n", total / 100d);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}

class T implements Comparable<T> {
	final int d, h, m;
	final boolean onOff;
	public T(int d, int h, int m, boolean onOff) {
		super();
		this.d = d;
		this.h = h;
		this.m = m;
		this.onOff = onOff;
	}
	
	private String ts(int t) {
		if(t < 10) {
			return "0" + t;
		} else {
			return "" + t;
		}
	}
	public String toString() {
		return ts(d) + ":" + ts(h) + ":" + ts(m);
	}

	@Override
	public int compareTo(T o) {
		if(d < o.d) {
			return -1;
		} else if(d > o.d) {
			return 1;
		} else {
			if(h < o.h) {
				return -1;
			} else if(h > o.h) {
				return 1;
			} else {
				return m < o.m ? -1 : (o.m > o.m ? 1 : 0);
			}
		}
	}
}

class TP {
	private static final int HOUR = 60;
	private static final int DAY = HOUR * 24;
	final T on;
	final T off;
	public TP(T on, T off) {
		super();
		this.on = on;
		this.off = off;
	}
	
	public int last() {
		int offTime = off.d * DAY + off.h * HOUR + off.m;
		int onTime = on.d * DAY + on.h * HOUR + on.m;
		return offTime - onTime;
	}
	
	public int charge(int[] rules) {
		int amount = 0;
		int offTime = off.d * DAY + off.h * HOUR + off.m;
		int d = on.d, h = on.h, m = on.m;
		int onTime = on.d * DAY + on.h * HOUR + on.m;
		for(int i = onTime; i < offTime; i++) {
			amount += rules[h];
			m = (m + 1) % 60;
			if(m == 0) {
				h = (h + 1) % 24;
				if(h == 0) {
					d += 1;
				}
			}
		}
		
		return amount;
	}
}

class P {
	final String name;
	String month;
	List<T> ts = new ArrayList<T>();
	public P(String name) {
		this.name = name;
	}
	
	public void readT(String l, String type) {
		String[] items = l.split(":");
		this.month = items[0];
		int d = Integer.valueOf(items[1]);
		int h = Integer.valueOf(items[2]);
		int m = Integer.valueOf(items[3]);
		ts.add(new T(d, h, m, type.equals("on-line")));
	}
	public TP[] process() {
		Collections.sort(ts);
			
		List<TP> tps = new ArrayList<TP>();
		for(int i = 0; i < ts.size() - 1;) {
			T t = ts.get(i);
			T nextT = ts.get(i + 1);
			if(t.onOff && !nextT.onOff) {
				tps.add(new TP(t, nextT));
				i = i + 2;
			} else {
				i += 1;
			}
		}
		
		return tps.toArray(new TP[]{});
	}
}
