package com.example.owner.android2;

import java.io.Serializable;

/**
 * Created by k_vol on 12/06/2017.
 */

public class location implements Serializable{
    public double Longitude;
    public double Latitude;

    public location(double latitude, double longitude) {
        this.Latitude = latitude;
        this.Longitude = longitude;
    }
}
