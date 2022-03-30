package com.company.generator;

public interface Punctuation {
    char[] punctuationMarks = new char[] { '!', '.', '?', ';', ':', ',', '.'};

    static char getMark() {
        return punctuationMarks[(int) (Math.random() * punctuationMarks.length)];
    }
}
