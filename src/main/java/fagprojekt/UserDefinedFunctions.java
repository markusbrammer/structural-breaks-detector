package fagprojekt;

import timeseries.TimeSeries;

// Johan
// works by the rectangle method
public class UserDefinedFunctions {
	public static String newB() {
		return null;
	}
	
	public static double g(Solution S, TimeSeries ts) {
		Rectangle R = new Rectangle(0, ts.getT(),ts);
		double areaOfR = R.getArea();
		
		int[] breakpoints = S.returnBreakpoints();
		
		double sum = 0.0;
		
		for (int i = 0; i<breakpoints.length-1; i++) {
			Rectangle temp = new Rectangle(breakpoints[i], breakpoints[i+1],ts);
			sum += temp.getArea();
		}
		
		return (areaOfR - sum)/areaOfR;
	}
	
	public static double fit(Solution S, double alpha, TimeSeries ts) {
		return g(S,ts) + alpha*p1(S.numberOfBreakpoints());
	}
	
	public static double p1(int k ) {
		return 1.0 / Math.sqrt(k);
	}
	
	
}

