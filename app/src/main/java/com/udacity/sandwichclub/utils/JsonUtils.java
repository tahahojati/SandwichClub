package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = "JSONUtils";
    private static final String JSON_KEY_NAME = "name";
    private static final String JSON_KEY_MAINNAME = "mainName";
    private static final String JSON_KEY_ALSOKNOWNAS = "alsoKnownAs";
    private static final String JSON_KEY_PLACEOFORIGIN = "placeOfOrigin";
    private static final String JSON_KEY_DESCRIPTION = "description";
    private static final String JSON_KEY_INGREDIENTS = "ingredients";
    private static final String JSON_KEY_IMAGE = "image";
    public static Sandwich parseSandwichJson(String json) {
        JSONObject jo;
        Sandwich sand = new Sandwich();
        try {
            jo = new JSONObject(json);
            if (jo.has(JSON_KEY_NAME)) {
                JSONObject nameObject = jo.optJSONObject(JSON_KEY_NAME);
                if (nameObject.has(JSON_KEY_MAINNAME)) {
                    sand.setMainName(nameObject.optString(JSON_KEY_MAINNAME));
                }
                if (nameObject.has(JSON_KEY_ALSOKNOWNAS)) {
                    ArrayList<String> akaList = new ArrayList<>();
                    sand.setAlsoKnownAs(akaList);
                    JSONArray akaArray = nameObject.optJSONArray(JSON_KEY_ALSOKNOWNAS);
                    for (int index = 0; index < akaArray.length(); ++index) {
                        akaList.add(akaArray.optString(index));
                    }
                }
            }
            if (jo.has(JSON_KEY_PLACEOFORIGIN)) {
                sand.setPlaceOfOrigin(jo.optString(JSON_KEY_PLACEOFORIGIN));
            }
            if (jo.has(JSON_KEY_DESCRIPTION)) {
                sand.setDescription(jo.optString(JSON_KEY_DESCRIPTION));
            }
            if (jo.has(JSON_KEY_IMAGE)) {
                sand.setImage(jo.optString(JSON_KEY_IMAGE));
            }
            if (jo.has(JSON_KEY_INGREDIENTS)) {
                ArrayList<String> ingredientList = new ArrayList<>();
                JSONArray ingredientArray = jo.optJSONArray(JSON_KEY_INGREDIENTS);
                sand.setIngredients(ingredientList);
                for (int index = 0; index < ingredientArray.length(); ++index) {
                    ingredientList.add(ingredientArray.optString(index));
                }
            }
            return sand;
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing sandwich's JSON string: " + json, e);
            return null;
        }
    }
}
