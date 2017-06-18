package com.example.owner.android2.Adapters;

/**
 * Created by k_vol on 18/06/2017.
 */

public class ListItem implements Comparable<ListItem> {
    public String textview1;
    public String textview2;
    public String textview3;

    public ListItem(String t1, String t2, String t3) {
        this.textview1 = t1;
        this.textview2 = t2;
        this.textview3 = t3;
    }

    public ListItem(String t1, String t2) {
        this.textview1 = t1;
        this.textview2 = t2;
    }

    //    Collections.sort(list1, new Comparator<ListItem>() {
//    @Override public int compare(ListItem p1, ListItem p2) {
//        return Integer.parseInt(p1.textview3) - Integer.parseInt(p2.textview3); // Ascending
//    }
//
//});
    @Override
    public int compareTo(ListItem o) {
        // usually toString should not be used,
        // instead one of the attributes or more in a comparator chain
        return toString().compareTo(o.toString());
    }
}
