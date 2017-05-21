package com.project.oo.postatm;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by CGLab-K501L on 2017/5/19.
 */

public class City {
    String name;
    int sortingValue;
    Set<String> districtSet = null;
    List<String> districtList = null;

    public City(String cityName, int sortVal) {
        name = cityName;
        sortingValue = sortVal;
    }

    public void addDistrict(String dist) {
        if (districtSet == null)
            districtSet = new LinkedHashSet<>();
        districtSet.add(dist);
    }

    public List<String> getDistricts() {
        if (districtList == null)
            districtList = new ArrayList<>(districtSet);
        else {
            districtList.clear();
            districtList.addAll(districtSet);
        }
        return districtList;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        City c = (City) o;
        return this.sortingValue == c.sortingValue;
    }

    public int hashCode() {
        return sortingValue;
    }

    public int getSortingValue() {
        return sortingValue;
    }

    @Override
    public String toString() {
        return name;
    }
}
