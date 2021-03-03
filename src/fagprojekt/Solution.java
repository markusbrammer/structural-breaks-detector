package fagprojekt;
// Johan
import java.util.LinkedList;
import java.util.List;

public class Solution {
	private LinkedList<Pair> solution;
	private double fit;
	private int T;
	
	public Solution(LinkedList<Pair> solution, int T, TimeSeries ts, double alpha) {
		this.solution = solution;
		this.T = T;
		calculateFit(alpha,ts);
	}
	
	public int numberOfBreakpoints() {
		return solution.size();
	}
	
	// initially empty, fitnesss function should not be computed
	public Solution(int T) {
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
		    	solution.add(i, new Pair(b,B));
		    	break;
		    }
		    else if (solution.get(i).getLeft() == b){
		    	solution.set(i, new Pair(b,B));
		    	break;
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
	
	public int[] returnBreakpoints() {
		int[] arr = new int[solution.size()];
		// måske lidt unødvendigt
		for (int i = 0; i < solution.size(); i++) {
			arr[i] = solution.get(i).getLeft();
		}
		return arr;
	}
	
	public void calculateFit(double alpha, TimeSeries ts) {
		this.fit = UserDefinedFunctions.fit(this, alpha, ts);
	}
	
	public double getFit() {
		return fit;
	}
}
