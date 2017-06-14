package com.project.oo.postatm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FunctionSelectActivity extends AppCompatActivity {
    PostATMManager pATMm;
    FavoriteManager favoriteManager;
    HistoryManager historyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_select);
        pATMm = PostATMManager.getInstance();
        favoriteManager = FavoriteManager.getInstance();
        favoriteManager.setRecordFilePath("userFavorite.json");
        historyManager = HistoryManager.getInstance();
        historyManager.setMaxRecordCount(10);
        historyManager.setRecordFilePath("userHistory.json");
        try {
            initialATMData();
            favoriteManager.loadRecordDate(FunctionSelectActivity.this);
            historyManager.loadRecordDate(FunctionSelectActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeActivity(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btnFavorite:
                intent.setClass(FunctionSelectActivity.this, FavoriteActivity.class);
                break;
            case R.id.btnSearch:
                intent.setClass(FunctionSelectActivity.this, SearchPostATMActivity.class);
                break;
            case R.id.btnNearBy:
                intent.setClass(FunctionSelectActivity.this, NearByPostATMActivity.class);
                break;
            case R.id.btnHistory:
                intent.setClass(FunctionSelectActivity.this, HistoryActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void initialATMData() throws IOException {
        InputStream input = null;
        input = getAssets().open("post_atm.csv");
        InputStreamReader reader = new InputStreamReader(input, "UTF-8");
        pATMm.setATMFileReader(reader);
        pATMm.loadATMData();
    }
}
