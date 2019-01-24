package com.example.justin.fitchburgline.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class TrainContract {


    public static final String CONTENT_AUTHORITY = "com.example.justin.fitchburgline";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TRAINS = "trains";

    /**
     *  Inner class of Inventory contract constants
     */
    public static final class TrainEntry implements BaseColumns {
        //  MIME list type
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_TRAINS;

        //  MIME item type
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_TRAINS;

        //  Content URI for accessing database:  content://com.example.justin.inventoryappone/inventory
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TRAINS);

        //  Table name
        public static final String TABLE_NAME = "inventory";

        //  Database columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TRAIN = "train";
        public static final String COLUMN_DIRECTION = "direction";
        public static final String COLUMN_NORSTA = "northstation";
        public static final String COLUMN_PORTER = "porter";
        public static final String COLUMN_BELMONT = "belmont";
        public static final String COLUMN_WAVERLEY ="waverley";


    }

}
