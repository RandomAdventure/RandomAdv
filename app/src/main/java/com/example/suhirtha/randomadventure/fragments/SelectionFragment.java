package com.example.suhirtha.randomadventure.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.models.UserRequest;
import com.xw.repo.BubbleSeekBar;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzhl.runOnUiThread;

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
    //private Spinner mCuisine;
    private RatingBar mRating;
    private Spinner mOther;
    private double mileConversion = 1609.344;
    //private AutoCompleteTextView mAutoComplete;
    private MultiAutoCompleteTextView mAutoComplete2;
//--------------------------------------------------------------------------------------------------
    private String cuisineSelected;
    private String attributeSelected;
    private boolean attribute;
//--------------------------------------------------------------------------------------------------
    UserRequest request;
//--------------------------------------------------------------------------------------------------
    private SelectionListener mListener;
//--------------------------------------------------------------------------------------------------
    private String[] testArray = new String[] {"Dessert", "Deli", "Brunch", "Indian", "Buffet", "Ice cream", "Greek", "Fast food",
                                        "Italian", "Ramen", "Noodles", "Seafood", "Thai", "Ethiopian", "Bars", "Bubble Tea", "Mexican",
                                        "Halal", "Japanese", "Sushi", "Vegetarian", "Vegan", "Waffles", "Salad", "Hot Dogs", "Food Trucks",
                                        "Poke", "Pretzels", "Tea", "Kombucha", "Brazilian", "Burgers", "Cafes", "Cajun", "Cake",
                                        "Cheesecake", "Chinese", "Creperies", "Cuban", "Danish", "Dumplings"};
//--------------------------------------------------------------------------------------------------
    public String data;
    public List<String> suggest;
    ArrayAdapter<String> mCuisineAdapter;

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
        //mCuisine = selectionView.findViewById(R.id.sfCuisineSpinner);
        mRating = selectionView.findViewById(R.id.sfRatingBar);
        mSeekPrice = selectionView.findViewById(R.id.sfPriceSeekBar);
        mOther = selectionView.findViewById(R.id.sfOtherSpinner);
        //mAutoComplete = selectionView.findViewById(R.id.sfAutoComplete);
        mAutoComplete2 = (MultiAutoCompleteTextView) selectionView.findViewById(R.id.sfMultiAutoComplete);

        ArrayAdapter cuisineAdapter  = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, testArray);

        //mAutoComplete.setAdapter(cuisineAdapter);
        //mAutoComplete.setThreshold(1);

        mAutoComplete2.setAdapter(cuisineAdapter);
        mAutoComplete2.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        mAutoComplete2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newText = charSequence.toString();
                new getSuggestions().execute(newText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

        //gets remote data asynchronously and adds it to AutoCompleteTextView
        //RemoteData remoteData = new RemoteData(this.getContext());
        //remoteData.getStoreData();



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
        void tatumTest();
    }

//--------------------------------------------------------------------------------------------------
    public void buildRequest() {

        // Add attributes to an arraylist
        String terms = mAutoComplete2.getText().toString();

        request = new UserRequest(this.getContext(), this.getActivity())
                .setRadius((int) (mSeekRadius.getProgress() * mileConversion))
                .setMaxPrice(mSeekPrice.getProgress())
                .setTerms(terms)
                .setMinRating(mRating.getRating())
                .setAttribute(attributeSelected);
    }

//--------------------------------------------------------------------------------------------------

    public class getSuggestions extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... key) {
            String newText = key[0];
            newText = newText.trim();
            newText = newText.replace(" ", "+");
            try{

                HttpClient hClient = new DefaultHttpClient();
                HttpGet hGet = new HttpGet("https://www.yelp.com/developers/documentation/v3/all_category_list/categories.json");
                ResponseHandler<String> rHandler = new BasicResponseHandler();
                data = hClient.execute(hGet,rHandler);
                Log.d("data", data);
                suggest = new ArrayList<>();
                JSONArray jArray = new JSONArray(data);
                for(int i=0;i < jArray.getJSONArray(1).length();i++){
                    String SuggestKey = jArray.getJSONArray(1).getString(i);
                    suggest.add(SuggestKey);
                }

            }catch(Exception e){
                Log.w("Error", e.getMessage());
            }
            runOnUiThread(new Runnable(){
                public void run(){
                    mCuisineAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, suggest);
                    //mAutoComplete2.setAdapter(mCuisineAdapter);
                    mCuisineAdapter.notifyDataSetChanged();
                }
            });

            return null;
        }

    }
}
