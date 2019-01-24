package com.example.justin.fitchburgline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.justin.fitchburgline.data.TrainContract;

public class TrainCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link TrainCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public TrainCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        //  Find individual views that we want to modify in the list item layout
        TextView trainNumberView = (TextView) view.findViewById(R.id.item);
        TextView northStationTextView = (TextView) view.findViewById(R.id.north_station);
        TextView porterTextView = (TextView) view.findViewById(R.id.porter);
        TextView belmontTextView = (TextView) view.findViewById(R.id.belmont);
        TextView waverleyTextView = (TextView) view.findViewById(R.id.waverley);

        TextView trainDirectionView = (TextView) view.findViewById(R.id.direction);

        //  Attach the Button in the layout
//        Button sellButton = (Button) view.findViewById(R.id.sell_button);

        //  Find the columns of pet attributes that we're interested in; grabbing all the columns to pass them into the Button.setTag
        final int idColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry._ID);
        final int itemColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_TRAIN);
        final int northStationColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_NORSTA);
        final int porterColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_PORTER);
        final int belmontColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_BELMONT);
        final int waverleyColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_WAVERLEY);

        //  Read the item attributes from the Cursor for the current item
        int itemId = cursor.getInt(idColumnIndex);

//        String trainNumber = cursor.getString(itemColumnIndex);
        int trainNumber = cursor.getInt(itemColumnIndex);

        if (trainNumber%2 == 0) {
            trainDirectionView.setText("Inbound");
        } else {
            trainDirectionView.setText("Outbound");
        }

//        long price = cursor.getLong(priceColumnIndex);
        int itemNorthStation = cursor.getInt(northStationColumnIndex);
        int itemPorter = cursor.getInt(porterColumnIndex);
        int itemBelmont = cursor.getInt(belmontColumnIndex);
        int itemWaverley = cursor.getInt(waverleyColumnIndex);

        String resutlNorthStation = String.format("%02d:%02d", itemNorthStation / 100, itemNorthStation % 100);
        String resultPorter = String.format("%02d:%02d", itemPorter / 100, itemPorter % 100);
        String resultBelmont = String.format("%02d:%02d", itemBelmont / 100, itemBelmont % 100);
        String resultWaverley = String.format("%02d:%02d", itemWaverley / 100, itemWaverley % 100);

//        String supplier = cursor.getString(supplierColumnIndex);
//        String supplierPhone = cursor.getString(supplierPhoneColumnIndex);

        //  Ready a ContentValues object for data for insert
        ContentValues tagValues = new ContentValues();

        //  Set the values
        tagValues.put(TrainContract.TrainEntry.COLUMN_TRAIN, trainNumber);
        tagValues.put(TrainContract.TrainEntry.COLUMN_NORSTA, itemNorthStation);
        tagValues.put(TrainContract.TrainEntry.COLUMN_PORTER, itemPorter);
        tagValues.put(TrainContract.TrainEntry.COLUMN_BELMONT, itemBelmont);
        tagValues.put(TrainContract.TrainEntry.COLUMN_WAVERLEY, itemWaverley);

        //  Put the ID and the values into tags for the Button
//        sellButton.setTag(R.string.key_one, itemId);
//        sellButton.setTag(R.string.key_two, tagValues);

        //  Format price with dollar sign
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
//        String currentPrice = numberFormat.format(price / 100.0);

        // Populate fields with extracted properties
        trainNumberView.setText(Integer.toString(trainNumber));
//        itemPriceView.setText(currentPrice);
//        northStationTextView.setText(Integer.toString(itemNorthStation));

        northStationTextView.setText((resutlNorthStation));
        porterTextView.setText((resultPorter));
        belmontTextView.setText(resultBelmont);
        waverleyTextView.setText(resultWaverley);


        //  Create onClickListener to detect clicks on Sell button in InventoryActivity; Sell button
        //  decreases item quantity by 1
//        sellButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //  Get the Button tags; key_one = ID of Sell button parent; key_two = ContentValues
//                //  of the row of the button that was clicked
//                String buttonId = v.getTag(R.string.key_one).toString();
//                ContentValues values = (ContentValues) v.getTag(R.string.key_two);
//
//                //  Get the item quantity from the values
//                int buttonQuantity = values.getAsInteger(TrainContract.TrainEntry.COLUMN_QUANTITY);
//                //  If buttonQuantity < 1, return out of routine because item count can't be reduced
//                if (buttonQuantity < 1) {
//                    return;
//                }
//
//                //  Decrement buttonQuantity by 1; put quantity back in ContentValues
//                values.put(TrainContract.TrainEntry.COLUMN_QUANTITY, --buttonQuantity);
//
//                //  Create the update URI by appending the row ID to the CONTENT_URI
//                Uri sellButtonUri = (Uri.withAppendedPath(TrainContract.TrainEntry.CONTENT_URI, buttonId));
//
//                //  Update the row with the new quantity
//                int row = context.getContentResolver().update(
//                        sellButtonUri,
//                        values,
//                        null,
//                        null);
//
//                if (row != 0) {
//                    Toast.makeText(v.getContext(), "Sell succeeded", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }
    
}
