module com.cal.calculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.cal.calculator to javafx.fxml;
    exports com.cal.calculator;
}