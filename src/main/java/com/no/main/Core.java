package com.no.main;

import com.no.antlr.CLexer;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.Token;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Core {

    private List<Integer> operatorsVocabulary;
    List<Token> operators;
    List<Token> operands;
    List<String> uniqueOperators;
    List<String> uniqueOperands;
    List<Token> operandsWithoutDuplicates;

    public Core(File file) throws IOException {
        OperatorsVocabulary vocabulary = new OperatorsVocabulary();
        operatorsVocabulary = vocabulary.getVocabulary();
        operators = new ArrayList<Token>();
        operands = new ArrayList<Token>();
        uniqueOperands = new ArrayList<String>();
        uniqueOperators = new ArrayList<String>();
        run(file);
    }

    public void run(File file) throws IOException {
        ANTLRFileStream input = new ANTLRFileStream(file.getAbsolutePath());
        CLexer lexer = new CLexer(input);
        List<? extends Token> tokens = lexer.getAllTokens();
        for (int i = 0; i < tokens.size(); i++) {
            if (operatorsVocabulary.contains(tokens.get(i).getType())) {
                operators.add(tokens.get(i));
                if (tokens.get(i - 1).getType() == 105 || tokens.get(i - 1).getType() == 106)
                    operands.add(tokens.get(i - 1));
                if (tokens.get(i + 1).getType() == 105 || tokens.get(i + 1).getType() == 106)
                    operands.add(tokens.get(i + 1));
            }
        }
        for (int i = 0; i < operators.size(); i++) {
            if (!uniqueOperators.contains(operators.get(i).getText())) uniqueOperators.add(operators.get(i).getText());
        }
        operandsWithoutDuplicates = new ArrayList<Token>(
                new HashSet<Token>(operands));
        for (int i = 0; i < operandsWithoutDuplicates.size(); i++) {
            if (!uniqueOperands.contains(operandsWithoutDuplicates.get(i).getText()))
                uniqueOperands.add(operandsWithoutDuplicates.get(i).getText());
        }
    }

    public List<Double> halstead() {
        List<Double> halstead = new ArrayList<Double>();
        double n1 = uniqueOperators.size();
        double n2 = uniqueOperands.size();
        double L1 = operators.size();
        double L2 = operandsWithoutDuplicates.size();
        double I = n1 + n2;
        double L = L1 + L2;
        double V = L * (Math.log(n1 + n2)/Math.log(2));
        double T = (n1*L2)/(2*n2);
        double E = V * T;
        double N = V/3000;
        halstead.add(L1);
        halstead.add(L2);
        halstead.add(n1);
        halstead.add(n2);
        halstead.add(I);
        halstead.add(L);
        halstead.add(V);
        halstead.add(T);
        halstead.add(E);
        halstead.add(N);
        return halstead;
    }


    public List<Token> getOperators() {
        return operators;
    }

    public List<Token> getOperandsWithoutDuplicates() {
        return operandsWithoutDuplicates;
    }
}
