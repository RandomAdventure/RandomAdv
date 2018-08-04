package com.example.suhirtha.randomadventure.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;

import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.models.UserRequest;

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
    private SeekBar mSeekRadius;
    private EditText mRadius;
    private EditText mCuisine;
    private RatingBar mRating;
    private EditText mPrice;
//--------------------------------------------------------------------------------------------------
    UserRequest request;
//--------------------------------------------------------------------------------------------------
    private SelectionListener mListener;
//--------------------------------------------------------------------------------------------------

    public SelectionFragment() {
        // Required empty public constructor
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

        mSearch = selectionView.findViewById(R.id.sfSearchButton);
        mDone = selectionView.findViewById(R.id.sfDoneButton);
        mRadius = selectionView.findViewById(R.id.sfEditDistance);
        mCuisine = selectionView.findViewById(R.id.sfEditCuisine);
        mRating = selectionView.findViewById(R.id.sfRatingBar);
        mPrice = selectionView.findViewById(R.id.sfEditPrice);

        mSearch.setOnClickListener(this);
        mDone.setOnClickListener(this);
        // Inflate the layout for this fragment
        return selectionView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sfSearchButton:
                buildRequest();
                mListener.makeRequest(request);
                break;
            case R.id.sfDoneButton:
                mListener.tatumTest();
                break;
        }
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface SelectionListener {
        void makeRequest(UserRequest request);
        void tatumTest();
    }

    public UserRequest buildRequest() {

        // Add attributes to an arraylist
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(mCuisine.getText().toString());

        UserRequest request = new UserRequest(this.getContext(), this.getActivity())
                .setRadius(Integer.parseInt(mRadius.getText().toString()))
                .setMaxPrice(Integer.parseInt(mPrice.getText().toString()))
                .setAttributes(attributes)
                .setMinRating(mRating.getRating())
                .buildURL();

        return request;
    }


}
