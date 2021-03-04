package fagprojekt;
import java.util.ArrayList;
// Johan
import java.util.List;
import java.util.Random;

public class Populations {
	private List<Solution> population; 
	private int M;
	private int T;
	
	public double sumOfSquaredFitnesses() {
		double h = 0.0; 
		for (int i = 0; i < population.size(); i++) {
			h+=population.get(i).getFit()*population.get(i).getFit();
		}
		return h;
	}
	
	public Populations(List<Solution> population, int T) {
		this.population = population;
		this.T = T;
		this.M = population.size();
	}
	
	public void removeIndex(int i) {
		population.remove(i);
	}
	
	public void add(Solution s) {
		population.add(s);
	}
	
	public Solution getIndex(int i) {
		if (i>=0 && i < M) {
			return population.get(i);
		}
		throw new IllegalArgumentException();
	}
	
	// måske mere effektivt at returnere den minimale værdi sammen med indekset
	public int leastFitIndex() {
		int minIndex = 0;
		double min = population.get(minIndex).getFit();
		for (int i = 1; i < M; i++) {
			if (population.get(i).getFit() < min) {
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public Solution fittestString() {
		int maxIndex = 0;
		double max = population.get(0).getFit();
		for (int i = 1; i < M; i++) {
			if (population.get(i).getFit() > max) {
				maxIndex = i;
			}
		}
		return population.get(maxIndex);
	}
	
	// init
	public Populations(int M, int k0, int T) {
		this.population = new ArrayList<Solution>();
		this.M = M;
		Random rand = new Random();
		for (int i = 0; i < M; i++) {
			int k = rand.nextInt(k0)+1;
			Solution temp = new Solution(T);
			temp.setBreakpoint(0, UserDefinedFunctions.newB());
			for (int j = 1; j <= k; j++) {
				temp.setBreakpoint(rand.nextInt(T-1)+1, UserDefinedFunctions.newB());
			}
			population.add(temp);
		}
	}
}
