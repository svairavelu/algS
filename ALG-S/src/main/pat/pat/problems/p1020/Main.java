/**
 * 
 */
package pat.problems.p1020;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Blues
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String nl = br.readLine();
			String[] firstLine = br.readLine().split("\\s+");
			String[] secondLine = br.readLine().split("\\s+");
			int n = Integer.valueOf(nl);
			int[] postOrderSeq = new int[n];
			int[] inOrderSeq = new int[n];
			for(int i = 0; i < n; i++) {
				postOrderSeq[i] = Integer.valueOf(firstLine[i]);
				inOrderSeq[i] = Integer.valueOf(secondLine[i]);
			}
			
			Node root = process(postOrderSeq, inOrderSeq);
			
			int[] levelOrder = levelOrder(root, n);
			System.out.print(levelOrder[0]);
			for(int i= 1; i < levelOrder.length; i++) {
				System.out.print(" " + levelOrder[i]);
			}
			System.out.println();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private static int[] levelOrder(Node root, int n) {
		Node[] nodes = new Node[n];
		int[] seq = new int[n];
		int index = 0;
		int size = 0;
		nodes[size ++] = root;
		while(size > 0) {
			Node[] nxtNodes = new Node[n];
			int nxtSize = 0;
			for(int i = 0; i < size; i++) {
				Node x = nodes[i];
				seq[index++] = x.index;
				if(x.left != null) {
					nxtNodes[nxtSize++] = x.left;
				}
				if(x.right != null) {
					nxtNodes[nxtSize++] = x.right;
				}
			}
			
			nodes = nxtNodes;
			size = nxtSize;
		}
		return seq;
	}
	private static boolean inSeq(int x, int[] seq) {
		for(int i = 0; i < seq.length; i++) {
			if(x == seq[i]) {
				return true;
			}
		}
		return false;
	}
	
	public static Node process(int[] postOrderSeq, int[] inOrderSeq) {
		int n = postOrderSeq.length;
		int x = postOrderSeq[n - 1];
		Node root = new Node(x);
		
		if(n > 1) {
			int[] left = new int[n];
			int[] right = new int[n];
			int side = -1; //-1 for left, 1 for right;
			int l = 0, r = 0;
			for(int i = 0; i < n; i++) {
				if(inOrderSeq[i] == x) {
					side = 1;
					continue;
				}
				
				if(side < 0) {
					left[l++] = inOrderSeq[i]; 
				} else {
					right[r++] = inOrderSeq[i];
				}
			}
			
			if(l > 0) {
				left = Arrays.copyOf(left, l);
				int[] leftPostOrder = new int[l];
				int index = 0;
				for(int i = 0; i < postOrderSeq.length; i++) {
					if(inSeq(postOrderSeq[i], left)) {
						leftPostOrder[index++] = postOrderSeq[i];
					}
				}
				root.left = process(leftPostOrder, left);
			}
			
			if(r > 0) {
				right = Arrays.copyOf(right, r);
				int[] rightPostOrder = new int[r];
				int index = 0;
				for(int i = 0; i < postOrderSeq.length; i++) {
					if(inSeq(postOrderSeq[i], right)) {
						rightPostOrder[index++] = postOrderSeq[i];
					}
				}
				root.right = process(rightPostOrder, right);
			}
		}
		
		return root;
	}
	
	static class Node {
		final int index;
		
		Node left, right;
		
		public Node(int index) {
			this.index = index;
		}
	}
}
