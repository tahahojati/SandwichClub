package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = "JSONUtils";
    public static Sandwich parseSandwichJson(String json) {
        JSONObject jo;
        Sandwich sand = new Sandwich();
        try {
            jo = new JSONObject(json);
            if (jo.has("name")) {
                JSONObject nameObject = jo.getJSONObject("name");
                if (nameObject.has("mainName")) {
                    sand.setMainName(nameObject.getString("mainName"));
                }
                if (nameObject.has("alsoKnownAs")) {
                    ArrayList<String> akaList = new ArrayList<>();
                    sand.setAlsoKnownAs(akaList);
                    JSONArray akaArray = nameObject.getJSONArray("alsoKnownAs");
                    for (int index = 0; index < akaArray.length(); ++index) {
                        akaList.add(akaArray.getString(index));
                    }
                }
            }
            if (jo.has("placeOfOrigin")) {
                sand.setPlaceOfOrigin(jo.getString("placeOfOrigin"));
            }
            if (jo.has("description")) {
                sand.setDescription(jo.getString("description"));
            }
            if (jo.has("image")) {
                sand.setImage(jo.getString("image"));
            }
            if (jo.has("ingredients")) {
                ArrayList<String> ingredientList = new ArrayList<>();
                JSONArray ingredientArray = jo.getJSONArray("ingredients");
                sand.setIngredients(ingredientList);
                for (int index = 0; index < ingredientArray.length(); ++index) {
                    ingredientList.add(ingredientArray.getString(index));
                }
            }
            return sand;
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing sandwich's JSON string: " + json, e);
            return null;
        }
    }
}
