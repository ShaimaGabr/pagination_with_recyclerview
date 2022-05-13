package com.example.pagination_with_recyclerview;

import java.util.LinkedList;
import java.util.List;

public class DataManger {

    private static DataManger instance;

    private DataManger() {
    }

    public static DataManger getInstance() {
        if (instance == null)
            return instance = new DataManger();
        return instance;
    }

    public List<Movie> getData() {
        List<Movie> allData = new LinkedList<>();
        allData.add(new Movie("android", "xxx"));
        allData.add(new Movie("android_2", "xxx"));
        allData.add(new Movie("android_3", "xxx"));
        allData.add(new Movie("android_4", "xxx_2"));
        allData.add(new Movie("android_5", "xxx_23"));
        allData.add(new Movie("android_6", "xxx_3"));

        return allData;
    }
}
