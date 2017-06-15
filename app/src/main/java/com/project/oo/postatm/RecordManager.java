package com.project.oo.postatm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BaLaBuBu on 2017/6/14.
 */

public class RecordManager {
    private String recordFilePath;
    private List<PostATM> recordATMs;

    public RecordManager() {
        recordATMs = new ArrayList<>();
    }

    public void addRecord(PostATM atm) {
        recordATMs.add(atm);
    }

    public void deleteRecord(PostATM atm) {
        for (int i = 0; i < recordATMs.size(); i++) {
            if (recordATMs.get(i).name.equals(atm.name)) {
                recordATMs.remove(i);
                break;
            }
        }
    }

    public void clearRecordData() {
        recordATMs.clear();
    }

    public boolean isInRecord(PostATM atm) {
        for (int i = 0; i < recordATMs.size(); i++) {
            if (recordATMs.get(i).name.equals(atm.name)) {
                return true;
            }
        }
        return false;
    }

    void loadRecordDate(Context c) throws Exception {
        FileInputStream fis = null;
        fis = c.openFileInput(recordFilePath);
        String json = convertStreamToString(fis);
        Type type = new TypeToken<List<PostATM>>() {
        }.getType();
        recordATMs = new Gson().fromJson(json, type);
    }

    void saveRecordDate(Context c) throws IOException {
        String json = new Gson().toJson(recordATMs);
        FileOutputStream fos = null;
        fos = c.openFileOutput(recordFilePath, Context.MODE_PRIVATE);
        fos.write(json.getBytes());
        fos.close();
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public void setRecordFilePath(String recordFilePath) {
        this.recordFilePath = recordFilePath;
    }

    public List<PostATM> getRecordATMs() {
        return recordATMs;
    }

    public void setRecordATMs(List<PostATM> atms) {
        recordATMs = new ArrayList<>(atms);
    }

    public  AdapterView.OnItemClickListener atmClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(parent.getContext(), ATMInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedATM", recordATMs.get(position));
            intent.putExtras(bundle);
            parent.getContext().startActivity(intent);
        }
    };
}
