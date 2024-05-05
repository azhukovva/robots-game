module vut.ija2023 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    opens vut.ija2023.common to com.fasterxml.jackson.databind;


    opens vut.ija2023 to javafx.fxml;
    exports vut.ija2023;
    opens vut.ija2023.common.log to com.fasterxml.jackson.databind;
    opens vut.ija2023.common.config to com.fasterxml.jackson.databind;
}