package dev.hiruna.weatherpulse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.awt.*;
import java.util.List;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherPulseController {

    // OpenWeather API Key
    private String weatherApiKey = "a4ce8b1e082027cbc54abcfcd8549375";
    // GeoLocation API Key
    private String geoApiKey = "5e1d1f5e9b894ddaabc5a23d3ebf064c";

    @FXML
    private AnchorPane bgWeatherPulse;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtDirection;

    @FXML
    private Label txtFeels;

    @FXML
    private Label txtForecastDate1;

    @FXML
    private Label txtForecastDate2;

    @FXML
    private Label txtForecastDate3;

    @FXML
    private Label txtForecastDesc1;

    @FXML
    private Label txtForecastDesc2;

    @FXML
    private Label txtForecastDesc3;

    @FXML
    private Label txtHumidity;

    @FXML
    private Label txtLocation;

    @FXML
    private Label txtMinHigh;

    @FXML
    private Label txtPressure;

    @FXML
    private Label txtTemp;

    @FXML
    private Label txtTime;

    @FXML
    private Label txtWeather;

    @FXML
    private Label txtWind;

    @FXML
    void openURL(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://hiruna.dev/"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

    public void initialize() {
        setupVideoBackground();
        updateTime();
        startLiveTimeUpdate();
        updateWeatherData();
        setForecastData();
    }

    private void setupVideoBackground() {
        // Load the video file
        String videoPath = "bgWeatherPulseRain.mp4";
        Media media = new Media(getClass().getResource(videoPath).toExternalForm());

        // Create a MediaPlayer
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely

        // Create a MediaView to display the video
        MediaView mediaView = new MediaView(mediaPlayer);

        // Set the size of the MediaView to match the size of the AnchorPane
        mediaView.fitWidthProperty().bind(bgWeatherPulse.widthProperty());
        mediaView.fitHeightProperty().bind(bgWeatherPulse.heightProperty());

        // Add the MediaView to the AnchorPane
        bgWeatherPulse.getChildren().add(mediaView);

        // Send the MediaView to the back
        mediaView.toBack();
    }

    private void startLiveTimeUpdate() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> updateTime()),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTime() {
        String currentTime = LocalDateTime.now().format(timeFormatter);
        txtTime.setText(currentTime);
    }

    private void updateWeatherData() {
        // Fetch public IP address
        String publicIP = IPFetcher.getPublicIP();
        if (publicIP != null) {
            // If public IP is fetched successfully, get latitude and longitude
            double[] latLon = getLatitudeLongitude(publicIP);
            if (latLon != null) {
                // Fetch weather data
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latLon[0] + "&lon=" + latLon[1] + "&units=metric&appid="+weatherApiKey;

                try {
                    WeatherData weatherData = WeatherData.fetchWeatherData(apiUrl);
                    setWeatherData(weatherData, publicIP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to fetch latitude and longitude.");
            }
        } else {
            System.out.println("Failed to fetch public IP address.");
        }
    }

    private double[] getLatitudeLongitude(String publicIP) {
        GeoLocation geoLocation = new GeoLocation(publicIP, geoApiKey);
        return geoLocation.getLatitudeLongitude();
    }

    private void setWeatherData(WeatherData weatherData, String publicIP) {

        txtHumidity.setText(weatherData.getHumidity() + "%");
        txtPressure.setText(weatherData.getPressure() + " hPa");
        txtWind.setText(weatherData.getWindSpeed() + " m/s");
        txtDirection.setText("Direction - " + weatherData.getWindDirection() + "°");

        //Weather Condition
        String[] words = weatherData.getDescription().split("\\s+");
        StringBuilder weatherConditionBuilder = new StringBuilder();
        for (String word : words) {
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            weatherConditionBuilder.append(capitalizedWord).append(" ");
        }
        String weatherCondition = weatherConditionBuilder.toString().trim().replace(" ", "\n");

        txtLocation.setText(getCityAndCountry(publicIP));
        txtWeather.setText(weatherCondition);
        txtFeels.setText("Feels like " + weatherData.getFeelsLike() + "°C");
        txtTemp.setText(weatherData.getTemp() + "°C");
        txtMinHigh.setText("Min - " + weatherData.getTempMin()+"°C / Max - " + weatherData.getTempMax()+"°C");
        txtHumidity.setText(weatherData.getHumidity() + "%");
        txtPressure.setText(weatherData.getPressure() + " hPa");
        txtWind.setText(weatherData.getWindSpeed() + " m/s");
        txtDirection.setText("Direction - " + weatherData.getWindDirection() + "° (" + getDirectionName(weatherData.getWindDirection()) + ")");
    }

    private String getCityAndCountry(String publicIP) {
        GeoLocation geoLocation = new GeoLocation(publicIP, geoApiKey);
        return geoLocation.getCityAndCountry();
    }

    private String getDirectionName(int windDirection) {
        String[] directions = {"North", "Northeast", "East", "Southeast", "South", "Southwest", "West", "Northwest"};
        int index = (int) Math.round((windDirection % 360) / 45.0);
        return directions[index % 8];
    }

    private void setForecastData() {
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=-11.8092&lon=51.509865&units=metric&appid=" + weatherApiKey;

        try {
            List<ForecastData> forecasts = ForecastData.getForecast(apiUrl);

            // Check if we have at least 3 forecast data points
            if (forecasts.size() >= 3) {
                // Extract the required forecast data
                ForecastData forecast1 = forecasts.get(0);
                ForecastData forecast2 = forecasts.get(1);
                ForecastData forecast3 = forecasts.get(2);

                // Update the text fields with actual data
                txtForecastDate1.setText(forecast1.getDateTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy\nHH:mm")));
                txtForecastDesc1.setText(String.format("Temperature: %.2f°C\nFeels Like: %.2f°C\nWeather: %s\nHumidity: %d%%\nWind: %.2f m/s, gusts up to %.2f m/s",
                        forecast1.getTemperature(), forecast1.getFeelsLike(), forecast1.getWeather(), forecast1.getHumidity(), forecast1.getWindSpeed(), forecast1.getWindGusts()));

                txtForecastDate2.setText(forecast2.getDateTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy\nHH:mm")));
                txtForecastDesc2.setText(String.format("Temperature: %.2f°C\nFeels Like: %.2f°C\nWeather: %s\nHumidity: %d%%\nWind: %.2f m/s, gusts up to %.2f m/s",
                        forecast2.getTemperature(), forecast2.getFeelsLike(), forecast2.getWeather(), forecast2.getHumidity(), forecast2.getWindSpeed(), forecast2.getWindGusts()));

                txtForecastDate3.setText(forecast3.getDateTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy\nHH:mm")));
                txtForecastDesc3.setText(String.format("Temperature: %.2f°C\nFeels Like: %.2f°C\nWeather: %s\nHumidity: %d%%\nWind: %.2f m/s, gusts up to %.2f m/s",
                        forecast3.getTemperature(), forecast3.getFeelsLike(), forecast3.getWeather(), forecast3.getHumidity(), forecast3.getWindSpeed(), forecast3.getWindGusts()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
