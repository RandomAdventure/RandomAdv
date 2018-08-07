package com.example.suhirtha.randomadventure;

import android.content.Context;

import com.example.suhirtha.randomadventure.activities.SelectionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AutoCompleteSuggestions {
    private String rawJSON;
    private SelectionActivity activity = new SelectionActivity();
    private JSONArray allCategories = new JSONArray();
    private JSONArray desiredCategories = new JSONArray();
    private String[] desiredStrings;
    private Context context;

    public AutoCompleteSuggestions(Context context) {
        this.context = context;
    }

    public void readData() throws IOException {

        InputStream ins = context.getResources().openRawResource(
                context.getResources().getIdentifier("categories",
                        "raw", context.getPackageName()));

        BufferedReader r = new BufferedReader(new InputStreamReader(ins));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line).append('\n');
        }

        rawJSON = total.toString();

        /**
        rawJSON = "[{\n" +
                "    \"alias\": \"backshop\",\n" +
                "    \"title\": \"Backshop\",\n" +
                "    \"parents\": [\n" +
                "      \"food\"\n" +
                "    ],\n" +
                "    \"country_whitelist\": [\n" +
                "      \"DE\",\n" +
                "      \"AT\",\n" +
                "      \"CH\"\n" +
                "    ]\n" +
                "  }, " +
                "  {\n" +
                "    \"alias\": \"bagels\",\n" +
                "    \"title\": \"Bagels\",\n" +
                "    \"parents\": [\n" +
                "      \"food\"\n" +
                "    ],\n" +
                "    \"country_blacklist\": [\n" +
                "      \"ES\",\n" +
                "      \"AU\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"alias\": \"baguettes\",\n" +
                "    \"title\": \"Baguettes\",\n" +
                "    \"parents\": [\n" +
                "      \"restaurants\"\n" +
                "    ],\n" +
                "    \"country_whitelist\": [\n" +
                "      \"SE\",\n" +
                "      \"DE\",\n" +
                "      \"TR\",\n" +
                "      \"PT\",\n" +
                "      \"CZ\",\n" +
                "      \"NO\",\n" +
                "      \"IT\",\n" +
                "      \"MX\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"alias\": \"bailbondsmen\",\n" +
                "    \"title\": \"Bail Bondsmen\",\n" +
                "    \"parents\": [\n" +
                "      \"localservices\"\n" +
                "    ],\n" +
                "    \"country_whitelist\": [\n" +
                "      \"PT\",\n" +
                "      \"US\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"alias\": \"bakeries\",\n" +
                "    \"title\": \"Bakeries\",\n" +
                "    \"parents\": [\n" +
                "      \"food\"\n" +
                "    ]\n" +
                "  }," +
                "  {\n" +
                "    \"alias\": \"bangladeshi\",\n" +
                "    \"title\": \"Bangladeshi\",\n" +
                "    \"parents\": [\n" +
                "      \"restaurants\"\n" +
                "    ],\n" +
                "    \"country_blacklist\": [\n" +
                "      \"PT\",\n" +
                "      \"DK\",\n" +
                "      \"ES\",\n" +
                "      \"TR\",\n" +
                "      \"MX\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"alias\": \"bankruptcy\",\n" +
                "    \"title\": \"Bankruptcy Law\",\n" +
                "    \"parents\": [\n" +
                "      \"lawyers\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"alias\": \"banks\",\n" +
                "    \"title\": \"Banks & Credit Unions\",\n" +
                "    \"parents\": [\n" +
                "      \"financialservices\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"alias\": \"barbers\",\n" +
                "    \"title\": \"Barbers\",\n" +
                "    \"parents\": [\n" +
                "      \"beautysvc\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"alias\": \"barcrawl\",\n" +
                "    \"title\": \"Bar Crawl\",\n" +
                "    \"parents\": [\n" +
                "      \"nightlife\"\n" +
                "    ],\n" +
                "    \"country_whitelist\": [\n" +
                "      \"CH\",\n" +
                "      \"US\",\n" +
                "      \"DE\",\n" +
                "      \"PL\",\n" +
                "      \"AT\",\n" +
                "      \"CZ\",\n" +
                "      \"AR\",\n" +
                "      \"AU\",\n" +
                "      \"MX\"\n" +
                "    ]\n" +
                "  }]"; **/
    }

    public void parseJSON() throws JSONException {
        if (rawJSON != null) {
            allCategories = new JSONArray(rawJSON);

            //for-each loop not applicable to JSONArray? :(
            int i = 0;
            while (i < allCategories.length()) {
                JSONObject categoryTemp = allCategories.getJSONObject(i);
                JSONArray parentsArr = categoryTemp.getJSONArray("parents");
                if (parentsArr.get(0).equals("food") || parentsArr.get(0).equals("restaurants")) {
                    desiredCategories.put(categoryTemp);
                }
                i++;
            }
        }
    }

    public void getDesiredStrings() throws JSONException {
        ArrayList<String> desired = new ArrayList<>();
        int i = 0;
        while (i < desiredCategories.length()) {
            desired.add(((JSONObject) desiredCategories.get(i)).getString("title"));
            i++;
        }

        desiredStrings = desired.toArray(new String[desired.size()]);
    }

    public String[] getSuggestions() throws IOException, JSONException {
        readData();
        parseJSON();
        getDesiredStrings();

        return desiredStrings;
    }
}
