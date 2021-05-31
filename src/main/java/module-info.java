module Fagprojekt{
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.desktop;
    requires org.controlsfx.controls;
    opens sample to javafx.fxml;
    exports sample;
    exports data;
    exports ga;
    exports fitness;
    exports fitness.rectangle;
    exports bp;
}