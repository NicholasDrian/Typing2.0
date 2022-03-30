package com.company;

import com.company.generator.Generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

public class GUI extends JFrame implements KeyListener, ActionListener {

    int lineCount = 3;
    int typed = 0;
    int errors = 0;
    boolean lastError = false;
    boolean started = false;
    double startTime;
    JTextArea stats;
    JButton reset;
    String text = "";
    JTextArea textArea;
    String textTyped = "";
    JTextArea textTypedArea;
    String wrong = "";
    JTextArea wrongArea;
    JTextArea typedCounter;
    JComboBox mode;
    int charCount;
    String type = "Hard"; //0 is default, 1 is unchanged java

    GUI(){


        File charCounter = new File("./charCounter");
        if (!charCounter.exists()) {
            charCount = 0;
            try {
                charCounter.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveCharCount();
        } else {
            try {
                charCount = new Scanner(charCounter).nextInt();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        setTitle("LeetTyping!");
        setBounds(100, 100, 900, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(true);
        addKeyListener(this);
        getContentPane().setLayout(null);

        reset = new JButton("RESET");
        reset.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        reset.setLocation(400,10);
        reset.setSize(140, 40);
        reset.addActionListener(this);
        reset.setFocusable(false);
        add(reset);


        textArea = new JTextArea(text);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        textArea.setForeground(Color.white);
        textArea.setLocation(10,110);
        textArea.setSize(900, 150);
        textArea.setOpaque(false);
        textArea.setFocusable(false);
        add(textArea);

        textTypedArea = new JTextArea(textTyped);
        textTypedArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        textTypedArea.setLocation(10,110);
        textTypedArea.setSize(900, 150);
        textTypedArea.setOpaque(false);
        textTypedArea.setForeground(new Color(100, 200, 100));
        textTypedArea.setFocusable(false);
        add(textTypedArea);

        wrongArea = new JTextArea(textTyped);
        wrongArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        wrongArea.setLocation(10,110);
        wrongArea.setSize(900, 150);
        wrongArea.setOpaque(false);
        wrongArea.setForeground(new Color(200, 100, 100));
        wrongArea.setFocusable(false);
        add(wrongArea);

        stats = new JTextArea();
        stats.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        stats.setForeground(Color.white);
        stats.setLocation(20,10);
        stats.setSize(400, 50);
        stats.setOpaque(false);
        stats.setFocusable(false);
        stats.setText(Stats.getStats(startTime, errors, typed));
        add(stats);

        typedCounter = new JTextArea();
        typedCounter.setText("charsTyped: " + charCount);
        typedCounter.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        typedCounter.setForeground(Color.white);
        typedCounter.setLocation(20,40);
        typedCounter.setSize(800, 50);
        typedCounter.setOpaque(false);
        typedCounter.setFocusable(false);
        add(typedCounter);


        String[] options = new String[]{
            "Hard",
            "Easy",
            "Code"
        };
        mode = new JComboBox(options);
        mode.setSize(100, 40);
        mode.setLocation(550, 10);
        mode.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        mode.addActionListener(this);
        mode.setFocusable(false);
        add(mode);

        resetText();
        getContentPane().setBackground(Color.black);
        setBackground(Color.black);
        setVisible(true);
    }

    int maxChars = 80;

    private String generateText() {
        String result = "";
        for (int i = 0; i < lineCount; i++) {
            result += getLine();
        }
        return result;
    }

    private String getLine() {
        switch (type) {
            case "Hard":
                return Generator.generateCode1(maxChars) + '\n';
            case "Easy":
                return Generator.generateCode2(maxChars) + '\n';
            case "Code":
                return Generator.generateCode3() + '\n';
        }
        return "";
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        updateTyped(e.getKeyChar());
    }

    public void saveCharCount() {
        File charCounter = new File("./charCounter");
        writeContents(charCounter, "" + charCount);
    }

    static void writeContents(File file, Object... contents) {
        try {
            if (file.isDirectory()) {
                throw
                    new IllegalArgumentException("cannot overwrite directory");
            }
            BufferedOutputStream str =
                new BufferedOutputStream(Files.newOutputStream(file.toPath()));
            for (Object obj : contents) {
                if (obj instanceof byte[]) {
                    str.write((byte[]) obj);
                } else {
                    str.write(((String) obj).getBytes(StandardCharsets.UTF_8));
                }
            }
            str.close();
        } catch (IOException | ClassCastException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
    }

    private void updateTyped(char c) {
        if (c == '￿'){
            return;
        }
        if (!started){
            started = true;
            startTime = System.nanoTime();
        }
        if (c == text.charAt(typed)){
            charCount++;
            saveCharCount();
            typedCounter.setText("charsTyped: " + charCount);
            char ch = ' ';
            if (c == '\n') {
                ch = '\n';
                if(lastError){
                    wrong += '↓';
                } else {
                    textTyped += '↓';
                }
            }
            if (c == ' ') {
                c = '_';
            }
            if (lastError) {
                wrong = wrong + c;
                textTyped = textTyped + ch;
                lastError = false;
            } else {
                wrong = wrong + ch;
                textTyped = textTyped + c;
            }
            text = text.substring(0, typed) +
                    ch +
                    text.substring(typed + 1);

            textTypedArea.setText(textTyped);
            textArea.setText(text);
            wrongArea.setText(wrong);
            add(textTypedArea);
            typed++;
            stats.setText(Stats.getStats(startTime, errors, typed));
        } else {
            if (!lastError) {
                errors++;
                lastError = true;
            }
        }
        if (typed + 1 == text.length()){
            resetText();
        }
    }

    @Override public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset){
            resetText();
            stats.setText(Stats.getStats(startTime, errors, typed));
        }
        if (e.getSource() == mode){
            type = (String) mode.getSelectedItem();
            resetText();
            stats.setText(Stats.getStats(startTime, errors, typed));
        }
    }

    public void resetText(){
        text = generateText();
        textArea.setText(text);
        textTyped = "";
        wrong = "";
        typed = 0;
        errors = 0;
        textTypedArea.setText(textTyped);
        wrongArea.setText(wrong);
        started = false;
    }
}
