package pat.problems.p1066;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {

		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			int n = Integer.valueOf(br.readLine());
			AVL<Integer> avl = new AVL<>();
			String[] nums = br.readLine().split("\\s+");
			for(int i = 0; i < n; i++) {
				avl.insert(Integer.valueOf(nums[i]));
			}
			
			System.out.println(avl.root());
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}


class AVL<T extends Comparable<T>> {
	
	class Node {
		final T val;
		Node left, right;
		int height;
		
		public Node(T val) {
			this.val = val;
			this.height = 0;
		}

		@Override
		public String toString() {
			return "Node [val=" + val + ", height=" + height + "]";
		}
	}
	
	private Node root;
	public AVL() {
		
	}
	
	public void insert(T t) {
		root = insert(root, t);
	}
	
	private int height(Node n) {
		if(n == null) {
			return -1;
		} else {
			return n.height;
		}
	}
	
	private int max(int x, int y) {
		if(x > y) {
			return x;
		} else {
			return y;
		}
	}
	
	private Node singleRotateWithLeft(Node p) {
		Node x = p.left;
		p.left = x.right;
		x.right = p;
		
		p.height = max(height(p.left), height(p.right)) + 1;
		x.height = max(height(x.left), p.height) + 1;
		return x;
	}
	
	private Node singleRotateWithRight(Node p) {
		Node x = p.right;
		p.right = x.left;
		x.left = p;
		
		p.height = max(height(p.right), height(p.left)) + 1;
		x.height = max(height(x.right), p.height) + 1;
		return x;
	}
	
	private Node doubleRotateWithLeft(Node g) {
		g.left = singleRotateWithRight(g.left);
		return singleRotateWithLeft(g);
	}
	
	private Node doubleRotateWithRight(Node g) {
		g.right = singleRotateWithLeft(g.right);
		return singleRotateWithRight(g);
	}
	
	private Node insert(Node p, T t) {
		if(p == null) {
			return new Node(t);
		} else {
			int cp = t.compareTo(p.val);
			if(cp == 0) {
				//the value is already in the tree, no operations;
			} else if(cp < 0) {
				p.left = insert(p.left, t);
				
				if(height(p.left) - height(p.right) > 1) {
					//need to re-balance
					if(t.compareTo(p.left.val) < 0) {
						p = singleRotateWithLeft(p);
					} else {
						p = doubleRotateWithLeft(p);
					}
				}
			} else if(cp > 0) {
				p.right = insert(p.right, t);
				
				if(height(p.right) - height(p.left) > 1) {
					if(t.compareTo(p.right.val) > 0) {
						p = singleRotateWithRight(p);
					} else {
						p = doubleRotateWithRight(p);
					}
				}
				
			}
			p.height = max(height(p.left), height(p.right)) + 1;
			return p;
		}
	}
	
	public T root() {
		return root.val;
	}
}