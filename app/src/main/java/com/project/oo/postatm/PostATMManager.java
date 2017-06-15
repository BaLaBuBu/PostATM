package com.project.oo.postatm;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by CGLab-K501L on 2017/5/19.
 */

public class PostATMManager {
    List<PostATM> postATMs;
    Set<City> citySet;
    List<City> cityList = null;
    InputStreamReader atmFileReader;

    private static class SingletonHolder {
        private static final PostATMManager INSTANCE = new PostATMManager();
    }

    private PostATMManager() {

    }

    public static final PostATMManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public int getPostATMCount() {
        return postATMs.size();
    }

    public void loadATMData() throws IOException {
        citySet = new LinkedHashSet<>();
        CSVReader csvReader = new CSVReader(atmFileReader);
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            PostATM tempATM = new PostATM();
            /*
                        0縣市
                        1鄉鎮市區
                        2儲匯局號
                        3局名
                        4聯絡電話
                        5郵局地址
                        6經度
                        7緯度
                        8自動櫃員機
                        9自動櫃員存提款機
                        10自動補摺機
                        11自動櫃員提款機
                        12是否為局外ATM
                        13縣市排序
            */
            tempATM.city = nextLine[0];
            tempATM.district = nextLine[1];
            tempATM.name = nextLine[3];
            tempATM.telephone = nextLine[4];
            tempATM.address = nextLine[5];
            tempATM.longitude = Double.parseDouble(nextLine[6]);
            tempATM.latitude = Double.parseDouble(nextLine[7]);
            tempATM.deposit = (!nextLine[9].equals(""));
            tempATM.passbookUpdate = (!nextLine[10].equals(""));
            tempATM.outside = (nextLine[12].equals("局外"));
            postATMs.add(tempATM);

            int sortVal = Integer.parseInt(nextLine[13]);
            City c = new City(tempATM.city, sortVal);
            if (citySet.contains(c)) {
                City target = getCityBySortingValue(sortVal);
                target.addDistrict(tempATM.district);
            } else
                citySet.add(c);
        }
    }

    public void setATMFileReader(InputStreamReader reader) {
        postATMs = new ArrayList<>();
        this.atmFileReader = reader;
    }

    public List<City> getCityList() {
        if (cityList == null)
            cityList = new ArrayList<City>(citySet);
        else {
            cityList.clear();
            cityList.addAll(citySet);
        }
        return cityList;
    }

    private City getCityBySortingValue(int sortVal) {
        for (Iterator<City> it = citySet.iterator(); it.hasNext(); ) {
            City c = it.next();
            if (c.getSortingValue() == sortVal)
                return c;
        }
        return null;
    }

    public void getPostATM(String cityName, String districtName, List<PostATM> result) {
        for (int i = 0; i < this.postATMs.size(); i++) {
            PostATM atm = (PostATM) this.postATMs.get(i);
            if ((atm.city).equals(cityName)) {

                if ((atm.district).equals(districtName)) {
                    result.add(atm);
                }
            }
        }
    }

    public PostATM getPostATMByName(String atmName) {
        for (int i = 0; i < this.postATMs.size(); i++) {
            if (postATMs.get(i).name.equals(atmName))
                return postATMs.get(i);
        }
        return null;
    }
}
