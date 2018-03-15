package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DetailViewActivity extends Activity {

    private EditText nameField, businessNumberField, primaryBusinessField, addressField, provinceField;
    Business receivedBusinessInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedBusinessInfo = (Business)getIntent().getSerializableExtra("Business");

        nameField = (EditText) findViewById(R.id.nameField);
        businessNumberField = (EditText) findViewById(R.id.businessNumberField);
        primaryBusinessField = (EditText) findViewById(R.id.primaryBusinessField);
        addressField = (EditText) findViewById(R.id.addressField);
        provinceField = (EditText) findViewById(R.id.provinceField);

        if(receivedBusinessInfo != null){
            nameField.setText(receivedBusinessInfo.name);
            businessNumberField.setText(receivedBusinessInfo.businessNumber);
            primaryBusinessField.setText(receivedBusinessInfo.primaryBusiness);
            addressField.setText(receivedBusinessInfo.address);
            provinceField.setText(receivedBusinessInfo.province);
        }
    }

    public void updateBusiness(View v){
        //TODO: Update business funcionality
    }

    public void eraseBusiness(View v)
    {
        //TODO: Erase business functionality
    }
}
