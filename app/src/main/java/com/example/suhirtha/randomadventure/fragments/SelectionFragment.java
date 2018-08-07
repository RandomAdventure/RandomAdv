package com.example.suhirtha.randomadventure.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.models.UserRequest;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectionFragment.SelectionListener} interface
 * to handle interaction events.
 */
public class SelectionFragment extends Fragment implements View.OnClickListener {

//--------------------------------------------------------------------------------------------------
    private Button mSearch;
    private Button mDone;
    private BubbleSeekBar mSeekRadius;
    private BubbleSeekBar mSeekPrice;
    private Spinner mCuisine;
    private RatingBar mRating;
    private Spinner mOther;
    private double mileConversion = 1609.344;
//--------------------------------------------------------------------------------------------------
    private String cuisineSelected;
    private String attributeSelected;
    private boolean attribute;
//--------------------------------------------------------------------------------------------------
    UserRequest request;
//--------------------------------------------------------------------------------------------------
    private SelectionListener mListener;
//--------------------------------------------------------------------------------------------------

    public SelectionFragment() {

    }

//--------------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//--------------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View selectionView = inflater.inflate(R.layout.fragment_selection, container, false);

        populateSpinner(selectionView);

        mSearch = selectionView.findViewById(R.id.sfSearchButton);
        mDone = selectionView.findViewById(R.id.sfDoneButton);
        mSeekRadius = selectionView.findViewById(R.id.sfDistanceBar);
        mCuisine = selectionView.findViewById(R.id.sfCuisineSpinner);
        mRating = selectionView.findViewById(R.id.sfRatingBar);
        mSeekPrice = selectionView.findViewById(R.id.sfPriceSeekBar);
        mOther = selectionView.findViewById(R.id.sfOtherSpinner);

        //Cuisine Spinner
        mCuisine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {   ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                cuisineSelected = parent.getItemAtPosition(position).toString(); //this is your selected item
                Log.d("Cuisine Chosen", cuisineSelected);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //'Other' Spinner
        mOther.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {   ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                attributeSelected = parent.getItemAtPosition(position).toString(); //this is your selected item
                if (attributeSelected == null || attributeSelected.equals("")) {
                    attribute = false;
                }
                Log.d("Attribute chosen", attributeSelected);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        mSeekPrice.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "$");
                array.put(1, "$$");
                array.put(2, "$$$");
                array.put(3, "$$$$");

                return array;
            }
        });

        mSearch.setOnClickListener(this);
        mDone.setOnClickListener(this);

        // Inflate the layout for this fragment
        return selectionView;
    }

//--------------------------------------------------------------------------------------------------

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sfSearchButton:
                Log.d("Search Button", "Pressed!");
                buildRequest();
                mListener.makeRequest(request);
                break;
            case R.id.sfDoneButton:
                mListener.tatumTest();
                break;
        }
    }

//--------------------------------------------------------------------------------------------------

    public void populateSpinner(View view) {
        Spinner cuisineSpinner = (Spinner) view.findViewById(R.id.sfCuisineSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.cuisine_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        cuisineSpinner.setAdapter(cuisineAdapter);

        Spinner otherSpinner = (Spinner) view.findViewById(R.id.sfOtherSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> otherAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.other_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        otherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        otherSpinner.setAdapter(otherAdapter);
    }

//--------------------------------------------------------------------------------------------------

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectionListener) {
            mListener = (SelectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

//--------------------------------------------------------------------------------------------------

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//--------------------------------------------------------------------------------------------------
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface SelectionListener {
        void makeRequest(UserRequest request);
        void tatumTest();
    }

//--------------------------------------------------------------------------------------------------
    public void buildRequest() {

        // Add attributes to an arraylist
        ArrayList<String> terms = new ArrayList<>();
        terms.add(cuisineSelected);

        request = new UserRequest(this.getContext(), this.getActivity())
                .setRadius((int) (mSeekRadius.getProgress() * mileConversion))
                .setMaxPrice(mSeekPrice.getProgress())
                .setTerms(terms)
                .setMinRating(mRating.getRating())
                .setAttribute(attributeSelected);
    }

//--------------------------------------------------------------------------------------------------

}
