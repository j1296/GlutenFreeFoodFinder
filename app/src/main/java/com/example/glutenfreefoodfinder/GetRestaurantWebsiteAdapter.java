// ==========================================
//  Title:  GetRestaurantWebsiteAdapter
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

public class GetRestaurantWebsiteAdapter extends ArrayAdapter<Restaurant> {

    private int resourceLayout;
    private Context mContext;

    // Adapter for website listview
    public GetRestaurantWebsiteAdapter(Context context, int resource, List<Restaurant> items) {
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
        if (r != null) {
            TextView getRestaurantWebsiteHeader = view.findViewById(R.id.getRestaurantWebsiteHeader);
            TextView getRestaurantWebsiteLink = view.findViewById(R.id.getRestaurantWebsiteLink);

            if (getRestaurantWebsiteLink != null && getRestaurantWebsiteHeader != null) {
                int urlLength = r.getWebsite().length();

                getRestaurantWebsiteHeader.setText(getContext().getResources().getString(R.string.get_restaurant_website_header));

                if (urlLength < 35) {
                    getRestaurantWebsiteLink.setText(r.getWebsite().substring(0, urlLength));
                } else {
                    getRestaurantWebsiteLink.setText(r.getWebsite().substring(0, 35).concat("..."));
                }
            }
        }
        return view;
    }
}
