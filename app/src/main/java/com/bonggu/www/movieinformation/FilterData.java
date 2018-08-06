package com.bonggu.www.movieinformation;

public class FilterData {
    private static FilterData instance;

    public String genre = "null";
    public String country = "null";
    public int start = 0;

    public static void initInstance() {
        if (instance == null) {
            instance = new FilterData();
        }
    }

    public static void deleteInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public static FilterData getInstance() {
        return instance;
    }
}