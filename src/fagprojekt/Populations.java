package fagprojekt;

import java.util.List;
import java.util.Random;

public class Populations {
	private List<Solution> population; 
	private int M;
	
	public Populations(List<Solution> population) {
		this.population = population;
		this.M = population.size();
	}
	
	public Populations(int M, int k0) {
		this.M = M;
		Random rand = new Random();
		for (int i = 0; i < M; i++) {
			int k = rand.nextInt(k0)+1;
			for (int j = 1; j <= k; j++) {
				int k = rand.nextInt(k0)+1;
				
			}
			
		}
	}
}
