package com.example.justin.fitchburgline;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.justin.fitchburgline.data.TrainContract;

public class ScheduleActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //  Define CursorAdapter
    TrainCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //  ListView to display inventory
        ListView inventoryItems = (ListView) findViewById(R.id.list);

        //  Empty view stuff to be activated
        View emptyView = findViewById(R.id.empty_view);
        inventoryItems.setEmptyView(emptyView);

        //  Create the Adapter...
        mAdapter = new TrainCursorAdapter(this, null);

        //  ...and attach it to the ListView
        inventoryItems.setAdapter(mAdapter);

        //  Set up the onItemListener to go to the EditScheduleActivity for a single item
        inventoryItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //  Create the Intent, pass in the URI, and set it running
                Intent intent = new Intent(ScheduleActivity.this, EditScheduleActivity.class);
                intent.setData(Uri.withAppendedPath(TrainContract.TrainEntry.CONTENT_URI, Long.toString(id)));
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);

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
