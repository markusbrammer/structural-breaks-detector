module Fagprojekt{
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.desktop;
    requires com.jfoenix;
    opens sample to javafx.fxml;
    exports sample;
    exports data;
}