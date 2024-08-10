package com.example.calculator.Logic;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.calculator.R;


public class Intializations{

    private Activity activity;
    private Button btn_0;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private Button btn_Division;
    private Button btn_Multplication;
    private Button btn_Subtraction;
    private Button btn_Addition;
    private Button btn_Dot;
    private Button btn_Reminder;
    private Button btn_Sign;
    public Button btn_Equal;
    private Button btn_Ac;
    private Button btn_C;
    public static boolean greenFlag;
    public static boolean operatorFlag ;
    public static boolean equalFlag;
    public static boolean dotflag;
    public static boolean divisionPressedFlag;
    public static boolean zeroHandleFlag;
    public static boolean isDotPressed;
    private TextView tv_MainView;

    public Intializations(Activity activity) {
        this.activity = activity;
    }


    public void ViewsIntialization() {
        tv_MainView = activity.findViewById(R.id.tv_Main);
        btn_0 = activity.findViewById(R.id.btn_0);
        btn_1 = activity.findViewById(R.id.btn_1);
        btn_2 = activity.findViewById(R.id.btn_2);
        btn_3 = activity.findViewById(R.id.btn_3);
        btn_4 = activity.findViewById(R.id.btn_4);
        btn_5 = activity.findViewById(R.id.btn_5);
        btn_6 = activity.findViewById(R.id.btn_6);
        btn_7 = activity.findViewById(R.id.btn_7);
        btn_8 = activity.findViewById(R.id.btn_8);
        btn_9 = activity.findViewById(R.id.btn_9);
        btn_Division = activity.findViewById(R.id.btn_Division);
        btn_Multplication = activity.findViewById(R.id.btn_star);
        btn_Subtraction = activity.findViewById(R.id.btn_minus);
        btn_Addition = activity.findViewById(R.id.btn_plus);
        btn_Dot = activity.findViewById(R.id.btn_dot);
        btn_Reminder = activity.findViewById(R.id.btn_Reminder);
        btn_Equal = activity.findViewById(R.id.btn_equals);
        btn_Ac = activity.findViewById(R.id.btn_AC);
        btn_C = activity.findViewById(R.id.btn_Delete);
        btn_Sign = activity.findViewById(R.id.btn_sign);
    }

    public void ActionListnerIntializtion(Calculator calculator){
        View.OnClickListener numberlistner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if(greenFlag & Calculator.resultFlag){
                    tv_MainView.append(button.getText());
                    operatorFlag = true;
                    equalFlag = true;
                    dotflag = true;
                }else {
                    tv_MainView.setText(button.getText());
                    greenFlag = true;
                    Calculator.resultFlag = true;
                    operatorFlag = true;
                    dotflag = true;
                }
            }
        };
        View.OnClickListener binaryOperationListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if(greenFlag & operatorFlag){
                    tv_MainView.append(" " + button.getText() + " ");
                    Calculator.resultFlag = true;
                    operatorFlag = false;
                    equalFlag = false;     // if binary operator is a final text. false flag for equals button
                    dotflag = true;
                }else {
                    tv_MainView.setText(tv_MainView.getText().toString());
                }
            }
        };

        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if(greenFlag){
                    if(divisionPressedFlag)
                        zeroHandleFlag = true;
                    tv_MainView.append(button.getText());
                    equalFlag = true;
                }else {
                    tv_MainView.setText(tv_MainView.getText().toString());
                }
            }
        });
        btn_1.setOnClickListener(numberlistner);
        btn_2.setOnClickListener(numberlistner);
        btn_3.setOnClickListener(numberlistner);
        btn_4.setOnClickListener(numberlistner);
        btn_5.setOnClickListener(numberlistner);
        btn_6.setOnClickListener(numberlistner);
        btn_7.setOnClickListener(numberlistner);
        btn_8.setOnClickListener(numberlistner);
        btn_9.setOnClickListener(numberlistner);
        btn_Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = tv_MainView.getText().toString().trim();
                if (!currentText.isEmpty()) {
                    if (currentText.startsWith("-")) {
                        tv_MainView.setText(currentText.substring(1));
                    } else {
                        tv_MainView.setText("-" + currentText);
                    }
                }
            }
        });
    btn_Division.setOnClickListener(binaryOperationListner);
        btn_Multplication.setOnClickListener(binaryOperationListner);
        btn_Subtraction.setOnClickListener(binaryOperationListner);
        btn_Addition.setOnClickListener(binaryOperationListner);
        btn_Reminder.setOnClickListener(binaryOperationListner);
        btn_Dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                divisionPressedFlag = true;

                if(greenFlag & dotflag & !isDotPressed){
                    tv_MainView.append(button.getText());
                    dotflag = false;
                    isDotPressed = true;
                    Calculator.resultFlag = true;
                }else {
                    tv_MainView.setText(tv_MainView.getText().toString());
                }
            }
        });
        btn_Ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_MainView.setText(" ");
                greenFlag = false;
            }
        });
        btn_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tv_MainView.getText().toString();
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                    tv_MainView.setText(text);
                }
            }
        });
        btn_Equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = tv_MainView.getText().toString();
                if (currentText.isEmpty()) {
                    tv_MainView.setText("");
                    return;
                }else if(equalFlag) {  // true flag (if final text is num not mathmatic sign )
                    calculator.splitExpression();
                    calculator.printResult();
                }
            }
        });
    }
    public TextView getTv_MainView() {
        return tv_MainView;
    }

    public void setTv_MainView(String value) {
        this.tv_MainView = tv_MainView;
    }

}