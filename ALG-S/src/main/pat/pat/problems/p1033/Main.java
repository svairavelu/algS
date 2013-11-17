package pat.problems.p1033;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] firstLine = br.readLine().split("\\s+");
			int max = Integer.valueOf(firstLine[0]);
			int d = Integer.valueOf(firstLine[1]);
			int avg = Integer.valueOf(firstLine[2]);
			int n = Integer.valueOf(firstLine[3]);
			
			Station[] stations = new Station[n + 1];
			for(int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				double price = Double.valueOf(line[0]);
				double dist = Double.valueOf(line[1]);
				stations[i] = new Station(dist, price);
			}
			
			stations[n] = new Station(d, 0);
			Arrays.sort(stations);
			
			Alg alg = new Alg(stations, avg, max);
			
			if(alg.getMaxDist() < d) {
				System.out.println("The maximum travel distance = " + alg.getMaxDist());
			} else {
				System.out.printf("%.2f\n", alg.getCost());
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}

class Alg {

	private Station[] stations;
	private int avgGas;
	private int tankVol;
	private double cost = 0;
	private double maxDist;

	public Alg(Station[] stations, int avgGas, int tankVol) {
		this.stations = stations;
		this.avgGas = avgGas;
		this.tankVol = tankVol;
		plan();
	}

	private void plan() {
		if(stations[0].dist > 0) {
			return;
		} 
		
		double step = 1.0 * tankVol * avgGas;
		int current = 0;
		double currentGas = 0.0;
		while(current < stations.length - 1) {
			if(stations[current + 1].dist - stations[current].dist > step) {
				maxDist = stations[current].dist + step;
				break;
			}
			
			Station v = stations[current];
			int index = current;
			double minPrice = v.price;
			for(int i = index + 1; i < stations.length && stations[i].dist - v.dist <= currentGas * avgGas; i++) {
				if(stations[i].price < minPrice) {
					index = i;
					minPrice = stations[i].price;
				}
			}
			
			if(index != current) {
				currentGas -= (stations[index].dist - v.dist) / avgGas;
				current = index;
				continue;
			}
			
			index = current;
			for(int i = index + 1; i < stations.length && stations[i].dist - v.dist <= step; i++) {
				if(stations[i].price < minPrice) {
					index = i;
					break;
				}
			}
			
			if(index != current) {
				cost += ((stations[index].dist - v.dist) / avgGas - currentGas) * v.price;
				currentGas = 0;
				current = index;
				continue;
			}
			
			index = current;
			minPrice = Double.MAX_VALUE;
			for(int i = index + 1; i < stations.length && stations[i].dist - v.dist <= step; i++) {
				if(stations[i].price < minPrice) {
					index = i;
					minPrice = stations[i].price;
				}
			}
			
			cost += (tankVol - currentGas) * v.price;
			currentGas = tankVol - (stations[index].dist - v.dist) / avgGas;
			current = index;
		}
		
		if(current == stations.length - 1) {
			maxDist = stations[current].dist;
		}
	}

	public double getCost() {
		return cost;
	}

	public double getMaxDist() {
		return maxDist;
	}

}

class Station implements Comparable<Station> {
	final double dist; // off hangzhou
	final double price;

	public Station(double dist, double price) {
		super();
		this.dist = dist;
		this.price = price;
	}

	@Override
	public int compareTo(Station o) {
		if (dist > o.dist) {
			return 1;
		} else if (dist < o.dist) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "Station [dist=" + dist + ", price=" + price + "]";
	}
	
}