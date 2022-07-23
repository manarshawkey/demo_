package com.example.android.aroundegypt.Data.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

interface JsonMapper <T>{
    T map(JSONObject jsonObject) throws JSONException;
}
