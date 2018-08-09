package com.example.suhirtha.randomadventure.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.suhirtha.randomadventure.AppDatabase;
import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;

import java.util.List;

/**
 * Created by anitac on 8/3/18.
 */

public class RestaurantsChosenViewModel extends AndroidViewModel {

    private LiveData<List<DatabaseRestaurant>> mRestaurants;
    private AppDatabase appDatabase;

    public RestaurantsChosenViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        mRestaurants = appDatabase.restaurantDao().getAllRestaurants();
    }

    public LiveData<List<DatabaseRestaurant>> getmRestaurants() {
        return mRestaurants; } //if null, setvalue(mRestaurants)

    public void deleteRestaurant(DatabaseRestaurant restaurant){
        new deleteAsyncTask(appDatabase).execute(restaurant);
    }

//    public void deleteAllRestaurants() {
//        new deleteAsyncTask(appDatabase)
//    }

    private static class deleteAsyncTask extends AsyncTask<DatabaseRestaurant, Void, Void> {
        private AppDatabase db;
        public deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final DatabaseRestaurant... params) {
            db.restaurantDao().delete(params[0]);
            return null;
        }
    }

}
