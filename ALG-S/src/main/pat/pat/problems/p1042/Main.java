package pat.problems.p1042;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	static String[] tps = {"S", "H", "C", "D"};
	public static void main(String[] args) {
		String[] cards = new String[54];
		int idx = 0;
		for(int i = 0; i < 4; i++) {
			String tp = tps[i];
			for(int j = 1; j <= 13; j++) {
				cards[idx++] = tp + j;
			}
		}
		
		cards[52] = "J1";
		cards[53] = "J2";
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			
			int times = Integer.valueOf(br.readLine());
			String[] line = br.readLine().split("\\s+");
			int[] xs = new int[line.length];
			for(int i = 0; i < xs.length; i++) {
				xs[i] = Integer.valueOf(line[i]) - 1;
			}
			
			
			for(int i = 0; i < times; i++) {
				String[] shuffedCards = new String[54];
				for(int j = 0; j < 54; j++) {
					int x = xs[j];
					shuffedCards[x] = cards[j];
				}
				cards = shuffedCards;
			}
			
			System.out.print(cards[0]);
			
			for(int i = 1; i < 54; i++) {
				System.out.print(" " + cards[i]);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
