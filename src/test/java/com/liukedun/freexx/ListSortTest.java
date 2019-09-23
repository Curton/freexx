package com.liukedun.freexx;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Covey Liu, covey@liukedun.com
 * Date: 9/23/2019 10:38 AM
 */
public class ListSortTest {
    @Test
    public void run() {
        List<Integer> integers  = new ArrayList<>();

        integers.add(2);
        integers.add(3);
        integers.add(1);
        integers.add(5);
        integers.add(4);

        integers.sort(Collections.reverseOrder());
        integers.forEach(System.out::println);
    }
}
