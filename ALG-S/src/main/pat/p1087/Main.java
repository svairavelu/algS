package p1087;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		String[] firstLine = cin.nextLine().split("\\s+");
		int n = Integer.parseInt(firstLine[0]);
		int k = Integer.parseInt(firstLine[1]);
		String s = firstLine[2];
		int[] hs = new int[n];
		Map<String, City> map = new HashMap<String, City>();
		City[] array = new City[n];
		hs[0] = 0;
		City scity = new City(s, 0);
		map.put(s, scity);
		array[0] = scity;
		for (int i = 1; i < n; i++) {
			String[] line = cin.nextLine().split("\\s+");
			String y = line[0];
			int h = Integer.parseInt(line[1]);
			hs[i] = h;
			City city = new City(y, i);
			map.put(y, city);
			array[i] = city;
		}

		int[][] matrix = new int[n][n];

		for (int i = 0; i < k; i++) {
			String[] line = cin.nextLine().split("\\s+");
			String x = line[0];
			String y = line[1];
			int cost = Integer.parseInt(line[2]);
			int a = map.get(x).index;
			int b = map.get(y).index;
			matrix[a][b] = cost;
			matrix[b][a] = cost;
		}

		PriorityQueue<City> que = new PriorityQueue<City>();
		que.offer(array[0]);
		array[0].setD(0);
		array[0].setChecked(true);
		int target = map.get("ROM").index;
		while (!que.isEmpty()) {
			City x = que.poll();
			if (x.index == target) {
				break;
			}

			for (int i = 0; i < n; i++) {
				if (x.index == i || array[i].isChecked()) {
					continue;
				}
				int a = matrix[x.index][i];
				if (a == 0) {
					continue;
				}
				City city = array[i];
				city.update(x, a);
				
			}
		}
	}

	private static class City implements Comparable<City> {
		final String name;
		final int index;
		private int d = Integer.MAX_VALUE;
		private City parent;
		private int h;
		private int c;
		private boolean checked;

		public City(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public void update(City x, int a) {
			// TODO Auto-generated method stub
			
		}

		public int getD() {
			return d;
		}

		public void setD(int d) {
			this.d = d;
		}

		public City getParent() {
			return parent;
		}

		public void setParent(City parent) {
			this.parent = parent;
		}

		public int getH() {
			return h;
		}

		public void setH(int h) {
			this.h = h;
		}

		public int getC() {
			return c;
		}

		public void setC(int c) {
			this.c = c;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		@Override
		public int compareTo(City that) {
			if (this.d < that.d) {
				return -1;
			} else if (this.d > that.d) {
				return 1;
			}

			if (this.h > that.h) {
				return -1;
			} else if (this.h < that.h) {
				return 1;
			}

			if (this.c < that.c) {
				return -1;
			} else if (this.c > that.c) {
				return 1;
			}

			return 0;
		}

	}

}
