package com.project.oo.postatm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class FavoriteActivity extends AppCompatActivity {
    FavoriteManager favoriteManager;
    ListView lstRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        favoriteManager = FavoriteManager.getInstance();
        lstRecord = (ListView) findViewById(R.id.lstRecord);
        showFavoriteATMs();
        lstRecord.setOnItemClickListener(favoriteManager.atmClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showFavoriteATMs();
    }

    private void showFavoriteATMs(){
        setTitle("我的最愛ATM (" + favoriteManager.getRecordATMs().size() + ")");
        PostATMAdapter postATMAdapter = new PostATMAdapter(this,favoriteManager.getRecordATMs());
        lstRecord.setAdapter(postATMAdapter);
    }
}
