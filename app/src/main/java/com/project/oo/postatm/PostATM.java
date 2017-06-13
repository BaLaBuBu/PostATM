package com.project.oo.postatm;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by CGLab-K501L on 2017/5/19.
 */

public class PostATM implements Serializable{
    String name;
    String city;
    String district;
    String telephone;
    String address;
    double latitude;
    double longitude;
    boolean deposit;
    boolean outside;
    boolean passbookUpdate;

    @Override
    public String toString() {
        return name;
    }
}
