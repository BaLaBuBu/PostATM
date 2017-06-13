package com.project.oo.postatm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.v7.appcompat.R.styleable.View;

public class SearchPostATMActivity extends AppCompatActivity {
    private boolean atmSearched = false;
    private CheckBox[] cbConditions;
    private PostATMManager postATMManager;
    private ListView lstSearch;
    private List<City> cityList;
    private City selectedCity;
    private String selectDistrict;
    private List<PostATM> searchResultATM;
    private List<PostATM> conditionATM;
    private TextView atmNoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post_atm);
        searchResultATM = new ArrayList<>();
        lstSearch = (ListView) findViewById(R.id.lstSearch);
        postATMManager = PostATMManager.getInstance();
        cityList = postATMManager.getCityList();
        cbConditions = new CheckBox[4];
        cbConditions[0] = (CheckBox) findViewById(R.id.cbAll);
        cbConditions[1] = (CheckBox) findViewById(R.id.cbDeposit);
        cbConditions[2] = (CheckBox) findViewById(R.id.cbUpdate);
        cbConditions[3] = (CheckBox) findViewById(R.id.cbOutside);
        for (int i = 0; i < 4; i++)
            cbConditions[i].setOnClickListener(conditionListener);
        showCities();
    }

    private void showCities() {
        ArrayAdapter<City> cityArrayAdapterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityList);
        lstSearch.setAdapter(cityArrayAdapterAdapter);
        lstSearch.setOnItemClickListener(cityClickListener);
    }

    private AdapterView.OnItemClickListener cityClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedCity = (City) parent.getItemAtPosition(position);
            showDistricts();
        }
    };

    private void showDistricts() {
        ArrayAdapter<String> districtArrayAdapterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedCity.getDistricts());
        lstSearch.setAdapter(districtArrayAdapterAdapter);
        lstSearch.setOnItemClickListener(districtClickListener);
    }

    private AdapterView.OnItemClickListener districtClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDistrict = parent.getItemAtPosition(position).toString();
            searchResultATM.clear();
            postATMManager.getPostATM(selectedCity.toString(), selectDistrict, searchResultATM);
            conditionATM = new ArrayList<>(searchResultATM);
            atmSearched = true;
            showPostATMs();
        }
    };
    private AdapterView.OnItemClickListener atmClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(SearchPostATMActivity.this, ATMInfoActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("selectedATM", conditionATM.get(position));
            intent.putExtras(bundle);
            intent.putExtra("note",atmNoteText.getText());
            startActivity(intent);
        }
    };

    private void showPostATMs() {
            conditionATM.clear();
            if (cbConditions[0].isChecked()) {
                for (int i = 0; i < searchResultATM.size(); i++) {
                    PostATM atm = searchResultATM.get(i);
                    conditionATM.add(atm);
                }

            } else {
                for (int i = 0; i < searchResultATM.size(); i++) {
                    PostATM atm = searchResultATM.get(i);
                    if ((!cbConditions[1].isChecked()) | cbConditions[1].isChecked() == atm.deposit &&
                            (!cbConditions[2].isChecked()) | cbConditions[2].isChecked() == atm.passbookUpdate &&
                            (!cbConditions[3].isChecked()) | cbConditions[3].isChecked() == atm.outside)
                        conditionATM.add(atm);
                }
            }
            ArrayAdapter postATMArrayAdapterAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, conditionATM) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    atmNoteText = (TextView) view.findViewById(android.R.id.text2);
                    text1.setText(conditionATM.get(position).name);
                    String status = "";
                    if (conditionATM.get(position).deposit)
                        status += "<存款>";
                    if (conditionATM.get(position).passbookUpdate)
                        status += "<補摺>";
                    if (conditionATM.get(position).outside)
                        status += "<局外>";
                    else if (!conditionATM.get(position).outside)
                        status += "<局內>";
                    atmNoteText.setText(status);
                    return view;
                }
            };
            lstSearch.setAdapter(postATMArrayAdapterAdapter);
            lstSearch.setOnItemClickListener(atmClickListener);
    }

    private CheckBox.OnClickListener conditionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if other checkboxes are unchecked, set "全部" checked
            if ( !cbConditions[1].isChecked()  && !cbConditions[2].isChecked() && !cbConditions[3].isChecked() )
                cbConditions[0].setChecked(true);
            //if  "全部" is checked, set others unchecked
            else if (v.getId() == R.id.cbAll && cbConditions[0].isChecked()) {
                for (int i = 1; i < cbConditions.length; i++)
                    cbConditions[i].setChecked(false);
            }
            //other checkbox is checked, set "全部" unchecked
            else
                cbConditions[0].setChecked(false);
            if (atmSearched)
                showPostATMs();
        }
    };

}
