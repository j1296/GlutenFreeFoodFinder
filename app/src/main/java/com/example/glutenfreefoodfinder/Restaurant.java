// ==========================================
//  Title:  Restaurant
//  Author: James Kelsey
//  Date:   15/03/2020
// ==========================================

package com.example.glutenfreefoodfinder;

public class Restaurant {

    private String id;
    private String name;
    private String address;
    private Boolean isOpenNow;
    private double rating;
    private String website;
    private int priceLevel;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private String vicinity;
    private String[] openingTimes;
    private String[] glutenFreeFeatures;

    public Restaurant(){
        super();
    }

    public String getID(){
        return this.id;
    }

    public void setID(String id){
        this.id = id;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public Boolean getIsOpenNow(){
        return this.isOpenNow;
    }

    public void setIsOpenNow(Boolean isOpen){
        this.isOpenNow = isOpen;
    }

    public double getRating(){
        return this.rating;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public String getWebsite(){
        return this.website;
    }

    public void setWebsite(String website){
        this.website = website;
    }

    public int getPriceLevel(){
        return this.priceLevel;
    }

    public void setPriceLevel(int priceLevel){
        this.priceLevel = priceLevel;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public String getVicinity(){
        return this.vicinity;
    }

    public void setVicinity(String vicinity){
        this.vicinity = vicinity;
    }

    public String[] getOpeningTimes(){
        return this.openingTimes;
    }

    public String getOpeningTime(int index){
        return this.openingTimes[index];
    }

    public void setOpeningTimes(String[] openingTimes){
        this.openingTimes = openingTimes;
    }

    public String[] getGlutenFreeFeatures(){
        return this.glutenFreeFeatures;
    }

    public void setGlutenFreeFeatures(String[] glutenFreeFeatures){
        this.glutenFreeFeatures = glutenFreeFeatures;
    }
}
