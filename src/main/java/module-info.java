module dev.hiruna.weatherly {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;
    requires javafx.media;

    opens dev.hiruna.weatherpulse to javafx.fxml;
    exports dev.hiruna.weatherpulse;
}