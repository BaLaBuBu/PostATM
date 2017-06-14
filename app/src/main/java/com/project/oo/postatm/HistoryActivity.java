package com.project.oo.postatm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class HistoryActivity extends AppCompatActivity {
    HistoryManager historyManager;
    ListView lstRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        historyManager = HistoryManager.getInstance();
        lstRecord = (ListView) findViewById(R.id.lstRecord);
        showHistoryATMs();
        lstRecord.setOnItemClickListener(historyManager.atmClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showHistoryATMs();
    }

    private void showHistoryATMs(){
        setTitle("查詢紀錄 (" + historyManager.getRecordATMs().size() + ")");
        PostATMAdapter postATMAdapter = new PostATMAdapter(this, historyManager.getRecordATMs());
        lstRecord.setAdapter(postATMAdapter);
    }

}
