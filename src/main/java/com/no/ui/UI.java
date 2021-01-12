package com.no.ui;

import com.no.main.Core;
import javafx.scene.text.TextAlignment;
import org.antlr.v4.runtime.Token;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class UI extends JFrame implements ActionListener {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private JButton openButton;
    private JTextPane halstead;
    private JTextPane operators;
    private JTextPane operands;
    private JScrollPane scrHalstead;
    private JScrollPane scrOperators;
    private JScrollPane scrOperands;
    private final JFileChooser fc = new JFileChooser();
    private Core core;
    DefaultStyledDocument halDoc;
    DefaultStyledDocument operatorsDoc;
    DefaultStyledDocument operandsDoc;

    public UI() throws BadLocationException {
        super("Miary Halsteada");
        FileFilter filter = new FileNameExtensionFilter("C File","c");
        fc.setFileFilter(filter);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocation(50,50);
        JPanel pane1 = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel();
        GridLayout resultLayout = new GridLayout(1,3);

        openButton = new JButton("Otwórz plik");
        halstead = new JTextPane();
        operators = new JTextPane();
        operands = new JTextPane();
        scrHalstead = new JScrollPane(halstead);
        scrOperands = new JScrollPane(operands);
        scrOperators = new JScrollPane(operators);
        openButton.addActionListener(this);

        halstead.setEditable(false);
        operators.setEditable(false);
        operands.setEditable(false);


        halDoc = new DefaultStyledDocument();
        operandsDoc = new DefaultStyledDocument();
        operatorsDoc = new DefaultStyledDocument();

        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_RIGHT);
        halDoc.insertString(0,"",style);
        operatorsDoc.insertString(0,"",style);
        operandsDoc.insertString(0,"",style);


        halstead.setDocument(halDoc);
        operators.setDocument(operatorsDoc);
        operands.setDocument(operandsDoc);

        halstead.setBorder(BorderFactory.createTitledBorder(new CompoundBorder(BorderFactory.createEmptyBorder(5,5,5,10),BorderFactory.createLineBorder(Color.BLACK)),"Miary Halesteada"));
        operators.setBorder(BorderFactory.createTitledBorder(new CompoundBorder(BorderFactory.createEmptyBorder(5,10,5,10),BorderFactory.createLineBorder(Color.BLACK)),"Znalezione operatory"));
        operands.setBorder(BorderFactory.createTitledBorder(new CompoundBorder(BorderFactory.createEmptyBorder(5,10,5,5),BorderFactory.createLineBorder(Color.BLACK)),"Znalezione operandy"));

        pane1.add(openButton,BorderLayout.NORTH);
        panel2.setLayout(resultLayout);
        panel2.add(scrHalstead);
        panel2.add(scrOperators);
        panel2.add(scrOperands);
        pane1.add(panel2,BorderLayout.CENTER);

        add(pane1);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    core = new Core(file);
                    setResults();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
                System.out.println(file.getName());
            } else {
                System.out.println("Akcja przerwana");
            }
        }
    }

    private void setResults() throws BadLocationException {
        List<Double> result = core.halstead();
        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
        this.halDoc.replace(0,halDoc.getLength(),"",null);
        this.operandsDoc.replace(0,operandsDoc.getLength(),"",null);
        this.operatorsDoc.replace(0,operatorsDoc.getLength(),"",null);
        this.halDoc.insertString(halDoc.getLength(),"Liczba wszystkich operatorów: " + result.get(0).toString() + "\n",style);
        this.halDoc.insertString(halDoc.getLength(),"Liczba wszystkich operandów: " + result.get(1).toString() + "\n",style);
        this.halDoc.insertString(halDoc.getLength(),"Liczba unikalnych operatorów: " + result.get(2).toString() + "\n",style);
        this.halDoc.insertString(halDoc.getLength(),"Liczba unikalnych operandów: " + result.get(3).toString() + "\n",style);
        this.halDoc.insertString(halDoc.getLength(),"Słownik programu: " + result.get(4).toString() + "\n",style);
        this.halDoc.insertString(halDoc.getLength(),"Dugość programu: " + result.get(5).toString() + "\n",style);
        this.halDoc.insertString(halDoc.getLength(),"Objętość programu: " + result.get(6).toString() + "\n",style);
        this.halDoc.insertString(halDoc.getLength(),"Trudność programu: " + result.get(7).toString() + "\n",style);
        this.halDoc.insertString(halDoc.getLength(),"Wymagany nakład pracy: " + result.get(8).toString() + "\n",style);
        this.halDoc.insertString(halDoc.getLength(),"Przewidywana liczba błędów: " + result.get(9).toString() + "\n",style);

        List<Token> op = core.getOperators();

        for(Token t:op) {
            this.operatorsDoc.insertString(operatorsDoc.getLength(),t.getText()+"\n",style);
        }

        List<Token> ope = core.getOperandsWithoutDuplicates();

        for(Token t:ope) {
            this.operandsDoc.insertString(operandsDoc.getLength(),t.getText()+"\n",style);
        }
    }
}
