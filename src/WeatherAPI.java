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
    - Create the URL and send the reuqest,
    - Check the response (make the code with catching an error),
    - Read the response and parse the Json, (you use the StringBuilder here)
    - For Loop to see the Json and take what you want,
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
                                                        scanner below*/ //basically like String
            Scanner scanner = new Scanner(url.openStream());/*openStream is the stream that is given from weather API*/
            while (scanner.hasNext()) {
                data.append(scanner.nextLine());
                //System.out.println(data);
            }
            scanner.close();

            //Parsing the Json, taking the weather data
            JSONParser parser = new JSONParser();
            //making jasonObject (String from the data from the StringBuilder) to be jsonData
            JSONObject jasonData = (JSONObject) parser.parse(data.toString());

            //System.out.println(jasonData);
            //making jsonObject into an Array to be later used to reiterate and find the dates and weather
            JSONArray list = (JSONArray) jasonData.get("list");

            //System.out.println(list);
            //For Loop to see the Json and take what you want,
            for (Object serverDatafromWeatherAPI : list){
                JSONObject theList = (JSONObject) serverDatafromWeatherAPI;
                //take the info here
                //Will have to look at the WeatherAPI for all the codes

                String date = (String) theList.get("dt_txt");
                LocalDate dates = LocalDate.parse(date.split(" ")[0]); //You take away the hours from the date
                System.out.println(dates);
                //String time = (String) theList.get("dt_txt");
                //LocalDate time = LocalDate.parse(time.split(" ")[0]);
                String highestTemperature = date.split(" ")[1];
                System.out.println(highestTemperature);
                //System.out.print(dates);
                //System.out.print(date);
                //Because temperature is in the main array, so you take the main array first
                JSONObject main = (JSONObject) theList.get("main");
                String celcius = main.get("temp") + " Degrees";

                forecastMap.put(dates, celcius);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return forecastMap;
    }

    // Creating another method for the funFact part from WeatherAPI
    public static class CityInfo{
        private String name;
        private String country;
        private long population;

        public CityInfo(String name, String country, long population){
            this.name = name;
            this.country = country;
            this.population = population;
        }

        public String getName(){
            return name;
        }
        public String getCountry(){
            return country;
        }
        public long getPopulation(){
            return population;
        }

        public static CityInfo parsingTheData (JSONObject jsonData){ // JSONObject as the parameter

            JSONObject cityInformation = (JSONObject) jsonData.get("city");
            String cityName = (String) cityInformation.get("name");
            String country = (String) cityInformation.get("country");
            Long population = (Long) cityInformation.get("population");

            return new CityInfo(cityName, country, population);

        }
    }
    /*To take the API you need to;
    - Create the URL and send the reuqest, (openweather API and its key), send the request with Http
    - Check the response (make the code with catching an error), creating a net
    - Read the response and parse the Json, (while NextLine it with StringBuilder, the use JSON parse)
    - For Loop to see the Json and take what you want,
    */
    //pass the URL into scanner, scanner filling in the data (while hasNext, which will be used in JSONObject

    public static CityInfo getCityInformation (String city){
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
                return null;
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

            System.out.println(jasonData);


            JSONObject cityinfo = (JSONObject) jasonData.get("city");
            String cityChosen = (String) cityinfo.get("name");
            String countryOfTheCity = (String) cityinfo.get("country");
            Long population = (Long) cityinfo.get("population"); //Long to prepare if the int is bigger than 2.1 Billions

            return new CityInfo(cityChosen, countryOfTheCity, population);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}