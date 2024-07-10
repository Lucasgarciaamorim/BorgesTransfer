module com.borges.transferenciaborges {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.borges.transferenciaborges to javafx.fxml;
    exports com.borges.transferenciaborges;
    exports com.borges.transferenciaborges.controller;
    opens com.borges.transferenciaborges.controller to javafx.fxml;
}