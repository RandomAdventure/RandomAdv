package com.example.suhirtha.randomadventure;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AutoCompleteSuggestions {

    private Context context;

    public AutoCompleteSuggestions(Context context) {
        this.context = context;
    }

    /**
     * Uses InputStream and StringBuilder to read JSON file from resource stored in res/raw
     * @throws IOException - if file not found
     */
    private String readData() throws IOException {

        InputStream ins = context.getResources().openRawResource(
                context.getResources().getIdentifier("categories",
                        "raw", context.getPackageName()));

        BufferedReader r = new BufferedReader(new InputStreamReader(ins));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line).append('\n');
        }

        return total.toString();
    }

    /**
     * Parses JSON file (stored in rawJSON) to create category objects (includes all types)
     * @throws JSONException - if rawJSON does not contain valid JSON
     */
    private JSONArray parseJSON(String rawJSON) throws JSONException {
        JSONArray desiredCategories = new JSONArray();
        if (rawJSON != null) {
            JSONArray allCategories = new JSONArray(rawJSON);

            int i = 0;
            while (i < allCategories.length()) {
                //current category object
                JSONObject categoryTemp = allCategories.getJSONObject(i);
                //array that stores the current category's 'parents'
                JSONArray parentsArr = categoryTemp.getJSONArray("parents");
                if (parentsArr != null && parentsArr.length() != 0 &&
                        (parentsArr.get(0).equals("food") || parentsArr.get(0).equals("restaurants")
                         || parentsArr.get(0).equals("gourmet") || parentsArr.get(0).equals("mexican"))) {
                    //adds category object IF primary parent = restaurant or food
                    desiredCategories.put(categoryTemp);
                    Log.d("Category", categoryTemp.getString("title"));
                }
                i++;
            }
        }

        return desiredCategories;
    }

    /**
     * Extracts only desired strings of categories (those that fall under 'restaurants' or 'food'
     * and stores the title value in an array
     * @throws JSONException
     */
    public String[] getDesiredStrings(JSONArray desiredCategories) throws JSONException {
        ArrayList<String> desired = new ArrayList<>();
        int i = 0;
        while (i < desiredCategories.length()) {
            desired.add(((JSONObject) desiredCategories.get(i)).getString("title"));
            i++;
        }

        //convert desiredStrings to array (because TextWatcher doesn't want arrayLists :/ )
        return desired.toArray(new String[desired.size()]);
    }

    /**
     * method to be called to run all helper methods
     * @return - only desired strings array
     * @throws IOException - if file not found
     * @throws JSONException - invalid JSON
     */
    public String[] getSuggestions() throws IOException, JSONException {
        String rawJSON = readData();
        JSONArray desiredCatgeoires = parseJSON(rawJSON);

        return getDesiredStrings(desiredCatgeoires);

    }

    /**
     * Creates cache file to store array of strings: currently not implemented
     * @param context - (Selection) activity
     * @param url - filename (WITHOUT extension)
     * @return - created file
     */
    private File getTempFile(Context context, String url, File arrayFile) {
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            arrayFile = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            Log.d("Cache file creation", "Error creating cache file");
        }
        return arrayFile;
    }

}
