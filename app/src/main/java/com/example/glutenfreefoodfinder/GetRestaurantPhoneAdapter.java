// ==========================================
//  Title:  GetRestaurantPhoneAdapter
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

public class GetRestaurantPhoneAdapter extends ArrayAdapter<Restaurant> {

    private int resourceLayout;
    private Context mContext;

    public GetRestaurantPhoneAdapter(Context context, int resource, List<Restaurant> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    // Adapter for phone listview
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
            TextView getRestaurantPhoneNumberHeader = view.findViewById(R.id.getRestaurantPhoneNumberHeader);
            TextView getRestaurantPhoneNumberLink = view.findViewById(R.id.getRestaurantPhoneNumberLink);

            if (getRestaurantPhoneNumberHeader != null && getRestaurantPhoneNumberLink != null) {
                getRestaurantPhoneNumberHeader.setText(getContext().getResources().getString(R.string.get_restaurant_phone_header));
                getRestaurantPhoneNumberLink.setText(r.getPhoneNumber());
            }
        }
        return view;
    }
}
