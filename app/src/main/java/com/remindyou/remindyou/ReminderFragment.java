package com.remindyou.remindyou;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReminderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderFragment extends Fragment {
    /** Constant for our arg id for when we pass in he reminderId into the fragment. */
    private static final String ARG_REMINDER_ID = "reminderId";

    /** The property where we will store the reminder id. */
    private String mReminderId;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param reminderId The ID associated with the reminder to pull.
     * @return A new instance of fragment ReminderFragment.
     */
    public static ReminderFragment newInstance(String reminderId) {
        ReminderFragment fragment = new ReminderFragment();

        // Add the reminderId that was passed in into the new fragment as a bundled arg
        Bundle args = new Bundle();
        args.putString(ARG_REMINDER_ID, reminderId);
        fragment.setArguments(args);

        return fragment;
    }

    public ReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mReminderId = getArguments().getString(ARG_REMINDER_ID);
        }

        ParseQuery<ParseObject> qry = ParseQuery.getQuery("Reminders");
        qry.whereEqualTo("objectId", this.mReminderId);
        qry.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> reminders, ParseException e) {
                if (e == null) {
                    // Do stuff
                } else {
                    Log.wtf("ReminderFragment", "What a terrible failure!");
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
