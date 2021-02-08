// ==========================================
//  Title:  GetRestaurantInfoAdapter
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

public class GetRestaurantInfoAdapter extends ArrayAdapter<Restaurant> {

    private int resourceLayout;
    private Context mContext;

    // Adapter for info listview
    public GetRestaurantInfoAdapter(Context context, int resource, List<Restaurant> items) {
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

        // If restaurant not null then set UI elements
        if (r != null) {
            TextView getRestaurantInfoHeader = view.findViewById(R.id.getRestaurantInfoHeader);
            TextView getRestaurantInfoLink = view.findViewById(R.id.getRestaurantInfoLink);

            if (getRestaurantInfoHeader != null && getRestaurantInfoLink != null) {
                getRestaurantInfoHeader.setText(getContext().getResources().getString(R.string.more_information));
                getRestaurantInfoLink.setText(getContext().getResources().getString(R.string.get_restaurant_info_text));
            }
        }
        return view;
    }
}
