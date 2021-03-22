module Fagprojekt{
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    opens sample to javafx.fxml;
    exports sample;
}