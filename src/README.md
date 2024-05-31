# WeatherPulse (Your Daily Weather Companion)

![WeatherPulse](https://telegra.ph/file/fc20e5118e9007d72e0e0.png)

WeatherPulse is a JavaFX application designed to provide up-to-date weather information based on the user's location. Utilizing the OpenWeather API, WeatherPulse retrieves real-time weather data and presents it in a user-friendly interface. Users can access essential weather metrics such as weather condition, temperature, humidity, wind speed, and direction, along with an hourly forecast to plan their day effectively.

## Features

- Real-time weather updates including weather condition, temperature, humidity, wind speed, and direction.
- Hourly weather forecast with detailed information.
- Display of the user's current city and country based on their IP address.
- Automatic live time and date updates.
- Network connectivity check before launching the application.

## Screenshots

![WeatherPulse Screenshot](https://telegra.ph/file/a3bf2f15534df96ed574f.png)

## Installation

To run WeatherPulse locally, follow these steps:

1. **Clone the repository:**

   ```sh
   git clone https://github.com/yourusername/weatherpulse.git
   cd weatherpulse

2 **Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).**
3. **Add JavaFX library to your project:**

    - Download JavaFX SDK from [Gluon](https://gluonhq.com/products/javafx/).

4. **Set up JavaFX in your IDE:**

    - **For IntelliJ IDEA:**
        - Go to `File` -> `Project Structure` -> `Libraries` and add the JavaFX SDK library.
    - **For Eclipse:**
        - Right-click on your project -> `Build Path` -> `Configure Build Path` and add the JavaFX SDK library.

5. **Run the Application:**

    - Execute the main method in the `WeatherPulseApplication` class.


## Usage

- Launch the application.
- WeatherPulse will check for network connectivity.
- Upon successful launch, the current weather data and a 3-hourly forecast will be displayed.
- The application will also show the current date and time, which updates in real-time.

### Configuration

Ensure you have valid API keys for OpenWeather and GeoLocation APIs. Replace the placeholder API keys in the `WeatherPulseController` class with your own keys:

```java
// OpenWeather API Key
String weatherApiKey = "your_openweather_api_key";

// GeoLocation API Key
String geoApiKey = "your_ipgeolocation_api_key";
```

### Video Background

The video background file `bgWeatherPulseRain.mp4` should be placed in the resources directory. You can replace this video with any other video of your choice.

### License

This project is licensed under the GNU General Public License v3.0 License - see the LICENSE file for details.

### Acknowledgements

- [OpenWeather](https://openweathermap.org/) for providing the weather data API.
- [IP Geolocation](https://ipgeolocation.io/) for providing the IP-based location data.
- JavaFX community for their extensive documentation and support.

### Contributing

Contributions are welcome! Please fork this repository and submit a pull request for any improvements or bug fixes.

### Contact

- Author: GD Hiruna
- Website: [hiruna.dev](https://hiruna.dev)
- Email: [hello@hiruna.dev](mailto:hello@hiruna.dev)