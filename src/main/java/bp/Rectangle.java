package bp;

public class Rectangle {

    private double xMin, xMax, yMin, yMax;

    public Rectangle (double xMin, double xMax, double yMin, double yMax) {

        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;

    }

    public double getArea() {
        return (xMax - xMin) * (yMax - yMin);
    }


//    public Rectangle(int breakPointIndex0, int breakPointIndex1,
//                     TimeSeries timeSeries) {
//        /*
//         * Draw rectangle between break point interval (BPI) in timeseries
//         * (TS).
//         */
//
//        // Read begin and end time for BPI in TS
//        double[] timeAxis = timeSeries.getTime();
//        xMin = timeAxis[breakPointIndex0];
//        xMax = timeAxis[breakPointIndex1];
//
//        // Read TS min. and max. value in BPI
//        double[] timeSeriesValues = timeSeries.getValues();
//        yMin = timeSeriesValues[0];
//        yMax = timeSeriesValues[0];
//        for (int i = breakPointIndex0; i < breakPointIndex1; i++) {
//            double tempValue = timeSeriesValues[i];
//            if (tempValue < yMin) {
//                yMin = tempValue;
//            } else if (tempValue > yMax) {
//                yMax = tempValue;
//            }
//        }
//    }
//
//    public double getArea() {
//        return (double) (xMax - xMin) * (yMax - yMin);
//    }
//
//    public static double getTimeSeriesGraphRectangleArea(TimeSeries timeSeries) {
//        double[] timeAxis = timeSeries.getTime();
//        double[] values = timeSeries.getValues();
//        int timeSeriesLength = timeSeries.getLength();
//
//        double startTime = timeAxis[0];
//        double endTime = timeAxis[timeSeriesLength - 1];
//
//        double minValue = values[0];
//        double maxValue = values[0];
//        for (int i = 0; i < timeSeriesLength; i++) {
//            double tempValue = values[i];
//            if (tempValue < minValue) {
//                minValue = tempValue;
//            } else if (tempValue > maxValue) {
//                maxValue = tempValue;
//            }
//        }
//
//        return (endTime - startTime) * (maxValue - minValue);
//    }

}
