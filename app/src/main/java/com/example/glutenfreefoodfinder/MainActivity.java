// ==========================================
//  Title:  Main Activity
//  Author: James Kelsey
//  Date:   15/03/2020
// ==========================================

package com.example.glutenfreefoodfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button_rounded_show_restaurants);
        button.setOnClickListener(view -> startRestaurants());

    }

    // Passes long/lat through an intent to the next activity
    public void startRestaurants() {
        Intent intent = new Intent(MainActivity.this, ShowRestaurantsActivity.class);

        // lat/long for st james park
        Double longitude = -1.621873666214279;
        Double latitude = 54.9754693;

        String longit = Double.toString(longitude);
        String lat = Double.toString(latitude);

        intent.putExtra("long", longit);
        intent.putExtra("lat", lat);

        startActivity(intent);
    }
}
