package com.project.oo.postatm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ATMInfoActivity extends AppCompatActivity {
    PostATM atm = null;
    String note;
    TextView txtName;
    TextView txtPhone;
    TextView txtAddress;
    TextView txtNote;
    ImageButton btnPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atminfo);

        atm = (PostATM) getIntent().getSerializableExtra("selectedATM");
        note = getIntent().getStringExtra("note");

        initialUIs();
        setTextView(txtName, atm.name);
        setTextView(txtPhone, atm.telephone);
        setTextView(txtAddress, atm.city + atm.district + atm.address);
        setTextView(txtNote, note);

        btnPhone.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + atm.telephone));
                startActivity(call);
            }
        });
    }
    private void initialUIs()
    {
        txtName = (TextView) findViewById(R.id.txtName);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtNote = (TextView) findViewById(R.id.txtNote);
        btnPhone = (ImageButton) findViewById(R.id.btnPhone);
    }
    private void setTextView(TextView v, String str)
    {
        v.setText(v.getText() + str);
    }
}
