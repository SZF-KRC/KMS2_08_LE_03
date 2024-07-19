module com.example.bildungsinstitut {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    // Exportujte balík com.bildungsinstitut do javafx.graphics
    opens com.bildungsinstitut to javafx.graphics, javafx.fxml;
    exports com.bildungsinstitut;
    exports com.bildungsinstitut.view;
    opens com.bildungsinstitut.view to javafx.fxml;
}
