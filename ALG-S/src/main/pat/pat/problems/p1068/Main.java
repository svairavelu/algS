package pat.problems.p1068;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int n = Integer.valueOf(firstLine[0]);
			int m = Integer.valueOf(firstLine[1]);
			int[] changes = new int[n];
			int size = 0;
			String[] secondLine = br.readLine().split("\\s+");
			for (int i = 0; i < n; i++) {
				int x = Integer.valueOf(secondLine[i]);
				if (x <= m) {
					changes[size++] = x;
				}
			}
			changes = Arrays.copyOf(changes, size);
			// boolean rt = findCoins(coins, m, changes, 0);
			Iterable<Integer> coins = findCoins0(m, changes);
			if (coins != null) {
				Iterator<Integer> iter = coins.iterator();
				System.out.print(iter.next());
				while (iter.hasNext()) {
					System.out.print(" " + iter.next());
				}
				System.out.println();
			} else {
				System.out.println("No Solution");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static Iterable<Integer> findCoins0(int capacity, int[] changes) {
		Stack<Integer> stack = new Stack<>();
		Arrays.sort(changes);
		boolean rt = findCoins(stack, capacity, changes, 0);
		if (rt) {
			return stack;
		} else {
			return null;
		}
	}

	private static boolean findCoins(Stack<Integer> coins, int capacity,
			int[] changes, int pos) {
		if (capacity == 0) {
			return true;
		} else if (capacity < 0) {
			return false;
		} else if (capacity > 0 && pos == changes.length) {
			return false;
		} else {
			int coin = changes[pos];
			coins.push(coin);
			boolean x = findCoins(coins, capacity - coin, changes, pos + 1);
			if (!x) {
				coins.pop();
				if (pos < changes.length - 1 && capacity >= changes[pos + 1]) {
					x = findCoins(coins, capacity, changes, pos + 1);
				}
			}
			return x;
		}
	}

	private static Iterable<Integer> findCoins(int capacity, int[] changes) {
		Arrays.sort(changes);
		Stack<Integer> stack = new Stack<Integer>();
		boolean found = false;
		stack.push(0);
		int left = capacity - changes[0];
		while (!found) {
			int pos = stack.peek();
			if (left == 0) {
				found = true;
			} else if (left < 0 || pos == changes.length - 1) {
				int nxtPos = pos + 1;
				if (nxtPos >= changes.length) {
					int idx = stack.firstElement();
					stack.clear();
					if (idx == changes.length - 1) {
						break;
					} else {
						stack.push(idx + 1);
						left = capacity - changes[idx + 1];
					}
				} else {
					int nxtVal = changes[nxtPos];
					int idx = 0;
					int sum = 0;
					while (sum < nxtVal && !stack.isEmpty()) {
						idx = stack.pop();
						sum += changes[idx];
					}
					stack.push(idx + 1);
					left = left + sum - changes[idx + 1];
				}
			} else {
				stack.push(pos + 1);
				int coin = changes[pos + 1];
				left = left - coin;
			}

		}
		if (found) {
			Stack<Integer> coins = new Stack<Integer>();
			for (Integer idx : stack) {
				coins.push(changes[idx]);
			}
			return coins;
		} else {
			return null;
		}
	}
	
	private static Iterable<Integer> findCoins1(int money, int[] changes) {
		Arrays.sort(changes);;
		int n = changes.length;
		
		int[] c = changes;
		for(int i = 0, j = n - 1; i < j; i++, j--) {
//			c[changes.length - 1 - i] = changes[i];
			int tmp = c[i];
			c[i] = c[j];
			c[j] = tmp;
		}

//		int[][] f = new int[n + 1][money + 1];
		int[] f = new int[money + 1];
		boolean[][] selected = new boolean[n + 1][money + 1];
		
		for(int i = 1; i <= n; i++) {
			int ci = c[i - 1];
//			for(int j = 1; j <= money; j++) {
//				if(j >= ci) {
//					if(f[i - 1][j] <= f[i - 1][j - ci] + ci) {
//						f[i][j] = f[i - 1][j - ci] + ci;
//						selected[i][j] = true;
//					} else {
//						f[i][j] = f[i - 1][j];
//						selected[i][j] = false;
//					}
//				} else {
//					f[i][j] = f[i - 1][j];
//					selected[i][j] = false;
//				}
//			}
			
			for(int j = money; j >= ci; j--) {
				if(f[j] <= f[j - ci] + ci) {
					f[j] = f[j - ci] + ci;
					selected[i][j] = true;
				} 
			}
//			for(int j = money; j >= 1; j--) {
//				if(j >= ci) {
//					if(f[j] < f[j - ci] + ci) {
//						f[j] = f[j - ci] + ci;
//						selected[i][j] = true;
//					} 
//				}
//			}
		}
		
		if(f[money] != money) {
			return null;
		} else {
			List<Integer> coins = new ArrayList<>();
			int tmp = money;
			int nx = n;
			while(tmp > 0) {
				if(selected[nx][tmp]) {
					coins.add(c[nx - 1]);
					tmp -= c[nx - 1];
				}
				nx -= 1;
			}
			
			return coins;
		}
	}
}
