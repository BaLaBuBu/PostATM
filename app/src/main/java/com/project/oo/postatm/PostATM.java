package com.project.oo.postatm;

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

    public String getStatus()
    {
        String status = "";
        if (deposit)
            status += "<存款>";
        if (passbookUpdate)
            status += "<補摺>";
        if (outside)
            status += "<局外>";
        else if (!outside)
            status += "<局內>";
        return  status;
    }

    @Override
    public String toString() {
        return name;
    }
}
