package com.example.suhirtha.randomadventure;

import android.util.Log;

import com.example.suhirtha.randomadventure.models.UserRequest;

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

    RestaurantListener restaurantListener;

//--------------------------------------------------------------------------------------------------
    //TODO - All (07/19)
    //TODO - put keys and client id in secrets.xml and stop pushing it to github :(
    public static final String URL = "https://api.yelp.com/v3/businesses/search";
    public static final String API_KEY = "-zQ4y50-JDbyywxHfjTw5QVtT-dg40f5xhBGzRrQX8VCSmiV1pofn7k_ki1OLhuPP7fzAqZQRfxrszgoHlFQitLoffkl7AJZYO36xmpz2LoJLdm0mzvG5SD8nNlMW3Yx";
    public JSONArray restaurantList;
    public JSONObject selectedRestaurant;
//--------------------------------------------------------------------------------------------------
    private final OkHttpClient client = new OkHttpClient();
    public JSONObject firstRestaurant;
//--------------------------------------------------------------------------------------------------

    /**
     * default constructor
     */
    public YelpClient(){

    }

    public YelpClient(RestaurantListener restaurantListener){
        this.restaurantListener = restaurantListener;

    }

    //TODO - Suhi (07/19)
    //TODO - enable reading in user input: (user-entered) location, attributes/cuisine,
    //TODO - automatic location, rating, price (in that order of implementation) by tomorrow?
    /**
     * Populates the restaurantList with a list of restaurants that matches user requirements
     * @param activity - not sure about passing in instance of an activity, there has to be a better way
     */
    public JSONArray getBusinesses(UserRequest req, final SelectionActivity activity)  {

        /*
        //Build URL according to user parameters: temporary
        Request request = new Request.Builder()
                .url(URL+"?location=" + location)
                .addHeader("Authorization", "Bearer "+API_KEY)
                .build();
                */

        Request request = new Request.Builder()
                .url(req.getCompleteURL() + "&sort_by=rating")
                .addHeader("Authorization", "Bearer "+API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    String data = responseBody.string(); //retrieve JSON response as String

            //--------------------------------------------------------------------------------------
                    try {
                        JSONObject object = new JSONObject(data);
                        JSONArray resultArray = object.getJSONArray("businesses");
                        //TODO: stop hard-coding strings

                        if (resultArray.length() > 0) { //ensures that there is at least one result
                            restaurantList = resultArray;
                            activity.resultsReturned(restaurantList);
                        } else {
                            Log.e("NoResults", "No restaurants seem to match your " +
                                    "requirements. You're picky.");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            //--------------------------------------------------------------------------------------
                }
            }
        });

        return restaurantList;
    }


    public JSONObject getBusinessInfo(String businessId) throws Exception {
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
                        restaurantListener.restaurantInfo(selectedRestaurant);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return selectedRestaurant;
    }

}

