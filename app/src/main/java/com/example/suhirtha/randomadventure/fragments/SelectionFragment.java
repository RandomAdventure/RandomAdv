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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suhirtha.randomadventure.AutoCompleteSuggestions;
import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.models.UserRequest;
import com.xw.repo.BubbleSeekBar;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectionFragment.SelectionListener} interface
 * to handle interaction events.
 */
public class SelectionFragment extends Fragment implements View.OnClickListener {

//--------------------------------------------------------------------------------------------------
    @BindView(R.id.sfSearchButton) Button mSearch;
    @BindView(R.id.sfDistanceBar) BubbleSeekBar mSeekRadius;
    @BindView(R.id.sfPriceSeekBar) BubbleSeekBar mSeekPrice;
    @BindView(R.id.sfRatingBar) RatingBar mRating;
    @BindView(R.id.sfOtherSpinner) Spinner mOther;
    @BindView(R.id.sfMultiAutoComplete) MultiAutoCompleteTextView mCuisineAutoComplete;

    private double mileConversion = 1609.344;

//--------------------------------------------------------------------------------------------------
    private String attributeSelected;
    private boolean attribute;
    String[] suggestions;
//--------------------------------------------------------------------------------------------------
    UserRequest request;
//--------------------------------------------------------------------------------------------------
    private SelectionListener mListener;
//--------------------------------------------------------------------------------------------------
    public String data;
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

        ButterKnife.bind(this, selectionView);

        populateSpinner(selectionView);

        //prevent keyboard from being shown on activity launch
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        AutoCompleteSuggestions auto = new AutoCompleteSuggestions(getContext());
        try {
            suggestions = auto.getSuggestions();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//--------------------------------------------------------------------------------------------------
        ArrayAdapter cuisineAdapter  = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, suggestions);


        mCuisineAutoComplete.setAdapter(cuisineAdapter);
        mCuisineAutoComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

//--------------------------------------------------------------------------------------------------
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

//--------------------------------------------------------------------------------------------------
        mSeekRadius.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "0 mi");
                array.put(5, "30 mi");


                return array;
            }
        });

//--------------------------------------------------------------------------------------------------
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
//--------------------------------------------------------------------------------------------------

        mSearch.setOnClickListener(this);

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
        }
    }

//--------------------------------------------------------------------------------------------------

    public void populateSpinner(View view) {
        //Spinner cuisineSpinner = (Spinner) view.findViewById(R.id.sfCuisineSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.cuisine_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //cuisineSpinner.setAdapter(cuisineAdapter);

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
    }

//--------------------------------------------------------------------------------------------------
    public void buildRequest() {

        String cuisines = mCuisineAutoComplete.getText().toString();

        request = new UserRequest(this.getContext(), this.getActivity())
                .setRadius((int) (mSeekRadius.getProgress() * mileConversion))
                .setMaxPrice(mSeekPrice.getProgress())
                .setCuisines(cuisines)
                .setMinRating(mRating.getRating())
                .setAttribute(attributeSelected);
    }

//--------------------------------------------------------------------------------------------------

}
