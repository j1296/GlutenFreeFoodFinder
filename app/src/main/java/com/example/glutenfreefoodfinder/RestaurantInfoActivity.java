// ==========================================
//  Title:  RestaurantInfoActivity
//  Author: James Kelsey
//  Date:   15/03/2020
// ==========================================

package com.example.glutenfreefoodfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class RestaurantInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        // Get info passed from GetRestaurant
        Intent intent = getIntent();
        String[] gfFeatures = intent.getStringArrayExtra("features");
        String[] times = intent.getStringArrayExtra("times");

        // onClick for back button returns to previous activity
        Button button = findViewById(R.id.restaurantInfoBackButton);
        button.setOnClickListener(view -> onBackPressed());

        // Use info
        ListView featuresList = findViewById(R.id.restaurantInfoFeaturesList);
        featuresList.setAdapter(new ArrayAdapter<String>(RestaurantInfoActivity.this, android.R.layout.simple_list_item_1, gfFeatures));
        featuresList.setHeaderDividersEnabled(true);
        featuresList.setFooterDividersEnabled(true);
        featuresList.addFooterView(new View(this), null, true);
        featuresList.addHeaderView(new View(this), null, true);

        ListView timesList = findViewById(R.id.restaurantInfoTimesList);
        timesList.setAdapter(new ArrayAdapter<String>(RestaurantInfoActivity.this, android.R.layout.simple_list_item_1, times));
        timesList.setHeaderDividersEnabled(true);
        timesList.setFooterDividersEnabled(true);
        timesList.addFooterView(new View(this), null, true);
        timesList.addHeaderView(new View(this), null, true);
    }
}
