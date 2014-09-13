package poj;

import java.util.PriorityQueue;
import java.util.Scanner;

public class P3253Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int n = cin.nextInt();
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		for (int i = 0; i < n; i++) {
			pq.offer(cin.nextInt());
		}
		long ans = 0;
		while (pq.size() > 1) {
			int a = pq.poll();
			int b = pq.poll();
			ans += (a + b);
			pq.offer(a + b);
		}

		System.out.println(ans);
	}

}
