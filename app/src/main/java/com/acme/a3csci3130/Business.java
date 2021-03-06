package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databsse. This is converted to a JSON format
 */
public class Business implements Serializable {

    public String uid;
    public String businessNumber;
    public String name;
    public String primaryBusiness;
    public String address;
    public String province;

    /**
     * Empty constructor for firebase object initialization
     */
    public Business() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    /**
     * Constructor to create a Business object with all fields
     * @param uid Unique ID of this business
     * @param businessNumber User-given business number
     * @param name Name of business
     * @param primaryBusiness Primary business type
     * @param address Business address
     * @param province Business province of operation
     */
    public Business(String uid, String businessNumber, String name, String primaryBusiness, String address, String province){
        this.uid = uid;
        this.businessNumber = businessNumber;
        this.name = name;
        this.primaryBusiness = primaryBusiness;
        this.address = address;
        this.province = province;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("businessNumber", businessNumber);
        result.put("name", name);
        result.put("primaryBusiness", primaryBusiness);
        result.put("address", address);
        result.put("province", province);

        return result;
    }
}
