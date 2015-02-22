package com.remindyou.remindyou;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String userPhoneNumber = tMgr.getLine1Number();

        if (userPhoneNumber == null) {
            Log.wtf(App.TAG, "User does not have a SIM card, or has multiple SIM cards.");
        } else {
            Log.d(App.TAG, "User's phone number: " + userPhoneNumber);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, UserRemindersFragment.newInstance(userPhoneNumber))
                    .commit();
        }

        // Maybe we should do something if we do not get a number from them.
        // If we do something, then we should reverse the if-else statement.
        // We should check if the phone number is not null, then show the fragment.
        // If the phone number is null, then we do whatever else (without else statement).
    }
}
