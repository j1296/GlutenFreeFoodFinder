// ==========================================
//  Title:  GetRestaurantActivity
//  Author: James Kelsey
//  Date:   15/03/2020
// ==========================================

package com.example.glutenfreefoodfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;

public class GetRestaurantActivity extends AppCompatActivity {

    private Restaurant restaurant;

    private final int MAKE_CALL = 1;
    // Name for log entry
    private static final String LOG_TAG = "GetRestaurants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_restaurant);

        // Gets restaurant ID from previous activity
        final Intent intent = getIntent();
        final String restaurantID = intent.getStringExtra("restaurantID");

        // Make calls on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Get restaurant
        restaurant = search(restaurantID);

        if (restaurant != null) {

            // Sets text, underline and icon link
            TextView iconsLink = findViewById(R.id.icons8Link);
            SpannableString content = new SpannableString(getResources().getString(R.string.icons8_text));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            iconsLink.setText(content);

            // onClick for icons link
            iconsLink.setOnClickListener(v -> {
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                websiteIntent.setData(Uri.parse(getResources().getString(R.string.icons8_url)));
                startActivity(websiteIntent);
            });

            // Sets restaurant price level
            TextView restaurantPriceLevel = findViewById(R.id.getRestaurantPriceLevel);
            if (restaurantPriceLevel != null) {
                restaurantPriceLevel.setText(String.valueOf(restaurant.getPriceLevel()).equalsIgnoreCase("0") ? "Free" :
                        String.valueOf(restaurant.getPriceLevel()).equalsIgnoreCase("1") ? "£" :
                                String.valueOf(restaurant.getPriceLevel()).equalsIgnoreCase("2") ? "££" :
                                        String.valueOf(restaurant.getPriceLevel()).equalsIgnoreCase("3") ? "£££" :
                                                String.valueOf(restaurant.getPriceLevel()).equalsIgnoreCase("4") ? "££££" :
                                                        "");
            }

            // onClick for back button
            Button backButton = findViewById(R.id.getRestaurantBackButton);
            backButton.setOnClickListener(view -> onBackPressed());

            // onClick for photo's button, passes through place ID and starts new activity
            Button photosButton = findViewById(R.id.getRestaurantPhotosButton);
            photosButton.setOnClickListener(view -> {
                Intent restaurantID1 = new Intent(GetRestaurantActivity.this, RestaurantPhotosActivity.class);
                restaurantID1.putExtra("placeID", restaurant.getID());
                startActivity(restaurantID1);
            });

            // Adds restaurant to list for listview adapters
            ArrayList<Restaurant> restaurantsList = new ArrayList<Restaurant>();
            restaurantsList.add(restaurant);

            // View for header
            ListView headerView = findViewById(R.id.getRestaurantListView);
            GetRestaurantHeaderAdapter listAdapter = new GetRestaurantHeaderAdapter(this, R.layout.get_restaurant_header_adapter, restaurantsList);
            headerView.setAdapter(listAdapter);

            //View and onClick for phone
            ListView phoneView = findViewById(R.id.getRestaurantPhoneNumber);
            GetRestaurantPhoneAdapter phoneAdapter = new GetRestaurantPhoneAdapter(this, R.layout.get_restaurant_phone_adapter, restaurantsList);
            phoneView.setAdapter(phoneAdapter);
            phoneView.setOnItemClickListener((adapter, v, position, id) -> {

                // Build phone number and pass to an intent
                String number = "tel:" + restaurant.getPhoneNumber();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(number));

                // If hasn't got permission, request permissions else make the call.
                if (ContextCompat.checkSelfPermission(GetRestaurantActivity.this, CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(GetRestaurantActivity.this, new String[]{ CALL_PHONE }, MAKE_CALL);
                } else {
                    startActivity(callIntent);
                }
            });

            // View and onClick for website
            ListView websiteView = findViewById(R.id.getRestaurantWebsite);
            GetRestaurantWebsiteAdapter websiteAdapter = new GetRestaurantWebsiteAdapter(this, R.layout.get_restaurant_website_adapter, restaurantsList);
            websiteView.setAdapter(websiteAdapter);
            websiteView.setOnItemClickListener((adapter, v, position, id) -> {

                // Gets website and passes to an intent
                String url = restaurant.getWebsite();
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                websiteIntent.setData(Uri.parse(url));
                startActivity(websiteIntent);
            });

            // View and onClick for directions
            ListView directionsView = findViewById(R.id.getRestaurantDirections);
            GetRestaurantDirectionsAdapter directionsAdapter = new GetRestaurantDirectionsAdapter(this, R.layout.get_restaurant_directions_adapter, restaurantsList);
            directionsView.setAdapter(directionsAdapter);
            directionsView.setOnItemClickListener((adapter, v, position, id) -> {
                String mapsBase = getResources().getString(R.string.maps_api_base);
                double lat = restaurant.getLatitude();
                double lng = restaurant.getLongitude();

                // Build up Google maps url
                StringBuilder mapUrl = new StringBuilder(mapsBase);
                mapUrl.append("&daddr=" + lat + "," + lng);

                String url = mapUrl.toString();

                // Store in an intent and start maps activity
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(url));
                startActivity(intent1);
            });

