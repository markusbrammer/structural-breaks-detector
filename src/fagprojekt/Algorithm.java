package fagprojekt;
import java.util.Random;

public class Algorithm {
	private TimeSeries timeSeries;
	private Random rand;
	
	public Algorithm(TimeSeries timeSeries) {
		this.timeSeries = timeSeries;
		Random rand = new Random();
	}
	
	public int[] UniformCrossover(int[] X, int[] Y) {
		int[] C = new int[timeSeries.getT()];
		for (int i = 0; i < timeSeries.getT(); i++) {
			if (rand.nextDouble()<0.5) {
				C[i] = X[i];
			}
			else {
				C[i] = Y[i];
			}
		}
		return C;
	}
	
	public int[] OnePointCrossover(int[] X, int[] Y) {
		int k = rand.nextInt(timeSeries.getT());
		int[] C = new int[timeSeries.getT()];
		for (int i = 0; i < timeSeries.getT(); i++) {
			if (i<k) {
				C[i] = X[i];
			}
			else {
				C[i] = Y[i];
			}
		}
		return C;
	}
}
