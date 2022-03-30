package com.company.generator;

public interface Generator {

    static String generateCode1(int maxLength) {
        String result = "";
        int size = 0;
        while (size < maxLength - 5) {
            String word = Words.getRandomList();
            if (word.length() + size + 1 < maxLength) {
                result += ' ' + word;
                size += word.length() + 1;
            }
        }
        return result.substring(1);
    }

    static String generateCode2(int maxLength) {
        String result = "";
        int size = 0;
        while (size < maxLength - 5) {
            String word = Words.getRandom();
            if (word.length() + size + 1 < maxLength) {
                result += ' ' + word;
                size += word.length() + 1;
            }
        }
        return result.substring(1);
    }

    static String generateCode3() {
        String result = BigDecimalLines.getLine();
        while (result.length() > 80) {
            result = BigDecimalLines.getLine();
        }
        return result;
    }
}
