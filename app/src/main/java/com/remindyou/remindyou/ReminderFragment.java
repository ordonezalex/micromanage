package com.remindyou.remindyou;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ReminderFragment extends Fragment {

    /** Constant for our arg id for when we pass in he reminderId into the fragment. */
    private static final String ARG_REMINDER_ID = "reminderId";

    /** The property where we will store the reminder id. */
    private String mReminderId;

    public ReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
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
                    reminders.get(0).getString("from");
                } else {
                    Log.wtf("ReminderFragment", "No reminder with id " + mReminderId + " exists!");
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
}
