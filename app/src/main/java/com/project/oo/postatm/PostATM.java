package com.project.oo.postatm;

import java.io.InputStream;

/**
 * Created by CGLab-K501L on 2017/5/19.
 */

public class PostATM {
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
