/**
 * 
 */
package pat.problems.p1004;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Blues
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String[] fl = br.readLine().split(" ");
			int n = Integer.valueOf(fl[0]);
			int m = Integer.valueOf(fl[1]);
			
			
			if(m == 0) {
				System.out.println(1);
				br.close();
				return;
			}

			FM[] fms = new FM[n + 1];
			
			for(int i = 0; i < m; i++) {
				String[] line = br.readLine().split("\\s+");
				String label = line[0];
				int index = Integer.valueOf(label);
				FM fm = new FM();
				int k = Integer.valueOf(line[1]);
				fm.hasC = k != 0;
				fms[index] = fm;				
				for(int j = 0; j < k; j++) {
					fm.addChild(Integer.valueOf(line[j + 2]));
				}
			}
			
			int maxLevel = process(fms[1], fms, 0);
			
			int[] levelCount = new int[maxLevel + 1];
			for(int i = 0; i <= maxLevel; i++) {
				int count = 0;
				for(int j = 0; j < n + 1; j++) {
					if(fms[j] != null && fms[j].level == i && fms[j].hasC == false) {
						count += 1;
					}
				}
				levelCount[i] = count;
			}
			
			
			StringBuffer buf = new StringBuffer();
			buf.append(levelCount[0]);
			for(int i = 1; i < levelCount.length; i++) {
				buf.append(" ").append(levelCount[i]);
			}
			System.out.println(buf.toString());
			br.close();
	}

	static int process(FM root, FM[] fms, int maxLevel) {
		int level = maxLevel;
		for(int c : root.children) {
			FM fmc = fms[c];
			if(fmc == null) {
				//it has to be leaf node
				fmc = new FM(root.level + 1);
				fms[c] = fmc;
				if(fmc.level > level) {
					level = fmc.level;
				}
			} else {
				// it may or may not a leaf node, but it doesn't matter here, just update its level
				fmc.level = root.level + 1;
				if(level < fmc.level) {
					level = fmc.level;
				}
				int rtLevel = process(fmc, fms, level);
				if(rtLevel > level) {
					level = rtLevel;
				}
			}
		}
		return level;
	}
	
	static class FM {
		int level;
		public FM(int level) {
			this.level = level;
		}
		
		public FM() {
			this(0);
		}
		boolean hasC = false;
		
		List<Integer> children = new ArrayList<>();
		
		public void addChild(int c) {
			children.add(c);
		}
	}
}
