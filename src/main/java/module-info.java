module com.heshanthenura.platformergame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.heshanthenura.platformergame to javafx.fxml;
    exports com.heshanthenura.platformergame;
}