package com.remindyou.remindyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;

public class CreateReminderFragment extends Fragment {

    /**
     * Constant for our arg id for when we pass in he reminderId into the fragment.
     */
    private static final String ARG_PHONE_NUMBER = "phoneNumber";

    /**
     * The property where we will store the reminder id.
     */
    private String phoneNumber;

    public static CreateReminderFragment newInstance(String phoneNumber) {

        Log.d(App.TAG, "Created CreateReminderFragment.");

        CreateReminderFragment fragment = new CreateReminderFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PHONE_NUMBER, phoneNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_reminder, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        Button reminderSubmitButton = (Button) getActivity().findViewById(R.id.submit_create_reminder_button);
        Button reminderCancelButton = (Button) getActivity().findViewById(R.id.cancel_create_reminder_button);

        reminderSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createReminder();
            }
        });

        reminderCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancelReminder();
            }
        });
    }

    private void createReminder() {

        EditText reminderContentEditText = (EditText) getActivity().findViewById(R.id.reminder_content_edit_text);
        String reminderContent = reminderContentEditText.getText().toString();

        if (reminderContent.isEmpty()) {
            Log.d(App.TAG, "Reminder content is empty.");
        }

        // Start create reminder
        ParseObject reminder = new ParseObject("Reminder");
        reminder.put("to", "13864903600");
        reminder.put("from", "13864903600");
        reminder.put("content", reminderContent);
        reminder.saveInBackground();
        // End create reminder

        Toast.makeText(getActivity(), getString(R.string.reminder_created_prompt), Toast.LENGTH_SHORT).show();
        Log.d(App.TAG, "Reminder created.");

        // Transition to new fragment
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, UserRemindersFragment.newInstance(phoneNumber))
                .commit();
    }

    private void cancelReminder() {

        Log.d(App.TAG, "Reminder canceled.");

        // Go back one, probably to UserRemindersFragment.
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, UserRemindersFragment.newInstance(phoneNumber))
                .commit();
    }
}
