// ==========================================
//  Title:  ShowRestaurantsActivity
//  Author: James Kelsey
//  Date:   15/03/2020
// ==========================================

package com.example.glutenfreefoodfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class ShowRestaurantsActivity extends AppCompatActivity {

    // List to store restaurants
    private ArrayList<Restaurant> restaurants;
    // Name for log entry
    private static final String LOG_TAG = "ShowRestaurants";
    // Radius of restaurant search
    private static final int RADIUS = 8700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurants);

        // Gets long/lat passed through intent
        Intent intent = getIntent();
        String longitude = intent.getStringExtra("long");
        String latitude = intent.getStringExtra("lat");

        // Use main thread to make call
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Double lng = Double.parseDouble(longitude);
        Double lat = Double.parseDouble(latitude);

        // Gets list of restaurants
        restaurants = search(lat, lng, RADIUS);

        if (restaurants != null) {
            // OnClick for back button
            Button backButton = findViewById(R.id.showRestaurantsBackButton);
            backButton.setOnClickListener(view -> onBackPressed());

            // Add list adapter and adjust list
            ListView showRestaurantsListView = findViewById(R.id.listView);
            ShowRestaurantsAdapter adapter = new ShowRestaurantsAdapter(this, R.layout.show_restaurants_adapter, restaurants);
            showRestaurantsListView.setAdapter(adapter);

            showRestaurantsListView.setHeaderDividersEnabled(true);
            showRestaurantsListView.setFooterDividersEnabled(true);
            showRestaurantsListView.addFooterView(new View(this), null, true);
            showRestaurantsListView.addHeaderView(new View(this), null, true);

            // Listens for list element click, passes restaurant id through to next activity
            showRestaurantsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                    Intent restaurantInfo = new Intent(ShowRestaurantsActivity.this, GetRestaurantActivity.class);
                    String restaurantID = restaurants.get(position - 1).getID();
                    restaurantInfo.putExtra("restaurantID", restaurantID);

                    startActivity(restaurantInfo);
                }
            });
        }
    }

    // Method to sort data
    public ArrayList<Restaurant> formatResults(ArrayList<Restaurant> restaurants) {

        // Remove unwanted restaurants from search
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).toString().contains("Porcellis") ||
                    restaurants.get(i).toString().contains("The Great British Cupcakery Ltd") ||
                        restaurants.get(i).toString().contains("Jakki's Chinese Takeaway") ||
                            restaurants.get(i).toString().contains("Subway")) {

                restaurants.remove(restaurants.get(i));
            }
        }

        // Remove last 23 results from list as they are duplicates for some reason
        restaurants.subList(restaurants.size() - 26, restaurants.size() - 1).clear();
        restaurants.remove(restaurants.size() - 1);

        // Orders restaurants by name
        Collections.sort(restaurants, (s1, s2) -> s1.toString().compareToIgnoreCase(s2.toString()));

        return restaurants;
    }

    // Method to send request and get response
    public String searchResults (double lat, double lng, int rad, String nextPageToken) {

        // Values to be appended to base url
        final String TYPE_SEARCH = "/nearbysearch";
        final String OUT_JSON = "/json?";
        final String PLACE_TYPE_RESTAURANT = "restaurant";

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            // Builds up request url
            StringBuilder string = new StringBuilder(getResources().getString(R.string.places_api_base));
            string.append(TYPE_SEARCH);
            string.append(OUT_JSON);

            // Check if there is another page to load
            if (nextPageToken == null || nextPageToken.equals("")) {
                string.append("location=" + lat + "," + lng);
                string.append("&radius=" + rad);
                string.append("&type=" + PLACE_TYPE_RESTAURANT);
                string.append("&keyword=gluten");

            } else {
                string.append("pagetoken=" + nextPageToken);
            }

            string.append("&key=" + getResources().getString(R.string.api_key));

            // Creates URL from string and makes request
            URL url = new URL(string.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Reads back the results using buffer and appends to result string
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }

            // Close input stream reader
            in.close();

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, getResources().getString(R.string.malformed_excpetion_error), e);
            return null;
        } catch (IOException e) {
            Log.e(LOG_TAG, getResources().getString(R.string.io_exception_error), e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return jsonResults.toString();
    }

    // Method to parse results
    public ArrayList<Restaurant> search(double lat, double lng, int rad) {
        ArrayList<Restaurant> resultList = null;

        try {
            String resultString;
            String secondPageToken, thirdPageToken;

            // Get result string from request
            resultString = searchResults(lat, lng, rad, null);

            // Gets results JSON array
            JSONObject firstJsonObj = new JSONObject(resultString);
            JSONArray firstJsonArray = firstJsonObj.getJSONArray("results");

            secondPageToken = firstJsonObj.optString("next_page_token");

            // If there's a second page, make second call and add results to first JSON array
            if(secondPageToken != null || !secondPageToken.equalsIgnoreCase("")) {
                    Thread.sleep(1500);
                    resultString = searchResults(lat, lng, rad, secondPageToken);
                    JSONObject secondJsonObj = new JSONObject(resultString);
                    JSONArray secondJsonArray = secondJsonObj.getJSONArray("results");

                    for (int i = 0; i < secondJsonArray.length(); i++){
                        firstJsonArray.put(secondJsonArray.getJSONObject(i));
                    }
            }

            JSONObject secondJsonObj = new JSONObject(resultString);
            thirdPageToken = secondJsonObj.optString("next_page_token");

            // If there's a third page, make third call and add results to first JSON array
            if (thirdPageToken != null || !thirdPageToken.equalsIgnoreCase("")) {
                    Thread.sleep(1500);
                    resultString = searchResults(lat, lng, rad, thirdPageToken);
                    JSONObject thirdJsonObj = new JSONObject(resultString);
                    JSONArray thirdJsonArray = thirdJsonObj.getJSONArray("results");

                    for (int j = 0; j < thirdJsonArray.length(); j++){
                        firstJsonArray.put(thirdJsonArray.getJSONObject(j));
                    }
            }

            resultList = new ArrayList<Restaurant>(firstJsonArray.length());

            // Iterate through JSON array, create new restaurant for each and set info
            for (int k = 0; k < firstJsonArray.length(); k++) {
                Restaurant restaurant = new Restaurant();
                restaurant.setID(firstJsonArray.getJSONObject(k).getString("place_id"));
                restaurant.setName(firstJsonArray.getJSONObject(k).getString("name"));
                restaurant.setRating(firstJsonArray.getJSONObject(k).getDouble("rating"));
                restaurant.setVicinity(firstJsonArray.getJSONObject(k).getString("vicinity"));

                Boolean hasHours = firstJsonArray.getJSONObject(k).has("opening_hours");

                if (hasHours) {
                    Boolean isOpen = firstJsonArray.getJSONObject(k).getJSONObject("opening_hours").getBoolean("open_now");
                    restaurant.setIsOpenNow(isOpen);

                } else {
                    restaurant.setIsOpenNow(null);
                }

                resultList.add(restaurant);

            }
            // Format results appropriately before returning
            resultList = formatResults(resultList);

        } catch (JSONException e) {
            Log.e(LOG_TAG, getResources().getString(R.string.json_exception_error), e);
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, getResources().getString(R.string.interrupted_exception_error), e);
        }
        return resultList;
    }
}
