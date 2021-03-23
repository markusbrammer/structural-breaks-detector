package algorithm;

import data.TimeSeries;

public class Rectangle {

    private double startTime, endTime, minValue, maxValue;

    public Rectangle(int breakPointIndex0, int breakPointIndex1,
                     TimeSeries timeSeries) {
        /*
         * Draw rectangle between break point interval (BPI) in timeseries
         * (TS).
         */

        // Read begin and end time for BPI in TS
        double[] timeAxis = timeSeries.getTime();
        startTime = timeAxis[breakPointIndex0];
        endTime = timeAxis[breakPointIndex1];

        // Read TS min. and max. value in BPI
        double[] timeSeriesValues = timeSeries.getValues();
        minValue = timeSeriesValues[0];
        maxValue = timeSeriesValues[0];
        for (int i = breakPointIndex0; i < breakPointIndex1; i++) {
            double tempValue = timeSeriesValues[i];
            if (tempValue < minValue) {
                minValue = tempValue;
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
            }
        }
    }

    public double getArea() {
        return (double) (endTime - startTime) * (maxValue - minValue);
    }

    public static double getTimeSeriesGraphRectangleArea(TimeSeries timeSeries) {
        double[] timeAxis = timeSeries.getTime();
        double[] values = timeSeries.getValues();
        int timeSeriesLength = timeSeries.getT();

        double startTime = timeAxis[0];
        double endTime = timeAxis[timeSeriesLength - 1];

        double minValue = values[0];
        double maxValue = values[0];
        for (int i = 0; i < timeSeriesLength; i++) {
            double tempValue = values[i];
            if (tempValue < minValue) {
                minValue = tempValue;
            } else if (tempValue > maxValue) {
                maxValue = tempValue;
            }
        }

        return (endTime - startTime) * (maxValue - minValue);
    }

}
