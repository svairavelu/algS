package pat.problems.p1026;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			int n = Integer.valueOf(br.readLine());
			Integer[] xs = new Integer[n];
			final Player[] players = new Player[n];
			for (int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				players[i] = new Player(new T(line[0]),
						Integer.valueOf(line[1]), "1".equals(line[2]));
				xs[i] = i;
			}

			Arrays.sort(xs, new Comparator<Integer>() {

				@Override
				public int compare(Integer i, Integer j) {
					Player x = players[i];
					Player y = players[j];
					return x.t.compareTo(y.t);
				}
			});

			String[] tableLine = br.readLine().split("\\s+");
			int k = Integer.valueOf(tableLine[0]);
			int m = Integer.valueOf(tableLine[1]);
			Table[] tables = new Table[k];
			String[] vipTableLine = br.readLine().split("\\s+");
			for (int i = 0; i < k; i++) {
				tables[i] = new Table();
			}
			for (int i = 0; i < m; i++) {
				int x = Integer.valueOf(vipTableLine[i]);
				tables[x - 1].vip = true;
			}

			for (int i = 0; i < n;) {
				int x = xs[i];
				Player player = players[x];

				if (player.startTime > 0) {
					i += 1;
					continue;
				}
				
				int idx = -1, vipIdx = -1;
				int minT = Integer.MAX_VALUE, vipMinT = Integer.MAX_VALUE;

				for (int j = 0; j < k; j++) {
					Table tb = tables[j];
					if (tb.vip) {
						if (tb.time < vipMinT) {
							vipMinT = tb.time;
							vipIdx = j;
						}
					} else {
						if (tb.time < minT) {
							minT = tb.time;
							idx = j;
						}
					}
				}

				if (vipMinT <= minT) {
					Player vipPlayer = null;
					for (int j = i; j < n; j++) {
						Player p = players[xs[j]];
						if (p.vip && p.arriveAt(vipMinT)) {
							vipPlayer = p;
						}
					}

					if (vipPlayer != null) {
						// xs[vipPlayerIdx] = -1;
						vipPlayer.startTime = vipMinT;
						tables[vipIdx].serve(vipPlayer);
//						tables[vipIdx].count += 1;
						continue;
					}
				}

				// then assign the free table to the first player in the queue;
				if (vipMinT < minT || (vipMinT == minT && player.vip)) {
					player.startTime = max(player.t.xtime, vipMinT);
					tables[vipIdx].serve(player);
//					tables[vipIdx].count += 1;
				} else {
					player.startTime = max(player.t.xtime, minT);
					tables[idx].serve(player);
//					tables[idx].count += 1;
				}
				i += 1;
			}

			Arrays.sort(xs, new Comparator<Integer>(){

				@Override
				public int compare(Integer i, Integer j) {
					Player x = players[i];
					Player y = players[j];
					
					if(x.startTime < y.startTime) {
						return -1;
					} else if(x.startTime == y.startTime) {
						return 0;
					} else {
						return 1;
					}
				}});;
			for(int i = 0; i < xs.length; i++) {
				int x = xs[i];
				Player p = players[x];
				if(p.startTime < 21 * 60 * 60) {
					System.out.println(p.t + " " + (new T(p.startTime)) + " " + p.inQueue());
				}
			}
			System.out.print(tables[0].count);
			for(int i = 1; i < k; i++) {
				System.out.print(" " + tables[i].count);
			}
			System.out.println();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static int max(int x, int y) {
		if (x > y) {
			return x;
		} else {
			return y;
		}
	}
}

class T implements Comparable<T> {
	final int[] time;
	final int xtime;

	public T(String s) {
		String[] xs = s.split(":");
		time = new int[3];
		for (int i = 0; i < 3; i++) {
			time[i] = Integer.valueOf(xs[i]);
		}
		xtime = (time[0] * 60 + time[1]) * 60 + time[2];
	}

	public T(int t) {
		this.time = new int[3];
		time[0] = t / 60 / 60;
		time[1] = t / 60 % 60;
		time[2] = t % 60;
		xtime = t;
	}

	public boolean earlier(int time) {
		return xtime <= (time);
	}
	
	public boolean later(int time) {
		return xtime >= time;
	}

	@Override
	public int compareTo(T o) {
		for (int i = 0; i < 3; i++) {
			if (time[i] > o.time[i]) {
				return 1;
			} else if (time[i] < o.time[i]) {
				return -1;
			}
		}

		return 0;
	}

	private static String t(int x) {
		if(x < 10) {
			return "0" + x;
		} else {
			return "" + x;
		}
	}
	@Override
	public String toString() {
		return t(time[0]) + ":" + t(time[1]) + ":" + t(time[2]);
	}
	
	
}

class Player {
	final T t;
	int startTime;
	final int txTime;
	final boolean vip;

	public Player(T t, int txTime, boolean vip) {
		super();
		this.t = t;
		if(txTime < 120) {
			this.txTime = txTime * 60;
		} else {
			this.txTime = 120 * 60;
		}
		this.vip = vip;
	}
	
	public int inQueue() {
		int mins = (this.startTime - this.t.xtime) / 60;
		int seconds = (this.startTime - this.t.xtime) % 60;
		if(seconds >= 30) {
			mins += 1;
		}
		
		return mins;
	}
	
	public boolean arriveAt(int time) {
		return t.earlier(time);
	}
}

class Table {
	boolean vip;

	int count = 0;

	int time = 480 * 60;

	public Table() {
		super();
	}
	
	public void serve(Player p) {
		if(p.startTime < 21 * 60 * 60) {
			this.time = p.startTime + p.txTime;
			this.count += 1;
		}
	}

}