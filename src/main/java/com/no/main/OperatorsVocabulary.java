package com.no.main;

import java.util.ArrayList;
import java.util.List;

public class OperatorsVocabulary {
    private List<Integer> vocabulary;

    public OperatorsVocabulary() {
        this.generateOperatorsVocabulary();
    }

    private void generateOperatorsVocabulary() {
        List<Integer> operators = new ArrayList<Integer>();

        //Arithmetic, increment and decrement operators
        operators.add(71); //+
        operators.add(73); //-
        operators.add(75); //*
        operators.add(76); // /
        operators.add(77); //%
        operators.add(72); //++
        operators.add(74); //--

        //Logic operators
        operators.add(100); //==
        operators.add(101); //!=
        operators.add(67); //>
        operators.add(65); //<
        operators.add(68); //>=
        operators.add(66); //<=
        operators.add(80); //&&
        operators.add(81); //||
        operators.add(83); //!

        //Assignment operators
        operators.add(89); //=
        operators.add(93); //+=
        operators.add(94); //-=
        operators.add(90); //*=
        operators.add(91); // /=
        operators.add(92); // %=

        this. vocabulary = operators;
    }

    public List<Integer> getVocabulary() {
        return vocabulary;
    }
}
