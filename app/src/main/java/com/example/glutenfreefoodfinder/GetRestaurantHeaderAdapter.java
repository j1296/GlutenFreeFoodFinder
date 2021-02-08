// ==========================================
//  Title:  GetRestaurantHeaderAdapter
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetRestaurantHeaderAdapter extends ArrayAdapter<Restaurant> {

    private int resourceLayout;
    private Context mContext;

    // Adapter for Header listview
    public GetRestaurantHeaderAdapter(Context context, int resource, List<Restaurant> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(mContext);
            view = vi.inflate(resourceLayout, null);
        }

        Restaurant r = getItem(position);

        // If restaurant not null set UI elements
        if (r != null){
            TextView restaurantName = view.findViewById(R.id.getRestaurantName);
            TextView restaurantRating = view.findViewById(R.id.getRestaurantRating);
            TextView restaurantAddress = view.findViewById(R.id.getRestaurantAddress);
            RatingBar restaurantRatingBar = view.findViewById(R.id.getRestaurantRatingBar);
            TextView restaurantOpenNow = view.findViewById(R.id.getRestaurantOpenNow);
            TextView restaurantOpeningTimes = view.findViewById(R.id.getRestaurantOpeningTimes);

            if (restaurantName != null) {
                restaurantName.setText(r.toString());
            }

            if (restaurantAddress != null) {
                restaurantAddress.setText(r.getAddress());
            }

            if (restaurantOpenNow != null) {

                boolean open = String.valueOf(r.getIsOpenNow()).equalsIgnoreCase("true");
                boolean closed = String.valueOf(r.getIsOpenNow()).equalsIgnoreCase("false");
                boolean noData =  String.valueOf(r.getIsOpenNow()).equalsIgnoreCase("null");

                if (open) {
                    restaurantOpenNow.setText("Open Now");
                    restaurantOpenNow.setTextColor(Color.GREEN);
                } else if (closed) {
                    restaurantOpenNow.setText("Closed");
                    restaurantOpenNow.setTextColor(Color.RED);
                } else if (noData) {
                    restaurantOpenNow.setText("No Opening Information");
                    restaurantOpenNow.setTextColor(Color.BLACK);
                }
            }

            if (restaurantRating != null && restaurantRatingBar != null) {
                String rating = String.valueOf(r.getRating());
                restaurantRating.setText(rating);

                restaurantRatingBar.setRating((float)r.getRating());
            }

            if (restaurantOpeningTimes != null) {

                Date date = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(date);

                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                if (r.getOpeningTimes() != null) {

                    switch(dayOfWeek) {
                        case 1 : restaurantOpeningTimes.setText(r.getOpeningTime(6));
                        break;
                        case 2 : restaurantOpeningTimes.setText(r.getOpeningTime(0));
                        break;
                        case 3 : restaurantOpeningTimes.setText(r.getOpeningTime(1));
                        break;
                        case 4 : restaurantOpeningTimes.setText(r.getOpeningTime(2));
                        break;
                        case 5 : restaurantOpeningTimes.setText(r.getOpeningTime(3));
                        break;
                        case 6 : restaurantOpeningTimes.setText(r.getOpeningTime(4));
                        break;
                        case 7 : restaurantOpeningTimes.setText(r.getOpeningTime(5));
                        break;
                        default: restaurantOpeningTimes.setText("");
                        break;
                    }
                }
            }
        }
        return view;
    }
}
