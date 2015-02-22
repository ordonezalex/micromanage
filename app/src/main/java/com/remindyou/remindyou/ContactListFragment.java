package com.remindyou.remindyou;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends ListFragment implements LoaderCallbacks<Cursor> {

    private CursorAdapter mAdapter;
    private ArrayAdapter mData;
    List<String> name1 = new ArrayList<String>();
    List<String> phno1 = new ArrayList<String>();
    List<String> intersection = new ArrayList<String>();
    String[] names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create adapter once
        Context context = getActivity();
        int layout = android.R.layout.simple_list_item_1;
        Cursor c = null; // there is no cursor yet
        int flags = 0; // no auto-requery! Loader requeries.
        getAllContacts(getActivity().getContentResolver());
        this.query();
        mAdapter = new SimpleCursorAdapter(context, layout, c, FROM, TO, flags);
        mData = new ArrayAdapter(context, layout);

    }

    private  void getAllContacts(ContentResolver cr) {

        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        Log.i(App.TAG, "in contacts");
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name1.add(name);
            phno1.add(phoneNumber);
            Log.i("phoNo", phoneNumber);
        }

        phones.close();
    }

    //needs to get numbers from parse then compare to numbers from the phone
    private void query()
    {
        Log.i("phnoSize", phno1.size() + "");
        ParseQuery<ParseObject> qry = ParseQuery.getQuery("_User");


        qry.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> people, com.parse.ParseException e) {
                if (e == null) {
                   for (int i = 0; i < people.size(); i++)
                   {
                       String test = people.get(i).getString("Phone");
                       Log.i("ParseTest", test);
//                       intersection1.add(people.get(i).getString("Phone"));
                       for (int j = 0; j < phno1.size(); j++)
                       {
                           if (("1"+phno1.get(j)).equals(people.get(i).getString("Phone")))
                           {
                                intersection.add(people.get(i).getString("Phone"));
                           }
                       }

                   }

                    mData.addAll(intersection);
                    mData.notifyDataSetChanged();

                    Log.i("size", people.size() + "");

                } else {
                    Log.wtf("Build", "Shit");
                }
            }

        });

    }

    public static ContactListFragment newInstance() {

        ContactListFragment fragment = new ContactListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // each time we are started use our listadapter
        setListAdapter(mData);
        // and tell loader manager to start loading
        getLoaderManager().initLoader(0, null, this);
    }


    // columns requested from the database
    private static final String[] PROJECTION = {
            Contacts._ID, // _ID is always required
            Contacts.DISPLAY_NAME_PRIMARY,
            // that's what we want to display
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