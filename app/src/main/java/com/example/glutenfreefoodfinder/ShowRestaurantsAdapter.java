// ==========================================
//  Title:  ShowRestaurantsAdapter
//  Author: James Kelsey
//  Date:   15/03/2020
// ==========================================

package com.example.glutenfreefoodfinder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.List;

public class ShowRestaurantsAdapter extends ArrayAdapter<Restaurant> {

    private int resourceLayout;
    private Context context;

    // Creates adapter for list item
    public ShowRestaurantsAdapter(Context context, int resource, List<Restaurant> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(context);
            view = vi.inflate(resourceLayout, null);
        }

        // Get the relevant restaurant
        Restaurant r = getItem(position);

        // If restaurant not null set UI elements
        if (r != null){
            TextView restaurantName = view.findViewById(R.id.restaurantName);
            RatingBar restaurantRating = view.findViewById(R.id.restaurantRatingBar);
            TextView restaurantVicinity = view.findViewById(R.id.restaurantVicinity);
            TextView restaurantOpenNow = view.findViewById(R.id.restaurantOpenNow);

            if (restaurantName != null) {
                restaurantName.setText(r.toString());
            }

            if (restaurantRating != null) {
                restaurantRating.setRating((float)r.getRating());
            }

            if (restaurantVicinity != null) {
                restaurantVicinity.setText(r.getVicinity());
            }

            if (restaurantOpenNow != null) {
                boolean open = String.valueOf(r.getIsOpenNow()).equalsIgnoreCase("true");
                boolean closed = String.valueOf(r.getIsOpenNow()).equalsIgnoreCase("false");
                boolean noData =  String.valueOf(r.getIsOpenNow()).equalsIgnoreCase("null");

                if (open) {
                    restaurantOpenNow.setText(getContext().getResources().getString(R.string.open_now));
                    restaurantOpenNow.setTextColor(Color.GREEN);
                } else if (closed) {
                    restaurantOpenNow.setText(getContext().getResources().getString(R.string.closed));
                    restaurantOpenNow.setTextColor(Color.RED);
                } else if (noData) {
                    restaurantOpenNow.setText("");
                }
            }
        }
        return view;
    }
}
