package pat.problems.p1012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] cs = {"A", "C", "M", "E"};
			Main processor = new Main();
			String[] line = br.readLine().split("\\s+");
			processor.init(Integer.valueOf(line[0]), br);
			int m = Integer.valueOf(line[1]);
			for(int i = 0; i < m; i++) {
				String id = br.readLine();
				ST st = processor.find(id);
				if(st != null) {
					int rank = st.rank();
					int c = st.best();
					System.out.printf("%d %s\n", rank, cs[c]);
				} else {
					System.out.println("N/A");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private ST[] sts;

	private Comparator<ST> comparator = new Comparator<ST>() {
		public int compare(ST o1, ST o2) {
			return o1.id.compareTo(o2.id);
		}
	};
	
	public void init(int n, BufferedReader br) throws IOException {
		sts = new ST[n];

		for (int i = 0; i < n; i++) {
			ST st = new ST(br.readLine());
			sts[i] = st;
		}

		for (int k = 0; k < 4; k++) {
			for (int i = 1; i < n; i++) {
				ST sti = sts[i];
				int j;
				for (j = i; j > 0 && sts[j - 1].scores[k] < sti.scores[k]; j--) {
					sts[j] = sts[j - 1];
				}
				sts[j] = sti;
			}
			
			sts[0].rank[k] = 1;
			for(int i = 1; i < n; i++) {
				if(sts[i].scores[k] == sts[i - 1].scores[k]) {
					sts[i].rank[k] = sts[i - 1].rank[k];
				} else {
					sts[i].rank[k] = i + 1;
				}
			}
			
		}

		Arrays.sort(sts, comparator);
	}
	
	ST dummy = new ST();

	public ST find(String id) {
		dummy.id = id;
		int index = Arrays.binarySearch(sts, dummy, comparator);
		if(index >= 0) {
			return sts[index];
		} else {
			return null;
		}
	}
	
	static class ST {
		String id;
		int[] scores = new int[4];
		int[] rank = new int[4];

		int bestRank = Integer.MAX_VALUE;
		int bestRankIndex = -1;
		public ST(String line) {
			String[] s = line.split("\\s+");
			this.id = s[0];
			scores[1] = Integer.valueOf(s[1]);
			scores[2] = Integer.valueOf(s[2]);
			scores[3] = Integer.valueOf(s[3]);
			scores[0] = (scores[1] + scores[2] + scores[3]) / 3;
			for (int i = 0; i < rank.length; i++) {
				rank[i] = Integer.MAX_VALUE;
			}
		}
		
		public ST() {
			
		}
		
		public int rank() {
			for(int i = 0; i < rank.length; i++) {
				if(rank[i] < bestRank) {
					bestRank = rank[i];
					bestRankIndex = i;
				}
			}
			return bestRank;
		}
		
		public int best() {
			return bestRankIndex;
		}

		@Override
		public String toString() {
			return "ST [id=" + id + ", scores=" + Arrays.toString(scores)
					+ ", rank=" + Arrays.toString(rank) + "]";
		}
		
		
	}
}
