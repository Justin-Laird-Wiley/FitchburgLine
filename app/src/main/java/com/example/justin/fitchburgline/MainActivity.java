package com.example.justin.fitchburgline;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.justin.fitchburgline.data.TrainContract;

import java.util.ArrayList;

//public class MainActivity extends AppCompatActivity {

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //  Class name constant for Log entries
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    //  int constant for Loader ID
    private static final int LOADER_ID = 1;

    //  Define CursorAdapter
    TrainCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.station_list);

        // Setup FAB to open EditScheduleActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditScheduleActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<Station> stations = new ArrayList<Station>();

        stations.add(new Station("North Station", "Fitchburg"));
        stations.add(new Station("Porter", "Fitchburg"));
        stations.add(new Station("Belmont", "Fitchburg"));
        stations.add(new Station("Waverley", "Fitchburg"));
//        stations.add(new Station("Waltham", "Fitchburg"));
//        stations.add(new Station("Brandeis/Roberts", "Fitchburg"));
//        stations.add(new Station("Kendal Green", "Fitchburg"));
//        stations.add(new Station("Lincoln", "Fitchburg"));
//        stations.add(new Station("Concord", "Fitchburg"));
//        stations.add(new Station("West Concord", "Fitchburg"));
//        stations.add(new Station("South Acton", "Fitchburg"));
//        stations.add(new Station("Littleton/Rte. 495", "Fitchburg"));
//        stations.add(new Station("Ayer", "Fitchburg"));
//        stations.add(new Station("Shirley", "Fitchburg"));
//        stations.add(new Station("North Leominster", "Fitchburg"));
//        stations.add(new Station("Fitchburg", "Fitchburg"));
//        stations.add(new Station("Wachusett", "Fitchburg"));

        StationAdapter StationAdapter = new StationAdapter(this, stations);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(StationAdapter);


//        //  ListView to display inventory
//        ListView inventoryItems = (ListView) findViewById(R.id.list);
//
//        //  Empty view stuff to be activated
//        View emptyView = findViewById(R.id.empty_view);
//        inventoryItems.setEmptyView(emptyView);
//
//        //  Create the Adapter...
//        mAdapter = new TrainCursorAdapter(this, null);
//
//        //  ...and attach it to the ListView
//        inventoryItems.setAdapter(mAdapter);
//
        //  Set up the onItemListener to go to the EditScheduleActivity for a single item
//        inventoryItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //  Create the Intent, pass in the URI, and set it running
//                Intent intent = new Intent(MainActivity.this, EditScheduleActivity.class);
//                intent.setData(Uri.withAppendedPath(TrainContract.TrainEntry.CONTENT_URI, Long.toString(id)));
//                startActivity(intent);

                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                intent.setData(Uri.withAppendedPath(TrainContract.TrainEntry.CONTENT_URI, Long.toString(id)));
                startActivity(intent);

            }
        });
//
//        //  Start the Loader to populate the ListView
//        getSupportLoaderManager().initLoader(0, null, this);

    }

    /**
     * Method to insert dummy data in database
     */
    private void insertDummyItem() {

        ContentValues values = new ContentValues();

        //  Construct some dummy data
        values.put(TrainContract.TrainEntry.COLUMN_TRAIN, 400);
        values.put(TrainContract.TrainEntry.COLUMN_NORSTA, 450);
        values.put(TrainContract.TrainEntry.COLUMN_PORTER, 522);
        values.put(TrainContract.TrainEntry.COLUMN_BELMONT, 1122);
        values.put(TrainContract.TrainEntry.COLUMN_WAVERLEY, 1532);

        getContentResolver().insert(TrainContract.TrainEntry.CONTENT_URI, values);

        Toast.makeText(this, "Dummy data inserted", Toast.LENGTH_LONG).show();
        Log.i(LOG_TAG, "Insert pet uri " + TrainContract.TrainEntry.COLUMN_TRAIN);
    }

    /**
     * Method to delete all the items in the inventory
     */
    private void deleteAllItems() {
        int rowsDeleted = getContentResolver().delete(TrainContract.TrainEntry.CONTENT_URI, null, null);
        Toast.makeText(this, rowsDeleted + " item rows deleted", Toast.LENGTH_LONG).show();
    }

    /**
     * Method to create options menu in Action Bar
     *
     * @param menu - menu layout
     * @return - return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Method to determine which menu option has been selected
     *
     * @param item - selected item
     * @return - return true if an item is selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertDummyItem();
                return true;

            case R.id.action_edit_train:
//                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
//                startActivity(intent);
                return true;

            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllItems();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new android.support.v4.content.CursorLoader(this,
                TrainContract.TrainEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }
}
