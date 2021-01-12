package com.no.main;


import com.no.ui.UI;

import javax.swing.text.BadLocationException;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new UI();
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
