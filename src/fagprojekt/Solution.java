package fagprojekt;

import java.util.LinkedList;
import java.util.List;

public class Solution {
	private LinkedList<Pair> solution;
	private double fit;
	private int T;
	private boolean fitComputable;
	
	public Solution(LinkedList<Pair> solution, int T) {
		this.solution = solution;
		this.T = T;
		calculateFit();
	}
	
	public int numberOfBreakpoints() {
		return solution.size();
	}
	
	// initially empty, fitnesss function should not be computed
	public Solution(int T) {
		this.fitComputable = false;
		this.T = T;
		solution = new LinkedList<Pair>();
	}
	
	public void setBreakpoint(int b, String B) {
		if (b>=T) {
			throw new IllegalArgumentException("Out of range");
		}
		if (solution.size() == 0) {
			solution.add(new Pair(b,B));
		}
		// add (b,B) to the correct position
		for (int i = 0; i < solution.size(); i++) {
		    if (solution.get(i).getLeft() > b) {
		    	solution.add(i-1, new Pair(b,B));
		    	break;
		    }
		    else if (solution.get(i).getLeft() == b){
		    	throw new IllegalArgumentException("Error! This index already exists!");
		    }
		}
	}
	
	public String getBreakpoint(int b) {
		if (b>=T) {
			throw new IllegalArgumentException("Out of range");
		}
		for (int i = 0; i < solution.size(); i++) {
		    if (solution.get(i).getLeft() >= b) {
		    	if (solution.get(i).getLeft() == b) {
		    		return solution.get(i).getRight();
		    	}
		    	else {
		    		return "*";
		    	}
		    }
		}
		return "*";
	}
	
	public void calculateFit() {
		this.fitComputable = true;
		this.fit = UserDefinedFunctions.fit(this);
	}
	
	public double getFit() {
		if (!fitComputable) {
			// ændr exceptiotypen
			throw new IllegalArgumentException();
		}
		return fit;
	}
}
