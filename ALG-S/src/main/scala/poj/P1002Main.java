package poj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class P1002Main {

	static Map<Character, Character> cache = new HashMap<Character, Character>();

	static {
		cache.put('0', '0');
		cache.put('1', '1');
		cache.put('2', '2');
		cache.put('3', '3');
		cache.put('4', '4');
		cache.put('5', '5');
		cache.put('6', '6');
		cache.put('7', '7');
		cache.put('8', '8');
		cache.put('9', '9');
		cache.put('A', '2');
		cache.put('B', '2');
		cache.put('C', '2');
		cache.put('D', '3');
		cache.put('E', '3');
		cache.put('F', '3');
		cache.put('G', '4');
		cache.put('H', '4');
		cache.put('I', '4');
		cache.put('J', '5');
		cache.put('K', '5');
		cache.put('L', '5');
		cache.put('M', '6');
		cache.put('N', '6');
		cache.put('O', '6');
		cache.put('P', '7');
		cache.put('R', '7');
		cache.put('S', '7');
		cache.put('T', '8');
		cache.put('U', '8');
		cache.put('V', '8');
		cache.put('W', '9');
		cache.put('X', '9');
		cache.put('Y', '9');
	}

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		Map<Integer, Num> map = new HashMap<Integer, Num>();
		int n = Integer.parseInt(cin.nextLine());
		for (int i = 0; i < n; i++) {
			Num x = readNum(cin);
			if (map.containsKey(x.getNumber())) {
				map.get(x.getNumber()).more();
			} else {
				map.put(x.getNumber(), x);
			}
		}

		List<Num> xs = new ArrayList<Num>();
		for(Num x : map.values()) {
			if(x.getCount() > 1) {
				xs.add(x);
			}
		}
		
		if(xs.size() == 0) {
			System.out.println("No duplicates.");
		} else {
			Collections.sort(xs);
			for(Num x : xs) {
				System.out.println(x);
			}
		}
	}

	private static Num readNum(Scanner cin) {
		char[] line = cin.nextLine().toCharArray();
		char[] xs = new char[7];
		int idx = 0;
		for (int i = 0; i < line.length; i++) {
			if (line[i] == '-') {
				continue;
			}
			xs[idx++] = cache.get(line[i]);
		}

		return new Num(xs);
	}

	static class Num implements Comparable<Num> {
		private int number;
		private final char[] xs;
		private int count;

		public Num(char[] xs) {
			this.xs = xs;
			int factor = 1;
			for (int i = xs.length - 1; i >= 0; i--) {
				number += convert(xs[i], factor);
				factor *= 10;
			}
			this.count = 1;
		}

		public int getNumber() {
			return number;
		}

		private int convert(char x, int factor) {
			return (x - '0') * factor;
		}

		public void more() {
			this.count += 1;
		}

		public int getCount() {
			return this.count;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + number;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Num other = (Num) obj;
			if (number != other.number)
				return false;
			return true;
		}

		@Override
		public int compareTo(Num o) {
			if (number < o.number) {
				return -1;
			} else if (number > o.number) {
				return 1;
			} else {
				return 0;
			}
		}

		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append(xs[0]);
			buffer.append(xs[1]);
			buffer.append(xs[2]);
			buffer.append("-");
			buffer.append(xs[3]);
			buffer.append(xs[4]);
			buffer.append(xs[5]);
			buffer.append(xs[6]);
			buffer.append(" ");
			buffer.append(this.count);
			return buffer.toString();
		}
	}
}
