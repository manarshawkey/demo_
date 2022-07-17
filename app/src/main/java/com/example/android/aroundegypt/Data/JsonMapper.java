package com.example.android.aroundegypt.Data;

import org.json.JSONException;
import org.json.JSONObject;

interface JsonMapper <T>{
    T map(JSONObject jsonObject) throws JSONException;
}
