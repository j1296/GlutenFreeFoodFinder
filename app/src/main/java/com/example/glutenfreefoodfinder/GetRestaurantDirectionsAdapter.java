// ==========================================
//  Title:  GetRestaurantDirectionsAdapter
//  Author: James Kelsey
//  Date:   15/03/2020
// ==========================================

package com.example.glutenfreefoodfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GetRestaurantDirectionsAdapter extends ArrayAdapter<Restaurant> {

    private int resourceLayout;
    private Context mContext;

    // Adapter for directions listview
    public GetRestaurantDirectionsAdapter(Context context, int resource, List<Restaurant> items) {
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

        // If restaurant not null, set UI elements
        if (r != null) {
            TextView getRestaurantDirectionsHeader = view.findViewById(R.id.getRestaurantDirectionsHeader);
            TextView getRestaurantDirectionsLink = view.findViewById(R.id.getRestaurantDirectionsLink);

            if (getRestaurantDirectionsHeader != null && getRestaurantDirectionsLink != null) {
                getRestaurantDirectionsHeader.setText("Directions");
                getRestaurantDirectionsLink.setText("To Restaurant");
            }
        }
        return view;
    }
}
