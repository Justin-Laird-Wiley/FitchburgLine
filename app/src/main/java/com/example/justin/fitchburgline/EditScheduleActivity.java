package com.example.justin.fitchburgline;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.justin.fitchburgline.data.TrainContract;

public class EditScheduleActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {


    //  Class name constant for Log entries
    private static final String LOG_TAG = EditScheduleActivity.class.getSimpleName();

    //  ID constant for loader
    private static final int EXISTING_LOADER = 1;

    //  EditText fields
    private EditText mItemEditText;
    private EditText mNorthStationEditText;
    private EditText mPorterEditText;
    private EditText mBelmontEditText;
    private EditText mWaverleyEditText;

    //  URI of the current item being edited
    private Uri mCurrentItemUri;

    //  Boolean set to true if any field in item has been changed
    private boolean mItemHasChanged = false;

    //  int quantity of items
    private int mItemQuantity;

    //  Boolean; true if all input valid
    public boolean inputIsValid = false;

    Cursor goodCursor = null;

    //  Create onTouchListener for screen changes
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;
            return false;
        }
    };

    /**
     * Method that receives Intent from InventoryActivity; attaches all views in edit display;
     * sets up onTouchListener's for each EditText views
     *
     * @param savedInstanceState - any parameters passed in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        //  Get the Intent from InventoryActivity; pull out the URI of the item
        Intent intent = getIntent();
        mCurrentItemUri = intent.getData();

        //  If there is no URI, create and insert a new item record
        if (mCurrentItemUri == null) {
            setTitle("Add an Item");
            invalidateOptionsMenu();
            Toast.makeText(this, "Please fill all data fields", Toast.LENGTH_SHORT).show();
            //  ...else initialize the loader that will pull up the item row to be edited
        } else {
            setTitle("Edit an Item");
            getSupportLoaderManager().initLoader(EXISTING_LOADER, null, this);
        }

        //  Attach all the relevant TextView's in the editor layout
        mItemEditText = (EditText) findViewById(R.id.edit_item_name);
        mNorthStationEditText = (EditText) findViewById(R.id.edit_north_station);
        mPorterEditText = (EditText) findViewById(R.id.edit_porter);
        mBelmontEditText = (EditText) findViewById(R.id.edit_belmont);
        mWaverleyEditText = (EditText) findViewById(R.id.edit_waverley);

        //  Set up the onTouchListener's for each EditText view
        mItemEditText.setOnTouchListener(mTouchListener);
        mNorthStationEditText.setOnTouchListener(mTouchListener);
        mPorterEditText.setOnTouchListener(mTouchListener);
        mBelmontEditText.setOnTouchListener(mTouchListener);
        mWaverleyEditText.setOnTouchListener(mTouchListener);

    }

    /**
     * Method to insert item into database
     */
    private void saveItem() {
        //  New URI of newly inserted item record into database
        Uri newUri;

        //  Pull the input data from the editor view
        String itemString = mItemEditText.getText().toString().trim();
        String northStationString = mNorthStationEditText.getText().toString().trim();
        String porterString = mPorterEditText.getText().toString().trim();
        String belmontString = mBelmontEditText.getText().toString().trim();
        String waverleyString = mWaverleyEditText.getText().toString().trim();

        //  If all the fields are empty, make a Toast, set inputIsValid = false, and jump out
        if (TextUtils.isEmpty(itemString) &&
                TextUtils.isEmpty(northStationString) &&
                TextUtils.isEmpty(porterString) &&
                TextUtils.isEmpty(belmontString) &&
                TextUtils.isEmpty(waverleyString)
                ) {

            inputIsValid = false;
            Toast.makeText(this, "All data fields are empty -- fill them in", Toast.LENGTH_SHORT).show();
            return;
        }

        //  If itemString is empty, make a Toast, set inputIsValid = false, and jump out
        if (TextUtils.isEmpty(itemString) || itemString == null) {
            inputIsValid = false;
            Toast.makeText(this, "Please enter valid input in item field", Toast.LENGTH_SHORT).show();
            return;
        }

        //  If northStationString is empty, make a Toast, set inputIsValid = false, and jump out
        if (TextUtils.isEmpty(northStationString) || northStationString == null) {
            inputIsValid = false;
            Toast.makeText(this, "Please enter valid input in North Station field", Toast.LENGTH_SHORT).show();
            return;
        }

        //  If porterString is empty, make a Toast, set inputIsValid = false, and jump out
        if (TextUtils.isEmpty(porterString) || porterString == null) {
            inputIsValid = false;
            Toast.makeText(this, "Please enter valid input in price field", Toast.LENGTH_SHORT).show();
            return;
        }

        //  If belmontString is empty, make a Toast, set inputIsValid = false, and jump out
        if (TextUtils.isEmpty(belmontString) || belmontString == null) {
            inputIsValid = false;
            Toast.makeText(this, "Please enter valid input in price field", Toast.LENGTH_SHORT).show();
            return;
        }

        //  If waverleyString is empty, make a Toast, set inputIsValid = false, and jump out
        if (TextUtils.isEmpty(waverleyString) || waverleyString == null) {
            inputIsValid = false;
            Toast.makeText(this, "Please enter valid input in price field", Toast.LENGTH_SHORT).show();
            return;
        }


        //  ...else...
//        else {
//            //  ...if there are any '$', ',', or '.' -- pull them out so parseInt works
//            priceString = priceString.replace("$", "");
//            priceString = priceString.replace(".", "");
//            priceString = priceString.replace(",", "");
//        }


        //  If we clear all the input checks, set inputIsValid to true
        inputIsValid = true;

        //  Convert the String's to int's as needed
//        int priceInt = Integer.parseInt(priceString);
//        int quantityInt = Integer.parseInt(quantityString);

        //  Format the phone number field
//        String formattedSupplierPhoneNumber = PhoneNumberUtils.formatNumber(supplierPhoneNoString);

        //  Ready a ContentValues object for data for insert
        ContentValues values = new ContentValues();

        //  Set the values
        values.put(TrainContract.TrainEntry.COLUMN_TRAIN, itemString);
        values.put(TrainContract.TrainEntry.COLUMN_NORSTA, northStationString);
        values.put(TrainContract.TrainEntry.COLUMN_PORTER, porterString);
        values.put(TrainContract.TrainEntry.COLUMN_BELMONT, belmontString);
        values.put(TrainContract.TrainEntry.COLUMN_WAVERLEY, waverleyString);

        if (mCurrentItemUri == null) {
            newUri = getContentResolver().insert(TrainContract.TrainEntry.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, "Insert failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Insert success", Toast.LENGTH_SHORT).show();
            }
        } else {
            String[] selectionArgs = new String[]{String.valueOf(ContentUris.parseId(mCurrentItemUri))};

            int row = getContentResolver().update(mCurrentItemUri,
                    values,
                    TrainContract.TrainEntry._ID,
                    selectionArgs);

            if (row == -1) {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update succeeded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Method to delete single item from database
     */
    private void deleteItem() {
        //  Delete the row at mCurrentUri; return number of rows deleted
        int rowDeleted = getContentResolver().delete(mCurrentItemUri,
                null, null);

        //  Display Toast message
        if (rowDeleted == 0) {
            Toast.makeText(this, "No item deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
        }

        //  Close EditScheduleActivity and return to InventoryActivity
        finish();
    }

    /**
     * Method to set up the EditScheduleActivity menu in the Action Bar
     *
     * @param menu - the menu to be displayed
     * @return - return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    /**
     * Method to register selected menu item
     *
     * @param item - menu item to act upon
     * @return - return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            //  Use check in Action Bar to save/update current item record
            case R.id.action_save:
                //  Attempt to save current data in TextEdit views
                saveItem();
                //  If all entered data is valid, then exit EditScheduleActivity and return to InventoryActivity
                if (inputIsValid) {
                    finish();
                }
                return true;

            //  Use delete option in Action Bar menu to delete current record
            case R.id.action_delete:
                //  If URI is not null, delete the item
                if (mCurrentItemUri != null) {
                    showDeleteConfirmationDialog();
                }
                return true;

            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mItemHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditScheduleActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditScheduleActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to block delete option when inserting a new item
     *
     * @param menu - menu to update
     * @return - always return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentItemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    /**
     * Method to decrement inventory by 1 by using Decrement button
     *
     * @param view - Button view passed in
     */
//    public void decrementQuantity(View view) {
//        //  If the current quantity is less than 1, don't decrement
//        if (mItemQuantity < 1) {
//            return;
//        }
//
//        //  Get the current quantity from the instance variable...
//        int quantity = mItemQuantity;
//        //  ...and decrement it by 1...
//        quantity -= 1;
//        //  ...then update the display...
//        mQuantityEditText.setText(Integer.toString(quantity));
//        //  ...and update the database
//        saveItem();
//    }

    /**
     * Method to increment inventory by 1 using Increment button
     *
     * @param view - Button view passed in
     */
//    public void incrementQuantity(View view) {
//
//        //  Get the current quantity from the instance variable...
//        int quantity = mItemQuantity;
//        //  ...and increment it by 1...
//        quantity += 1;
//        //  ...then update the display...
//        mQuantityEditText.setText(Integer.toString(quantity));
//        //  ...and update the database
//        saveItem();
//    }

    /**
     * Method to create delete confirmation dialog box
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Method to generate dialog about unsaved changes
     *
     * @param discardButtonClickListener - listener
     */
    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        //  Create an AlertDialog.Builder and set the message, and click listeners
        //  for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        //  Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Method to signal back button has been pressed; have changes been made?
     */
    @Override
    public void onBackPressed() {
        //  If the pet hasn't changed, continue with handling back button press
        if (!mItemHasChanged) {
            super.onBackPressed();
            return;
        }

        //  Otherwise if there are unsaved changes, setup a dialog to warn the user.
        //  Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };
        //  Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    /**
     * Mandatory Loader method that queries the database and fills the Cursor with the current
     * row of item information
     *
     * @param id   - row ID
     * @param args - selection args if needed
     * @return - Cursor with item information
     */
    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //  Set projection to grab all columns
        String[] projection = {TrainContract.TrainEntry._ID,
                TrainContract.TrainEntry.COLUMN_TRAIN,
                TrainContract.TrainEntry.COLUMN_NORSTA,
                TrainContract.TrainEntry.COLUMN_PORTER,
                TrainContract.TrainEntry.COLUMN_BELMONT,
                TrainContract.TrainEntry.COLUMN_WAVERLEY
        };

        return new CursorLoader(this,
                mCurrentItemUri,
                projection,
                null,
                null,
                null);
    }

    /**
     * Mandatory Loader method that reads the item data out of the Cursor and puts it into
     * the EditText fields of the EditScheduleActivity
     *
     * @param loader - Loader that filled Cursor
     * @param cursor - Cursor with item data
     */
    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {

        //  If the cursor is null or has no rows, don't proceed
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        //  If the cursor can be moved to the first row, then read the contents and
        if (cursor.moveToFirst()) {

            //  Find the columns of pet attributes that we're interested in
            int itemColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_TRAIN);
            int northStationColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_NORSTA);
            int porterColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_PORTER);
            int belmontColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_BELMONT);
            int waverleyColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_WAVERLEY);

            //  Extract out the value from the Cursor for the given column index
            String item = cursor.getString(itemColumnIndex);
            String northStation = cursor.getString(northStationColumnIndex);
            String porter = cursor.getString(porterColumnIndex);
            String belmont = cursor.getString(belmontColumnIndex);
            String waverley = cursor.getString(waverleyColumnIndex);

            //  Format price with dollar sign
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
//            String currentPrice = numberFormat.format(price / 100.0);

            //  Put quantity in instance variable to be used for decrement/increment button
//            mItemQuantity = quantity;

            // Update the views on the screen with the values from the database
            mItemEditText.setText(item);
            mNorthStationEditText.setText(northStation);
            mPorterEditText.setText(porter);
            mBelmontEditText.setText(belmont);
            mWaverleyEditText.setText(waverley);
        }
    }

    /**
     * Mandatory Loader method that resets all the EditText fields
     *
     * @param loader - Loader that fills the Cursor being reset
     */
    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        mItemEditText.setText("");
        mNorthStationEditText.setText("");
        mPorterEditText.setText("");
        mBelmontEditText.setText("");
        mWaverleyEditText.setText("");

    }

}
