package fagprojekt;

public class TimeSeries {
	private int[] time;
	private double[] values;
	private int T;
	
	public TimeSeries(int[] time, double[] values) {
		if (time.length != values.length) {
			throw new IllegalArgumentException("Error!");
		}
		this.time = time;
		this.values = values;
		this.T =  time.length;
	}

	public int[] getTime() {
		return time;
	}

	public double getValues(int i) {
		return values[i];
	}

	public int getT() {
		return T;
	}
	
	
}
