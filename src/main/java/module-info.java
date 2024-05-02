module vut.ija2023 {
    requires javafx.controls;
    requires javafx.fxml;


    opens vut.ija2023 to javafx.fxml;
    exports vut.ija2023;
}