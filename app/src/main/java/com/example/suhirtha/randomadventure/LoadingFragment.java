package com.example.suhirtha.randomadventure;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

/**
 * Created by togata on 7/27/18.
 */

public class LoadingFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        view = inflater.inflate(R.layout.activity_loading, container, false);
        setRetainInstance(true);
        return view;
    }
}
