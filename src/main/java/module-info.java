module Fagprojekt{
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.desktop;
    // requires org.controlsfx.controls;
    opens gui to javafx.fxml;
    exports gui;
    exports data;
    exports genetic;
    exports fitness;
    exports fitness.rectangle;
    exports breakpointalgorithm;
}