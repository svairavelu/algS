package pat.problems.p1068;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int n = Integer.valueOf(firstLine[0]);
			int m = Integer.valueOf(firstLine[1]);
			int[] changes = new int[n];
			String[] secondLine = br.readLine().split("\\s+");
			for(int i = 0; i < n; i++) {
				changes[i] = Integer.valueOf(secondLine[i]);
			}
			
			Arrays.sort(changes);
			
//			boolean rt = findCoins(coins, m, changes, 0);
			Iterable<Integer> coins = findCoins(m, changes);
			if(coins != null) {
				Iterator<Integer> iter = coins.iterator();
				System.out.print(iter.next());
				while(iter.hasNext()) {
					System.out.print(" " + iter.next());
				}
				System.out.println();
			} else {
				System.out.println("No Solution");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	
	private static Iterable<Integer> findCoins0(int capacity, int[] changes) {
		Stack<Integer> stack = new Stack<>();
		boolean rt = findCoins(stack, capacity, changes, 0);
		if(rt) {
			return stack;
		} else {
			return null;
		}
	}
	
	private static boolean findCoins(Stack<Integer> coins, int capacity, int[] changes, int pos) {
		if(capacity == 0) {
			return true;
		} else if(capacity < 0) {
			return false;
		} else if(capacity > 0 && pos == changes.length) {
			return false;
		} else {
			int coin = changes[pos];
			coins.push(coin);
			boolean x = findCoins(coins, capacity - coin, changes, pos + 1);
			if(!x) {
				coins.pop();
				if(pos < changes.length - 1 && capacity >= changes[pos + 1]) {
					x = findCoins(coins, capacity, changes, pos + 1);
				} 
			}
			return x;
		}
	}
	
	private static Iterable<Integer> findCoins(int capacity, int[] changes) {
		Stack<Integer> stack = new Stack<Integer>();
		boolean found = false;
		stack.push(0);
		int left = capacity - changes[0];
		while(!found) {
			int pos = stack.peek();
			if(left == 0) {
				found = true;
			} else if (left < 0 || pos == changes.length - 1){
				int nxtPos = pos + 1;
				if(nxtPos >= changes.length) {
					int idx = stack.firstElement();
					stack.clear();
					if(idx == changes.length - 1) {
						break;
					} else {
						stack.push(idx + 1);
						left = capacity - changes[idx + 1];
					}
				} else {
					int nxtVal = changes[nxtPos];
					int idx = 0;
					int sum = 0;
					while(sum < nxtVal && !stack.isEmpty()) {
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
		if(found) {
			Stack<Integer> coins = new Stack<Integer>();
			for(Integer idx : stack) {
				coins.push(changes[idx]);
			}
			return coins;
		} else {
			return null;
		}
	}
}
