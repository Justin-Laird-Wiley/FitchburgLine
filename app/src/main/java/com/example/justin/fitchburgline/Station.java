package com.example.justin.fitchburgline;

public class Station {

    private String mStationName;
    private String mRailLine;

    public Station(String station, String railLine) {
        mStationName = station;
        mRailLine =railLine;
    }

    public String getmStationName() {
        return mStationName;
    }

    public String getmRailLine() {
        return mRailLine;
    }

}
