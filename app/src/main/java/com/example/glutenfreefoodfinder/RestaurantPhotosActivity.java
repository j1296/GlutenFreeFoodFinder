// ==========================================
//  Title:  RestaurantPhotosActivity
//  Author: James Kelsey
//  Date:   15/03/2020
// ==========================================

package com.example.glutenfreefoodfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

public class RestaurantPhotosActivity extends AppCompatActivity {

    // Name for log entry
    private static final String LOG_TAG = "RestaurantPhotos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_photos);

        // Get place ID from previous activity
        final Intent intent = getIntent();
        final String id = intent.getStringExtra("placeID");

        // Make call on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Initialise places
        Places.initialize(getApplicationContext(), getResources().getString(R.string.api_key));

        // Create a new places client instance
        PlacesClient placesClient = Places.createClient(this);

        ImageView firstImage = findViewById(R.id.restaurantFirstImage);
        ImageView secondImage = findViewById(R.id.restaurantSecondImage);
        ImageView thirdImage = findViewById(R.id.restaurantThirdImage);
        ImageView fourthImage = findViewById(R.id.restaurantFourthImage);
        ImageView fifthImage = findViewById(R.id.restaurantFifthImage);

        ArrayList<ImageView> images = new ArrayList<ImageView>();
        images.add(firstImage);
        images.add(secondImage);
        images.add(thirdImage);
        images.add(fourthImage);
        images.add(fifthImage);

        // OnClick for back button
        Button backButton = findViewById(R.id.restaurantPhotosBackButton);
        backButton.setOnClickListener(view -> onBackPressed());

        // Search method to get/set images
        search(id, placesClient, images);
    }

    public void search(String id, PlacesClient placesClient, ArrayList<ImageView> images){

        List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);

        // Build up FetchPlace request
        FetchPlaceRequest placeRequest = FetchPlaceRequest.builder(id, fields).build();

        placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
            Place place = response.getPlace();

            // Get the metadata of each photo
            PhotoMetadata firstPhotoMetadata = place.getPhotoMetadatas().get(0);
            PhotoMetadata secondPhotoMetadata = place.getPhotoMetadatas().get(1);
            PhotoMetadata thirdPhotoMetadata = place.getPhotoMetadatas().get(2);
            PhotoMetadata fourthPhotoMetadata = place.getPhotoMetadatas().get(3);
            PhotoMetadata fifthPhotoMetadata = place.getPhotoMetadatas().get(4);

            // Create FetchPhotoRequest requests
            FetchPhotoRequest firstPhotoRequest = FetchPhotoRequest.builder(firstPhotoMetadata)
                    .build();

            FetchPhotoRequest secondPhotoRequest = FetchPhotoRequest.builder(secondPhotoMetadata)
                    .build();

            FetchPhotoRequest thirdPhotoRequest = FetchPhotoRequest.builder(thirdPhotoMetadata)
                    .build();

            FetchPhotoRequest fourthPhotoRequest = FetchPhotoRequest.builder(fourthPhotoMetadata)
                    .build();

            FetchPhotoRequest fifthPhotoRequest = FetchPhotoRequest.builder(fifthPhotoMetadata)
                    .build();

            // Fetch photo, get bitmap and set imageview
            placesClient.fetchPhoto(firstPhotoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                Bitmap b = fetchPhotoResponse.getBitmap();
                images.get(0).setImageBitmap(b);
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    Log.e(LOG_TAG, "First photo not found: " + exception.getMessage());
                }
            });

            placesClient.fetchPhoto(secondPhotoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                Bitmap b = fetchPhotoResponse.getBitmap();
                images.get(1).setImageBitmap(b);
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    Log.e(LOG_TAG, "Second photo not found: " + exception.getMessage());
                }
            });

            placesClient.fetchPhoto(thirdPhotoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                Bitmap b = fetchPhotoResponse.getBitmap();
                images.get(2).setImageBitmap(b);
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    Log.e(LOG_TAG, "Third photo not found: " + exception.getMessage());
                }
            });

            placesClient.fetchPhoto(fourthPhotoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                Bitmap b = fetchPhotoResponse.getBitmap();
                images.get(3).setImageBitmap(b);
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    Log.e(LOG_TAG, "Fourth photo not found: " + exception.getMessage());
                }
            });

            placesClient.fetchPhoto(fifthPhotoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                Bitmap b = fetchPhotoResponse.getBitmap();
                images.get(4).setImageBitmap(b);
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    Log.e(LOG_TAG, "Fifth photo not found: " + exception.getMessage());
                }
            });
        });
    }
}
