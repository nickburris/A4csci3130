package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * DetailViewActivity shows details for a specific business
 */
public class DetailViewActivity extends Activity {

    private EditText nameField, businessNumberField, primaryBusinessField, addressField, provinceField;
    Business receivedBusinessInfo;

    /**
     * onCreate sets up this DetailViewActivity with the current business data as editable fields
     * @param savedInstanceState
     */
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

    /**
     * The click listener for the update business button updates the current business data in firebase
     * @param v Clicked view
     */
    public void updateBusiness(View v){
        receivedBusinessInfo.businessNumber = businessNumberField.getText().toString();
        receivedBusinessInfo.name = nameField.getText().toString();
        receivedBusinessInfo.primaryBusiness = primaryBusinessField.getText().toString();
        receivedBusinessInfo.address = addressField.getText().toString();
        receivedBusinessInfo.province = provinceField.getText().toString();

        ((MyApplicationData) getApplicationContext()).firebaseReference.child(receivedBusinessInfo.uid).setValue(receivedBusinessInfo);
        finish();
    }

    /**
     * The click listener for the erase business button erases the current business data
     * @param v Clicked view
     */
    public void eraseBusiness(View v)
    {
        ((MyApplicationData) getApplicationContext()).firebaseReference.child(receivedBusinessInfo.uid).removeValue();
        finish();
    }
}
