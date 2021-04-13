module Fagprojekt{
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.desktop;
    opens sample to javafx.fxml;
    exports sample;
}