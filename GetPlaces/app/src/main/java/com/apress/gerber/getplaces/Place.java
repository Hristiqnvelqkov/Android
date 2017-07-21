package com.apress.gerber.getplaces;


import android.os.Parcel;
import android.os.Parcelable;

class Place implements Parcelable {
    private String name;
    private float lat;
    private float lng;

    protected Place(Parcel in) {
        name = in.readString();
        lat = in.readFloat();
        lng = in.readFloat();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public Place() {
    }

    public String getName(){
        return name;
    }
    public float getLat(){
        return lat;
    }
    public float getLng(){
        return lng;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setLat(float lat){
        this.lat=lat;
    }
    public void setLng(float lng){
        this.lng=lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(lat);
        dest.writeFloat(lng);
    }
}
