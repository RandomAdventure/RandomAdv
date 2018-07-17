package com.example.suhirtha.randomadventure;

import android.content.Context;

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

    public static final String REST_URL = "https://api.yelp.com/v3/businesses/search";
    public static final String REST_API_KEY = "-zQ4y50-JDbyywxHfjTw5QVtT-dg40f5xhBGzRrQX8VCSmiV1pofn7k_ki1OLhuPP7fzAqZQRfxrszgoHlFQitLoffkl7AJZYO36xmpz2LoJLdm0mzvG5SD8nNlMW3Yx";
    public static final String REST_CLIENT_ID = "tTmiwin5LV1jvFNL51ng4A";
    public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
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

                    System.out.println(responseBody.string());
                }
            }
        });
    }

    public YelpClient(Context c) {

    }

}
