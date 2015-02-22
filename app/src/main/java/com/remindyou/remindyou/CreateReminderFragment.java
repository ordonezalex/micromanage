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

    private static final String ARG_TO_PHONE_NUMBER = "toPhoneNumber";
    private static final String ARG_FROM_PHONE_NUMBER = "fromPhoneNumber";

    private String toPhoneNumber;
    private String fromPhoneNumber;

    public static CreateReminderFragment newInstance(String toPhoneNumber, String fromPhoneNumber) {

        Log.d(App.TAG, "Created CreateReminderFragment.");

        CreateReminderFragment fragment = new CreateReminderFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TO_PHONE_NUMBER, toPhoneNumber);
        args.putString(ARG_FROM_PHONE_NUMBER, fromPhoneNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.setToPhoneNumber(getArguments().getString(ARG_TO_PHONE_NUMBER));
            this.setFromPhoneNumber(getArguments().getString(ARG_FROM_PHONE_NUMBER));
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
        reminder.put("to", this.getToPhoneNumber());
        reminder.put("from", this.getFromPhoneNumber());
        reminder.put("content", reminderContent);
        reminder.saveInBackground();
        // End create reminder

        Toast.makeText(getActivity(), getString(R.string.reminder_created_prompt), Toast.LENGTH_SHORT).show();
        Log.d(App.TAG, "Reminder created.");

        // Transition to new fragment
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, UserRemindersFragment.newInstance(toPhoneNumber))
                .commit();
    }

    private void cancelReminder() {

        Log.d(App.TAG, "Reminder canceled.");

        // Go back one, probably to UserRemindersFragment.
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, UserRemindersFragment.newInstance(toPhoneNumber))
                .commit();
    }

    public void setToPhoneNumber(String toPhoneNumber) {

        this.toPhoneNumber = toPhoneNumber;
    }

    public void setFromPhoneNumber(String fromPhoneNumber) {

        this.fromPhoneNumber = fromPhoneNumber;
    }

    public String getToPhoneNumber() {

        return this.toPhoneNumber;
    }

    public String getFromPhoneNumber() {

        return this.fromPhoneNumber;
    }
}
