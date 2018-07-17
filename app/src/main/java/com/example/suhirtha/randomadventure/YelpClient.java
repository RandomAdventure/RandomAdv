package com.example.suhirtha.randomadventure;

import android.content.Context;
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

    public static final String URL = "https://api.yelp.com/v3/businesses/search";
    public static final String API_KEY = "-zQ4y50-JDbyywxHfjTw5QVtT-dg40f5xhBGzRrQX8VCSmiV1pofn7k_ki1OLhuPP7fzAqZQRfxrszgoHlFQitLoffkl7AJZYO36xmpz2LoJLdm0mzvG5SD8nNlMW3Yx";
    public static final String CLIENT_ID = "tTmiwin5LV1jvFNL51ng4A";
    public static final String CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    private final OkHttpClient client = new OkHttpClient();
    public JSONObject firstRestaurant;

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url(URL+"?location=san francisco")
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
                        JSONArray array = object.getJSONArray("businesses");
                        JSONObject firstRestaurant = array.getJSONObject(0);
                        Log.d("something", firstRestaurant.getString("name"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //response = responseBody.string();


                }
            }
        });
    }

    public YelpClient(Context c) throws Exception {
        run();
    }

}
