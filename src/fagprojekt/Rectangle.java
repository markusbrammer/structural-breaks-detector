package fagprojekt;
// Johan
public class Rectangle {
	private int x0, x1;
	private double y0, y1;
	
	public double getArea() {
		return ((double) (x1-x0)) * (y1-y0);
	}
	
	public Rectangle(int firstBreakpoint, int secondBreakpoint, TimeSeries ts) {
		// For R use firstBreakpoint = 0 and secondBreakpoint = T+1
		// find min and max - assuming equidistance
		this.x0 = firstBreakpoint;
		this.x1 = secondBreakpoint + 1;
		
		// basic max-min finding algorithm that goes through all values
		double m0 = ts.getValues(firstBreakpoint);
		double m1 = ts.getValues(firstBreakpoint);
		for (int i = firstBreakpoint+1; i < secondBreakpoint; i++) {
			double temp = ts.getValues(i);
			if (temp < m0) {
				m0 = temp;
			}
			else if (m1 < temp) {
				m1 = temp;
			}
		}
		this.y0 = m0;
		this.y1 = m1;
	}
}
