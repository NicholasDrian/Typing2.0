package com.company.generator;

public interface Number {

    static int getInt() {
        return (int) (Math.random() * 1000);
    }

}
