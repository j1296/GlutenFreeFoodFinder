// ==========================================
//  Title:  RestaurantUnitTest
//  Author: James Kelsey
//  Date:   08/04/2020
// ==========================================

package com.example.glutenfreefoodfinder;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class RestaurantUnitTest {
    Restaurant r = new Restaurant();

    @Test
    public void ID() {
        r.setID("2");
        assertEquals("2", r.getID());
    }

    @Test
    public void Name() {
        r.setName("Restaurant");
        assertEquals("Restaurant", r.toString());
    }

    @Test
    public void Address() {
        r.setAddress("123 Drive");
        assertEquals("123 Drive", r.getAddress());
    }

    @Test
    public void IsOpenNow() {
        r.setIsOpenNow(true);
        assertEquals(true, r.getIsOpenNow());
    }

    @Test
    public void Rating() {
        r.setRating(4.6);
        assertEquals(4.6, r.getRating(), 0);
    }

    @Test
    public void Website() {
        r.setWebsite("www.example.com/test");
        assertEquals("www.example.com/test", r.getWebsite());
    }

    @Test
    public void PriceLevel() {
        r.setPriceLevel(3);
        assertEquals(3, r.getPriceLevel());
    }

    @Test
    public void PhoneNumber() {
        r.setPhoneNumber("07123456789");
        assertEquals("07123456789", r.getPhoneNumber());
    }

    @Test
    public void Latitude() {
        r.setLatitude(14.57);
        assertEquals(14.57, r.getLatitude(), 0);
    }

    @Test
    public void Longitude() {
        r.setLongitude(-57.91);
        assertEquals(-57.91, r.getLongitude(), 0);
    }

    @Test
    public void Vicinity() {
        r.setVicinity("Somewhere Nearby");
        assertEquals("Somewhere Nearby", r.getVicinity());
    }

    @Test
    public void OpeningTime() {
        String[] times = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        r.setOpeningTimes(times);
        assertEquals("Wednesday", r.getOpeningTime(2));
    }

    @Test
    public void OpeningTimes() {
        String[] times = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        r.setOpeningTimes(times);
        assertArrayEquals(times, r.getOpeningTimes());
    }

    @Test
    public void GlutenFreeFeatures() {
        String[] features = new String[]{"Pasta", "Pizza", "Steak", "Chips", "Bread"};
        r.setGlutenFreeFeatures(features);
        assertArrayEquals(features, r.getGlutenFreeFeatures());
    }
}