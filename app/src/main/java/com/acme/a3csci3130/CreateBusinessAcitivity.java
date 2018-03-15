package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateBusinessAcitivity extends Activity {

    private Button submitButton;
    private EditText businessNumberField, nameField, primaryBusinessField, addressField, provinceField;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
        businessNumberField = (EditText) findViewById(R.id.createBusinessNumberField);
        nameField = (EditText) findViewById(R.id.createNameField);
        primaryBusinessField = (EditText) findViewById(R.id.createPrimaryBusinessField);
        addressField = (EditText) findViewById(R.id.createAddressField);
        provinceField = (EditText) findViewById(R.id.createProvinceField);
    }

    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String businessID = appState.firebaseReference.push().getKey();
        String businessNumber = businessNumberField.getText().toString();
        String name = nameField.getText().toString();
        String primaryBusiness = primaryBusinessField.getText().toString();
        String address = addressField.getText().toString();
        String province = provinceField.getText().toString();
        Business business = new Business(businessID, businessNumber, name, primaryBusiness, address, province);

        appState.firebaseReference.child(businessID).setValue(business);

        finish();
    }
}
