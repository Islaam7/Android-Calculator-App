package com.example.calculator.Logic;


import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator<T extends Number>{

    private String expression;
    private StringTokenizer tokenizer;
    private String token;
    private List<String> tokens;
    private static final String TAG = "CalculatorActivity";
    private Activity activity;
    private TextView tv_MainView;
    private TextView tv_Second;
    public static boolean resultFlag;


    public Calculator(Activity activity, TextView tv_MainView) {
        this.activity = activity;
        this.tv_MainView = tv_MainView;
    }


    public void splitExpression (){
        expression = tv_MainView.getText().toString();

        tokenizer = new StringTokenizer(expression, "+-÷x%", true);
        tokens = new ArrayList<>();

        while (tokenizer.hasMoreTokens()){
            token = tokenizer.nextToken();
            if (token.equals("-") && (tokens.isEmpty() || isOperator(tokens.get(tokens.size() - 1)))) {
                tokens.add(token + tokenizer.nextToken());
            } else {
                tokens.add(token);
            }
        }
        for (String item: tokens) {
            Log.d(TAG, "Token: " + item);
        }
    }


    public void printResult(){
        resultFlag = false;
        List<String> postfix = infixToPostfix(tokens);
        try {
            double result = evaluatePostfix(postfix);
            if (result % 1 == 0) {
                tv_MainView.setText(String.valueOf((long) result));
            } else {
                tv_MainView.setText(String.valueOf(result));
            }
        } catch (Exception e) {
            tv_MainView.setText(tv_MainView.getText().toString());
        }
    }

    private List<String> infixToPostfix(List<String> infixTokens) {
        List<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for (String token : infixTokens) {
            if (isNumeric(token)) {
                postfix.add(token);
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token)) {
                    postfix.add(stack.pop());
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }
        return postfix;
    }

    public double evaluatePostfix(List<String> postfixTokens) {
        Stack<Double> stack = new Stack<>();
        for (String token : postfixTokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                Double operand2 = stack.pop();
                Double operand1 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        stack.push(operand1 - operand2);
                        break;
                    case "x":
                        stack.push(operand1 * operand2);
                        break;
                    case "%":
                        if (operand2 == 0) {
                            throw new ArithmeticException();
                        }
                        stack.push(operand1 % operand2);
                        break;
                    case "÷":
                        if (operand2 == 0) {
                        throw new ArithmeticException();
                        }
                        stack.push(operand1 / operand2);
                        break;
                }
            }
        }
        return stack.pop();
    }

    private boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("x") || token.equals("÷") || token.equals("%");
    }

    private int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "x":
            case "÷":
            case "%":
                return 2;
            default:
                return -1;
        }
    }
}
