package com.example.owner.android2.User;

import com.example.owner.android2.User.User;

import java.util.Comparator;

/**
 * Created by k_vol on 18/06/2017.
 */

public class MyComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if (o1.Score > o2.Score) {
            return -1;
        } else if (o1.Score < o2.Score) {
            return 1;
        }
        return 0;
    }
}
