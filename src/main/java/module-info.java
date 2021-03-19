module fagprojekt {
	requires jdk.compiler;
	requires json.simple;
	requires javafx.graphics;
	requires javafx.controls;
	opens fagprojekt;
	exports timeseries to javafx.graphics;
	exports gui to javafx.graphics;
}
