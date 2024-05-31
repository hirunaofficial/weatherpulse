package dev.hiruna.weatherpulse;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;

public class WeatherPulseApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WeatherPulseApplication.class.getResource("weatherpulse-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set the icon of the application window
        stage.getIcons().add(new Image(WeatherPulseApplication.class.getResourceAsStream("icon.png")));

        // Disable window resize
        stage.setResizable(false);

        stage.setTitle("WeatherPulse (Your Daily Weather Companion)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Check for network connection before launching the application
        if (isNetworkAvailable()) {
            launch(args);
        } else {
            showAlertAndExit();
        }
    }

    // Method to show alert and exit the application
    private static void showAlertAndExit() {
        // Initialize JavaFX runtime without starting the application
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Network Error");
            alert.setHeaderText("No Network Connection");
            alert.setContentText("The application requires an internet connection to work. Please check your connection and try again.");
            alert.showAndWait();
            Platform.exit();
            System.exit(0);  // Ensure the application exits
        });
    }

    // Method to check if network is available
    private static boolean isNetworkAvailable() {
        try {
            InetAddress inetAddress = InetAddress.getByName("www.hiruna.dev");
            return inetAddress.isReachable(5000); // Timeout of 5 seconds
        } catch (IOException e) {
            return false;
        }
    }
}
