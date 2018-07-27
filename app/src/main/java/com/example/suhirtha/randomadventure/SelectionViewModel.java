package com.example.suhirtha.randomadventure;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.suhirtha.randomadventure.models.UserRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SelectionViewModel extends ViewModel {
    public static final String URL = "https://api.yelp.com/v3/businesses/search";
    public static final String API_KEY = "-zQ4y50-JDbyywxHfjTw5QVtT-dg40f5xhBGzRrQX8VCSmiV1pofn7k_ki1OLhuPP7fzAqZQRfxrszgoHlFQitLoffkl7AJZYO36xmpz2LoJLdm0mzvG5SD8nNlMW3Yx";
    private final OkHttpClient client = new OkHttpClient();
    public JSONArray JSONList;

    private MutableLiveData<JSONArray> restaurantList;

    public LiveData<JSONArray> getRestaurants(UserRequest request) {
        if (restaurantList == null) {
            restaurantList = new MutableLiveData<>();
            loadRestaurants(request);
        }
        return restaurantList;
    }

    private void loadRestaurants(UserRequest req) {

        // Do an asynchronous operation to fetch users.

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
                            restaurantList.postValue(resultArray);
                            //activity.resultsReturned(restaurantList);
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

    }

}
