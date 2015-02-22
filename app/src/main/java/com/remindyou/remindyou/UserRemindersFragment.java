package com.remindyou.remindyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserRemindersFragment extends Fragment {

    // Constant for our arg id for when we pass in he reminderId into the fragment.
    private static final String ARG_PHONE_NUMBER = "phoneNumber";

    // The property where we will store the reminder id.
    private String mPhoneNumber;

    public static UserRemindersFragment newInstance(String phoneNumber) {

        UserRemindersFragment fragment = new UserRemindersFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PHONE_NUMBER, phoneNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            this.mPhoneNumber = getArguments().getString(ARG_PHONE_NUMBER);
        }

        ParseQuery<ParseObject> queryUser = ParseQuery.getQuery("_User");
        queryUser.whereEqualTo("Phone", this.mPhoneNumber);
        queryUser.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d(App.TAG, "Size: " + objects.size());
                    String objectID = objects.get(0).getObjectId();

                    Log.d(App.TAG, "Found a user: " + objectID);

                    ParseQuery<ParseObject> queryReminder = ParseQuery.getQuery("Reminder");
                    queryReminder.whereEqualTo("objectId", objectID);
                    queryReminder.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> reminders, ParseException e) {

                            if (e == null) {

                                ListView view = (ListView) getActivity().findViewById(R.id.user_reminders_list_view);

                                ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                                        android.R.layout.simple_list_item_1, android.R.id.text1, reminders);

                                // Assign adapter to ListView
                                view.setAdapter(adapter);
                            } else {
                                Log.wtf("User Reminders Fragment", "Couldn't load reminders?");
                            }
                        }
                    });
                } else {
                    // Something went wrong.
                }
            }
        });

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
//        query.whereEqualTo("Phone", "13056085012");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> parseObjects, ParseException e) {
//                if (e == null) {
//
//                    String objectID = parseObjects.get(0).getString("objectId");
//
//                    Log.d(App.TAG, "Found a user: " + objectID);
//
//                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Reminder");
//                    query.whereEqualTo("objectId", objectID);
//                    query.findInBackground(new FindCallback<ParseObject>() {
//                        public void done(List<ParseObject> reminders, ParseException e) {
//                            if (e == null) {
//
//                                ListView view = (ListView) getActivity().findViewById(R.id.user_reminders_list_view);
//
//                                ListAdapter adapter = new ArrayAdapter<>(getActivity(),
//                                        android.R.layout.simple_list_item_1, android.R.id.text1, reminders);
//
//                                // Assign adapter to ListView
//                                view.setAdapter(adapter);
//
//                            } else {
//                                Log.wtf("User Reminders Fragment", "Couldn't load reminders?");
//                            }
//                        }
//                    });
//
//                } else {
//                    Log.wtf("User Reminders Fragment", "No phone number associated? What?");
//                }
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_reminders, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_user_reminders, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.action_new_reminder) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, CreateReminderFragment.newInstance(mPhoneNumber))
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
