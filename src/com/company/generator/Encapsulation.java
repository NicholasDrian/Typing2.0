package com.company.generator;

public interface Encapsulation {

    char[] open = new char[] { '(', '{', '[', '<', '\"', '\'' };
    char[] closed = new char[] { ')', '}', ']', '>', '\"', '\'' };

    static String encapsulate (String s) {
        int index = (int) (Math.random() * open.length);
        return open[index] + s + closed[index];
    }
}
