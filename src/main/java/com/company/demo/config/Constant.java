package com.company.demo.config;

import java.util.ArrayList;
import java.util.Arrays;

public class Constant {
    // 7 ngày
    public static final int MAX_AGE_COOKIE = 7 * 24 * 60 * 60;

    public static final int LIMIT_POST_PER_PAGE = 8;

    public static final int PUBLIC_POST = 1;
    public static final int DRAFT_POST = 0;

    public static final int DISCOUNT_PERCENT = 1;
    public static final int DISCOUNT_AMOUNT = 2;

    public static ArrayList<Integer> SIZE_VN = new ArrayList<>(Arrays.asList(35, 36, 37, 38, 39, 40, 41, 42));
    public static final double[] SIZE_US = {2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5};
    public static final double[] SIZE_CM = {21.3, 22.2, 23, 23.8, 24.6, 25.4, 26.2, 27.1};
}
