package com.example.suhirtha.randomadventure.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.models.MenuRow;
import com.sysdata.widget.accordion.ItemAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccordionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AccordionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public AccordionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accordion, container, false);


    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        //TODO - initialize fields
        //TODO - onClickListeners

        ItemAdapter.OnItemClickedListener mListener = new ItemAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(ItemAdapter.ItemViewHolder<?> viewHolder, int id) {
                ItemAdapter.ItemHolder itemHolder = viewHolder.getItemHolder();
                MenuRow item = ((MenuRow) itemHolder.item);

                switch (id) {
                    case ItemAdapter.OnItemClickedListener.ACTION_ID_COLLAPSED_VIEW:
                        Toast.makeText(view.getContext(), String.format("Collapsed %s clicked!", item.getTitle()), Toast.LENGTH_LONG).show();

                        break;
                    case ItemAdapter.OnItemClickedListener.ACTION_ID_EXPANDED_VIEW:
                        Toast.makeText(view.getContext(), String.format("Expanded %s clicked!", item.getTitle()), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        // do nothing
                        break;
                }
            }
        };



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
