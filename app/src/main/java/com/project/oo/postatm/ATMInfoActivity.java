package com.project.oo.postatm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;

public class ATMInfoActivity extends AppCompatActivity {
    PostATM atm = null;
    TextView txtName;
    TextView txtPhone;
    TextView txtAddress;
    TextView txtNote;
    FavoriteManager favoriteManager;
    ImageButton btnFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atminfo);

        favoriteManager = FavoriteManager.getInstance();
        atm = (PostATM) getIntent().getSerializableExtra("selectedATM");
        setTitle(atm.name);
        initialUIs();
        setTextView(txtName, atm.name);
        setTextView(txtPhone, atm.telephone);
        setTextView(txtAddress, atm.city + atm.district + atm.address);
        setTextView(txtNote, atm.getStatus());
    }

    private void initialUIs() {
        txtName = (TextView) findViewById(R.id.txtName);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtNote = (TextView) findViewById(R.id.txtNote);
        btnFavorite = (ImageButton) findViewById(R.id.btnFavorite);
        refreshFavoriteIcon();
    }

    private void refreshFavoriteIcon()
    {
        if (favoriteManager.isInRecord(atm))
            btnFavorite.setImageResource(btn_star_big_on);
        else
            btnFavorite.setImageResource(btn_star_big_off);
    }

    private void setTextView(TextView v, String str) {
        v.setText(v.getText() + str);
    }

    public void buttonClicked(View v) {

        switch (v.getId()) {
            case R.id.btnPhone: {
                Intent intent;
                try {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + atm.telephone));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast toast = Toast.makeText(ATMInfoActivity.this, "不支援播號功能!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            }

            case R.id.btnMap: {
                Intent intent;
                Uri uri = Uri.parse("geo:" + atm.latitude + "," + atm.longitude + "?q=" + atm.latitude + "," + atm.longitude + "(ATM : " + atm.name + ")");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                break;
            }
            case R.id.btnNavigation: {
                Intent intent;
                Uri uri = Uri.parse("google.navigation:q=" + atm.latitude + "," + atm.longitude);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                break;
            }
            case R.id.btnFavorite:
            {
               if (favoriteManager.isInRecord(atm))
                    favoriteManager.deleteRecord(atm);
                else
                    favoriteManager.addRecord(atm);
                refreshFavoriteIcon();
                try {
                    favoriteManager.saveRecordDate(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

    }
}
