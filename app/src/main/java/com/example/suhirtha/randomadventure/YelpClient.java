package com.example.suhirtha.randomadventure;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by togata on 7/16/18.
 */

public class YelpClient{

    //TODO - Suhi (07/19)
    //TODO - put keys and client id in secrets.xml and stop pushing it to github :(

    public static final String URL = "https://api.yelp.com/v3/businesses/search";
    public static final String API_KEY = "-zQ4y50-JDbyywxHfjTw5QVtT-dg40f5xhBGzRrQX8VCSmiV1pofn7k_ki1OLhuPP7fzAqZQRfxrszgoHlFQitLoffkl7AJZYO36xmpz2LoJLdm0mzvG5SD8nNlMW3Yx";
    public static final String CLIENT_ID = "tTmiwin5LV1jvFNL51ng4A";
    public static final String CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";
    //public JSONArray
    public JSONObject selectedRestaurant;


    private final OkHttpClient client = new OkHttpClient();
    public JSONObject firstRestaurant;

    //TODO - Suhi (07/19)
    //TODO - enable reading in user input: (user-entered) location, attributes/cuisine,
    //TODO - automatic location, rating, price (in that order of implementation) by tomorrow?
    //TODO - call class from Selection Activity (or resulting fragment)

    public void run(String location) throws Exception {
        Request request = new Request.Builder()
                .url(URL+"?location=" + location)
                .addHeader("Authorization", "Bearer "+API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();

                    /* what is this printing... why is it printing things...
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    */

                    String data = responseBody.string();

                    try {
                        JSONObject object = new JSONObject(data);
                        JSONArray resultArray = object.getJSONArray("businesses"); //TODO: stop hard-coding strings


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public JSONObject getBusinessInfo(String businessId, final ResultActivity activity) throws Exception{
        Request request = new Request.Builder()
                .url("https://api.yelp.com/v3/businesses/"+businessId)
                .addHeader("Authorization", "Bearer "+API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String data = responseBody.string();
                    try {
                        JSONObject object = new JSONObject(data);
                        Log.d("Business", object.getString("name"));
                        selectedRestaurant = object;
                        activity.notifyRestuarantUpdated(object);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return selectedRestaurant;
    }

    public YelpClient(){
    }

}

