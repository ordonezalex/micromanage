package com.remindyou.remindyou;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserRemindersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserRemindersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRemindersFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;
    private String phoneNumber;

    public UserRemindersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserRemindersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserRemindersFragment newInstance(String number) {

        UserRemindersFragment fragment = new UserRemindersFragment();

        fragment.setPhoneNumber(number);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("Phone", phoneNumber);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {

                    String objectID = parseObjects.get(0).getString("objectId");

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Reminder");
                    query.whereEqualTo("objectId", objectID);
                    query.findInBackground(new FindCallback<ParseObject>() {
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
                    Log.wtf("User Reminders Fragment", "No phone number associated? What?");
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_reminders, container, false);
    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
