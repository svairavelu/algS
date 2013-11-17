package pat.problems.p1039;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int n = Integer.valueOf(firstLine[0]);
			int k = Integer.valueOf(firstLine[1]);
			
			int[][] matrix = new int[40000][k + 1];
			int size = 0;
			Map<String, Integer> map = new HashMap<>();
			
			for(int i = 0; i < k; i++) {
				String[] line = br.readLine().split("\\s+");
				int no = Integer.valueOf(line[0]);
				int nx = Integer.valueOf(line[1]);
				if(nx == 0) {
					//can't assume that every course has at least 1 students; 
					continue;
				}
				String[] ss = br.readLine().split("\\s+");
				for(String name : ss) {
					int idx = -1;
					if(map.containsKey(name)) {
						idx = map.get(name);
					} else {
						idx = size++;
						map.put(name, idx);
					}
					
					int[] row = matrix[idx];
					row[0] += 1;
					row[no] = 1;
				}
			}
			
			String[] names = br.readLine().split("\\s+");
			for(String name : names) {
				if(map.containsKey(name)) {
					int idx = map.get(name);
					int[] row = matrix[idx];
					System.out.print(name + " " + row[0]);
					for(int i = 1; i <= k; i++) {
						if(row[i] > 0) {
							System.out.print(" " + i);
						}
					}
					System.out.println();
				} else {
					System.out.println(name + " 0");
				}
				
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
