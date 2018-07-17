package com.example.suhirtha.randomadventure;

import android.content.Context;


/**
 * Created by togata on 7/16/18.
 */

public class YelpClient{

    public static final String REST_URL = "https://api.yelp.com/v3/businesses/search";
    public static final String REST_API_KEY = "-zQ4y50-JDbyywxHfjTw5QVtT-dg40f5xhBGzRrQX8VCSmiV1pofn7k_ki1OLhuPP7fzAqZQRfxrszgoHlFQitLoffkl7AJZYO36xmpz2LoJLdm0mzvG5SD8nNlMW3Yx";
    public static final String REST_CLIENT_ID = "tTmiwin5LV1jvFNL51ng4A";
    public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    public YelpClient(Context c) {
        //super(c, REST_API_INSTANCE, REST_URL, REST_CLIENT_ID, REST_API_KEY, REST_CALLBACK_URL_TEMPLATE);
    }

}
