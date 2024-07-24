module com.example.bildungsinstitut {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.desktop;


    opens com.bildungsinstitut to javafx.graphics, javafx.fxml;
    opens com.bildungsinstitut.model to javafx.base;
    opens com.bildungsinstitut.view to javafx.fxml;


    exports com.bildungsinstitut;
    exports com.bildungsinstitut.view;
}
