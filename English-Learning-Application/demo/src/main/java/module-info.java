module erdos.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens erdos.demo.controllers to javafx.fxml;
    exports erdos.demo;
}