            // View and onClick for more info
            ListView infoView = findViewById(R.id.getRestaurantInfo);
            GetRestaurantInfoAdapter infoAdapter = new GetRestaurantInfoAdapter(this, R.layout.get_restaurant_info_adapter, restaurantsList);
            infoView.setAdapter(infoAdapter);
            infoView.setOnItemClickListener((adapter, v, position, id) -> {

                Intent intent12 = new Intent(GetRestaurantActivity.this, RestaurantInfoActivity.class);

                String[] gfFeatures = restaurant.getGlutenFreeFeatures();
                String[] times = restaurant.getOpeningTimes();

                // Pass gf features and opening times to info activity and start
                intent12.putExtra("features", gfFeatures);

                if (times != null){
                    intent12.putExtra("times", times);
                }else {
                    intent12.putExtra("times", new String[]{getResources().getString(R.string.no_opening_information)});
                }

                startActivity(intent12);
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 ) {

            // If has permission, build up call otherwise request permissions again
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                String phone = "tel:" + restaurant.getPhoneNumber();
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse(phone));
                startActivity(call);
            } else {
                ActivityCompat.requestPermissions(GetRestaurantActivity.this, new String[]{CALL_PHONE}, MAKE_CALL);
            }
        }
    }

    // Method to make places request and get string results
    public String searchResults (String id){
        final String TYPE_DETAILS = "/details";
        final String OUT_JSON = "/json?";

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            // Build up request url
            StringBuilder detailsSearch = new StringBuilder(getResources().getString(R.string.places_api_base));
            detailsSearch.append(TYPE_DETAILS);
            detailsSearch.append(OUT_JSON);
            detailsSearch.append("place_id=" + id);
            detailsSearch.append("&key=" + getResources().getString(R.string.api_key));

            // Set up http connection and make request
            URL detailsUrl = new URL(detailsSearch.toString());
            conn = (HttpURLConnection) detailsUrl.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read response and store using buffer
            int read;
            char[] buff = new char[1024];

            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }

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

    // Method to set crowdources gluten free info
    public Restaurant setGlutenFreeFeatures(Restaurant restaurant) {

        Restaurant r = restaurant;
        String name = restaurant.toString();

        // Set crowdsourced info
        switch (name) {
            case "TGI Fridays":
            case "Cote Brasserie":
            case "Mantra Thai Dining Restaurant":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Menu"});
                break;
            case "Zizzi":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Menu", "Pizza", "Desserts"});
                break;
            case "ASK Italian":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options", "Desserts"});
                break;
            case "Central Oven & Shaker":
            case "Pizzeria Francesca":
            case "Starks Kitchen":
            case "Sky Apple Cafe":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options"});
                break;
            case "Fat Hippo":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Menu", "Chips", "Rolls"});
                break;
            case "Frankie & Benny's":
            case "Quay Ingredient":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options", "Bread"});
                break;
            case "Landmark":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Menu", "Chicken Dishes"});
                break;
            case "Mascalzone":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options", "Mains", "Desserts"});
                break;
            case "Miller & Carter":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Menu", "Chips", "Bread"});
                break;
            case "Olive & Bean":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options", "Cakes", "Bread"});
                break;
            case "Piazza Latina Restaurant":
            case "Pizza Punks":
            case "Prima Restaurant":
                r.setGlutenFreeFeatures(new String[]{"Pizza Base"});
                break;
            case "Pizza Express":
            case "Sorella Sorella":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options", "Pizza Base", "Desserts"});
                break;
            case "Sale Pepe":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options", "Pasta"});
                break;
            case "Sambucas":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options", "Pizza Base"});
                break;
            case "Smashburger":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options", "Buns"});
                break;
            case "Tapas Revolution":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Options", "Tapas"});
                break;
            case "The Purple Bear":
                r.setGlutenFreeFeatures(new String[]{"Gluten Free Menu", "Buns"});
                break;
            default:
                r.setGlutenFreeFeatures(new String[]{getResources().getString(R.string.no_gluten_information)});
                break;
        }
        return r;
    }

    // Method to return selected restaurant
    public Restaurant search(String id) {
        Restaurant restaurant = null;

        try {
            // Make call and get results
            String resultString = searchResults(id);

            // Get appropriate JSON object
            JSONObject detailsJsonObj = new JSONObject(resultString);
            final JSONObject detailsJson = detailsJsonObj.getJSONObject("result");

            // Create new restaurant object and populate
            restaurant = new Restaurant();

            restaurant.setID(detailsJson.getString("place_id"));
            restaurant.setName(detailsJson.getString("name"));
            restaurant.setAddress(detailsJson.getString("formatted_address"));
            restaurant.setPriceLevel(detailsJson.optInt("price_level", -1));
            restaurant.setRating(detailsJson.getDouble("rating"));
            restaurant.setWebsite(detailsJson.optString("website", ""));
            restaurant.setPhoneNumber(detailsJson.getString("formatted_phone_number"));

            final JSONObject location = detailsJson.getJSONObject("geometry").getJSONObject("location");
            restaurant.setLatitude(location.getDouble("lat"));
            restaurant.setLongitude(location.getDouble("lng"));

            // Get/set restaurant open now
            Boolean hasHours = detailsJson.has("opening_hours");

            if (hasHours) {
                Boolean isOpen = detailsJson.getJSONObject("opening_hours").getBoolean("open_now");
                restaurant.setIsOpenNow(isOpen);

            } else {
                restaurant.setIsOpenNow(null);
            }

            // Get/set restaurant opening times
            JSONObject openingHours = detailsJson.optJSONObject("opening_hours");

            if (openingHours != null) {
                JSONArray openingTimes = openingHours.optJSONArray("weekday_text");

                if (openingTimes != null) {
                    String[] temp = new String[openingTimes.length()];

                    for (int i = 0; i < openingTimes.length(); i++) {
                        temp[i] = openingTimes.getString(i);
                    }
                    restaurant.setOpeningTimes(temp);
                } else {
                    restaurant.setOpeningTimes(new String[]{getResources().getString(R.string.no_opening_information)});
                }
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, getResources().getString(R.string.json_exception_error), e);
        }

        // Set gluten free features
        if (restaurant != null){
            restaurant = setGlutenFreeFeatures(restaurant);
        }

        return restaurant;
    }
}
