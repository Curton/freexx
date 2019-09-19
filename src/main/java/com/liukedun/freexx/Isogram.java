package com.liukedun.freexx;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Covey Liu, covey@liukedun.com
 * Date: 9/18/2019 10:24 AM
 */
public class Isogram {
    public static boolean  isIsogram(String str) {
        // ...
        str = str.toLowerCase();
        char[] c = str.toCharArray();
        Set<Character> charSet = new HashSet<>();
        for (char temp : c) {
            if (!charSet.add(temp)) {
                return false;
            }
        }
        return true;
    }
}
