package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new GUI();
/*
        File file = new File("./src/com/company/generator/BigDecimalLines");
        File from = new File("./src/BigDecimal.txt");
        Scanner s = null;
        try {
            s = new Scanner(from);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String result = "";
        while (s.hasNextLine()) {
            String line = s.nextLine().replace("\"", "\\\"");
            line.replaceAll("/\\s\\s+/g", " ");
            if (line.length() > 10) {
                while (line.length() > 0 && (line.charAt(0) == ' ' || line.charAt(0) == '\t')) line = line.substring(1);
                result += "\"" + line + "\",\n";
            }
        }
        Utils.writeContents(file, result);


 */


    }

}
