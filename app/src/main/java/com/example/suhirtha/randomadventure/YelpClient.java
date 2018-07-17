package com.example.suhirtha.randomadventure;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by togata on 7/16/18.
 */

public class YelpClient extends OAuthBaseClient {

    public static final String REST_URL = "https://api.yelp.com/v3/businesses/search";
    public static final String REST_API_KEY = "-zQ4y50-JDbyywxHfjTw5QVtT-dg40f5xhBGzRrQX8VCSmiV1pofn7k_ki1OLhuPP7fzAqZQRfxrszgoHlFQitLoffkl7AJZYO36xmpz2LoJLdm0mzvG5SD8nNlMW3Yx";
    public static final String REST_CLIENT_ID = "tTmiwin5LV1jvFNL51ng4A";
    public static final BaseApi REST_API_INSTANCE = YelpApi.instance();
    public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    public YelpClient(Context c) {
        super(c, REST_API_INSTANCE, REST_URL, REST_CLIENT_ID, REST_API_KEY, REST_CALLBACK_URL_TEMPLATE);
    }


    public void getStoresInRadius(float radius, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", radius);
        params.put("since_id", 1);

        client.get(apiUrl, params, handler);
    }
}
