package fagprojekt;

import java.util.LinkedList;
import java.util.Map;

public class Solution {
	private LinkedList<Pair> solution;
	private double fit;
	private int T;
	private boolean fitComputable;
	
	public Solution(LinkedList<Pair> solution, int T) {
		this.solution = solution;
		calculateFit();
	}
	
	// initially empty, fitnesss function should not be computed
	public Solution(int T) {
		this.fitComputable = false;
		this.T = T;
		solution = new LinkedList<Pair>();
	}
	
	public void set(int b, String B) {
		
	}
	
	// TO-DO: implementation of fitness function
	public void calculateFit() {
		this.fitComputable = true;
		this.fit = 0.0;
	}
	
	public double getFit() {
		if (!fitComputable) {
			// ændr exceptiotypen
			throw new IllegalArgumentException();
		}
		return fit;
	}
	
	
	
}
