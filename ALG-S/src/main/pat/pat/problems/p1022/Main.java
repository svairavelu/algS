/**
 * 
 */
package pat.problems.p1022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Blues
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in))) {
			int n = Integer.valueOf(br.readLine());

			SplayTree[] ts = new SplayTree[6];
			for (int i = 1; i < ts.length; i++) {
				ts[i] = new SplayTree();
			}

			for (int i = 0; i < n; i++) {
				String id = br.readLine();
				String title = br.readLine();
				ts[1].insert(id, title);
				String author = br.readLine();
				ts[2].insert(id, author);
				String[] keyWords = br.readLine().split("\\s+");
				for (int j = 0; j < keyWords.length; j++) {
					ts[3].insert(id, keyWords[j]);
				}

				String publisher = br.readLine();
				ts[4].insert(id, publisher);
				String publishedYear = br.readLine();
				ts[5].insert(id, publishedYear);
			}

			int m = Integer.valueOf(br.readLine());
			for (int i = 0; i < m; i++) {
				String line = br.readLine();
				String[] xline = line.split(": ");
				int no = Integer.valueOf(xline[0]);
				String key = xline[1];
				List<String> rs = ts[no].find(key);
				System.out.println(line);
				if (rs == null) {
					System.out.println("Not Found");
				} else {
					Collections.sort(rs);
					for (String x : rs) {
						System.out.println(x);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

class SplayTree {
	class Node {
		int hashKey; // which is used as the key value
		String key;
		List<String> ids;
		Node left, right, parent;

		public Node(String key, String id) {
			this.key = key;
			this.hashKey = key.hashCode();
			this.ids = new LinkedList<String>();
			this.ids.add(id);
		}

		public void add(String id) {
			this.ids.add(id);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + hashKey;
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
			Node other = (Node) obj;
			if (hashKey != other.hashKey)
				return false;
			return true;
		}

		public List<String> getIds() {
			return ids;
		}

		@Override
		public String toString() {
			return "Node [key=" + key + "]";
		}

	}

	private Node root;

	public SplayTree() {
	}

	public List<String> find(String key) {
		Node node = find(key.hashCode());
		if (node != null) {
			return node.getIds();
		}
		return null;
	}

	private Node find(int hashKey) {
		Node tmp = root;
		while (tmp != null) {
			if (hashKey == tmp.hashKey) {
				break;
			}
			if (hashKey < tmp.hashKey) {
				tmp = tmp.left;
			} else {
				tmp = tmp.right;
			}
		}
		return tmp;
	}

	
	private void leftToRight(Node x) {
		Node p = x.parent;
		Node g = p.parent;
		p.left = x.right;
		p.parent = x;
		x.right = p;
		x.parent = g;
		if(g != null && g.hashKey < x.hashKey) {
			g.right = x;
		} else if(g != null && g.hashKey > x.hashKey) {
			g.left = x;
		}
	}
	
	private void rightToLeft(Node x) {
		Node p = x.parent;
		Node g = p.parent;
		p.right = x.left;
		x.parent = p.parent;
		p.parent = x;
		x.left = p;
		x.parent = g;
		if(g != null && g.hashKey < x.hashKey) {
			g.right = x;
		} else if(g != null && g.hashKey > x.hashKey) {
			g.left = x;
		}
	}

	private Node splay(Node x) {
		Node p = x.parent;
		while(p != null) {
			Node g = p.parent;
			if(g == null) {
				if(p.left == x) {
					leftToRight(x);
				} else {
					rightToLeft(x);
				}
			} else {
				if(g.left == p && p.left == x) {
					leftToRight(p);
					leftToRight(x);
				} else if(g.left == p && p.right == x) {
					rightToLeft(x);
					leftToRight(x);
				} else if(g.right == p && p.right == x) {
					rightToLeft(p);
					rightToLeft(x);
				} else if(g.right == p && p.left == x) {
					leftToRight(x);
					rightToLeft(x);
				}
			}
			p = x.parent;
		}
		
		return x;
	}
	
	private Node insert(Node root, String id, String key, int hashKey) {
		Node p = root;
		Node tmp = root;
		while(tmp != null) {
			if(tmp.hashKey == hashKey) {
				tmp.add(id);
				break;
			} else if(tmp.hashKey > hashKey) {
				p = tmp;
				tmp = tmp.left;
			} else {
				p = tmp;
				tmp = tmp.right;
			}
		}
		
		if(tmp == null) {
			tmp = new Node(key, id);
			tmp.parent = p;
			if(hashKey > p.hashKey) {
				p.right = tmp;
			} else {
				p.left = tmp;
			}
		}
		
		return splay(tmp);
		
	}

	public void insert(String id, String key) {
		if(root == null) {
			root = new Node(key, id);
			return;
		}
		root = insert(root, id, key, key.hashCode());
	}
}