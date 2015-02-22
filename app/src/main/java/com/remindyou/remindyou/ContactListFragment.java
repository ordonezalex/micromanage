package com.remindyou.remindyou;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ContactListFragment extends ListFragment implements LoaderCallbacks<Cursor> {

    private CursorAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create adapter once
        Context context = getActivity();
        int layout = android.R.layout.simple_list_item_1;
        Cursor c = null; // there is no cursor yet
        int flags = 0; // no auto-requery! Loader requeries.
        mAdapter = new SimpleCursorAdapter(context, layout, c, FROM, TO, flags);
    }

    private void readNumbers()
    {


    }

    //needs to get numbers from parse then compare to numbers from the phone
    private void query()
    {
//        Integer amount = Integer.parseInt(Contacts._COUNT);
//        for (int x = 0; x < amount; x++) {
//            ParseQuery<ParseObject> qry = ParseQuery.getQuery("_USER");
//            //phone needs to be equal to contacts number
//            qry.whereEqualTo("Phone", skills.get(x).getString("skillId"));
//            qry.findInBackground(new FindCallback<ParseObject>() {
//                public void done(List<ParseObject> skills, ParseException e) {
//                    if (e == null) {
//                        //display contacts
//                    } else {
//                        Log.wtf("Build", "Shit");
//                    }
//                }
//            });
//        }

    }

    public static ContactListFragment newInstance() {

        ContactListFragment fragment = new ContactListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // each time we are started use our listadapter
        setListAdapter(mAdapter);
        // and tell loader manager to start loading
        getLoaderManager().initLoader(0, null, this);
    }


    // columns requested from the database
    private static final String[] PROJECTION = {
            Contacts._ID, // _ID is always required
            Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER// that's what we want to display
    };

    // and name should be displayed in the text1 textview in item layout
    private static final String[] FROM = { Contacts.DISPLAY_NAME_PRIMARY };
    private static final int[] TO = { android.R.id.text1 };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // load from the "Contacts table"
        Uri contentUri = Contacts.CONTENT_URI;

        // no sub-selection, no sort order, simply every row
        // projection says we want just the _id and the name column
        return new CursorLoader(getActivity(),
                contentUri,
                PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Once cursor is loaded, give it to adapter
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // on reset take any old cursor away
        mAdapter.swapCursor(null);
    }

}