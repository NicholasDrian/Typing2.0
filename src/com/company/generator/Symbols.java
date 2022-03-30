package com.company.generator;

public interface Symbols {
    char[] data = new char[] {'~', '@', '#', '$', '%', '^', '&', '*', '_', '+', '-', '=', '/', '|'};

    static char getMark() {
        return data[(int) (Math.random() * data.length)];
    }
}
