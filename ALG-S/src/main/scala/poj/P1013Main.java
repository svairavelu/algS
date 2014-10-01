package poj;

import java.util.Scanner;

public class P1013Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int n = Integer.parseInt(cin.nextLine().trim());
		for (int i = 0; i < n; i++) {
			String a = cin.nextLine();
			String b = cin.nextLine();
			String c = cin.nextLine();
			process(a, b, c);
		}
	}

	private static void process(String a, String b, String c) {
		Silver[] ss = new Silver[12];
		for (int i = 0; i < ss.length; i++) {
			ss[i] = new Silver((char) ('A' + i));
		}

	}

	private static void process(String line, Silver[] ss) {
		String[] xs = line.split("\\s+");
		String balance = xs[2];
		if(balance.equals("even")) {
			for(Silver s : ss) {
				s.setState(State.REAL);
			}
		} else if(balance.equals("up")) {
			
		}
	}

	enum State {
		UNKNOWN, LIGHT, REAL, HEAVY
	}

	static class Silver {
		final char x;
		private State state;

		public Silver(char x) {
			this.x = x;
			this.state = State.UNKNOWN;
		}

		public State getState() {
			return this.state;
		}

		public boolean setState(State state) {
			if (this.state == State.UNKNOWN) {
				this.state = state;
				return true;
			}
			return false;
		}
	}

}
