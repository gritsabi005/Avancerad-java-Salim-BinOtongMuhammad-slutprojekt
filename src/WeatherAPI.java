import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import org.json.simple.JSONArray;

public class WeatherAPI {
    private static final String apiKey = "6644bf41da75738bfb18480baf46f669";
    private static final String weatherAPIURL = "https://api.openweathermap.org/data/2.5/forecast";

    /*To take the API you need to;
    Create the URL and send the reuqest,
    Check the response (make the code with catching an error),
    Read the response and parse the Json, (you use the StringBuilder here)
    For Loop to see the Json and take what you want,
    */
    public static Map<LocalDate, String> getWeatherForecast(String city) {
        Map<LocalDate, String> forecastMap = new HashMap<>();
        try {
            // Creating the URL and the request

            // You created the URL here and send the request to weatherAPI
            String theCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            URL url = new URL(weatherAPIURL + "?q=" + theCity + "&appid=" + /*consists of APIKEY here*/
                    apiKey + /*metric system*/"&units=metric");

            //creating the Http connection using the URL above (that consists of which city the user wants to search)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //creating the GET for the data retrieved later on
            connection.setRequestMethod("GET");

            //connecting to server
            connection.connect();

            //Check the response (make the code with catching an error)

            //Checking the response, if the response is NOT 200, that means there is a problem fecthing the data
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error fetching weather: HTTP response code " + responseCode);
                return forecastMap;
            }

            //Read the response and parse the Json, (you use the StringBuilder here)

            //Creating StringBuilder that consists of the data from the weatherAPI server
            StringBuilder data = new StringBuilder(); /*you save the data in the String here which will be read by
                                                        scanner below*/
            Scanner scanner = new Scanner(url.openStream());/*openStream is the stream that is given from weather API*/

            while (scanner.hasNext()) {
                data.append(scanner.nextLine());
            }
            scanner.close();

            //Parsing the Json, taking the weather data
            JSONParser parser = new JSONParser();
            //making jsonData (String) to be jsonObject
            JSONObject jasonData = (JSONObject) parser.parse(data.toString());
            //making jsonObject into an Array to be later used to reiterate and find the dates and weather
            JSONArray list = (JSONArray) jasonData.get("list");

            //For Loop to see the Json and take what you want,

            for (Object serverDatafromWeatherAPI : list){
                JSONObject theList = (JSONObject) serverDatafromWeatherAPI;
                //take the info here
                forecastMap.put()
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return forecastMap;
    }

}