package pat.problems.p1043;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws Exception {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			int n = Integer.valueOf(br.readLine());
			int[] xs = new int[n];
			String[] line = br.readLine().split("\\s+");
			for(int i = 0; i < n; i++) {
				xs[i] = Integer.valueOf(line[i]);
			}
			BST bst = new BST(xs, n);
			if(bst.check()) {
				System.out.println("YES");
				System.out.print(bst.ys[n - 1]);
				for(int i = n - 2; i >= 0; i--) {
					System.out.print(" " + bst.ys[i]);
				}
			} else {
				System.out.println("NO");
			}
			
		} catch(Exception ex) {
			throw ex;
		}
	}
	
	
}

class BST {
	final int[] xs, ys;
	private int yIdx;
	final int n;
	public BST(int[] xs, int n) {
		super();
		this.xs = xs;
		this.ys = new int[n];
		this.n = n;
	}

	public boolean check() {
		yIdx = 0;
		if(checkBST(0, n)) {
			return true;
		} else {
			yIdx = 0;
			return checkMBST(0, n);
		}
	}
	
	private boolean checkBST(int start, int end) {
		if(start == end) {
			return true;
		}
		
		int x = xs[start];
		ys[yIdx++] = x;
		
		int rightStart = end - 1;
		while(rightStart > start && xs[rightStart] >= x) {
			rightStart -= 1;
		}
		rightStart += 1;
		if(checkBST(rightStart, end)) {
			int leftStart = rightStart - 1;
			while(leftStart > start && xs[leftStart] < x) {
				leftStart -= 1;
			}
			
			leftStart += 1;
			
			if(leftStart != start + 1) {
				return false;
			} else {
				return checkBST(leftStart, rightStart);
			}
		} else {
			return false;
		}
	}

	private boolean checkMBST(int start, int end) {
		if(start == end) {
			return true;
		}
		
		int x = xs[start];
		ys[yIdx++] = x;
		
		int rightStart = end - 1;
		while(rightStart > start && xs[rightStart] < x) {
			rightStart -= 1;
		}
		rightStart += 1;
		if(checkMBST(rightStart, end)) {
			int leftStart = rightStart - 1;
			while(leftStart > start && xs[leftStart] >= x) {
				leftStart -= 1;
			}
			
			leftStart += 1;
			
			if(leftStart != start + 1) {
				return false;
			} else {
				return checkMBST(leftStart, rightStart);
			}
		} else {
			return false;
		}
	}
	
}
