package com.example.a11398.jisuanqi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = "test:";
    private TextView ed_input;
    private TextView ed_output;
    private Button bt_clr;
    private Button bt_del;
    private Button bt_l;
    private Button bt_r;
    private Button bt_div;
    private Button bt_mul;
    private Button bt_add;
    private Button bt_sub;
    private Button bt_equal;
    private Button bt_0;
    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button bt_4;
    private Button bt_5;
    private Button bt_6;
    private Button bt_7;
    private Button bt_8;
    private Button bt_9;
    private String expression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        bindView();
    }

    private void bindView() {

        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        bt_7.setOnClickListener(this);
        bt_8.setOnClickListener(this);
        bt_9.setOnClickListener(this);
        bt_l.setOnClickListener(this);
        bt_r.setOnClickListener(this);
        bt_del.setOnClickListener(this);
        bt_equal.setOnClickListener(this);
        bt_clr.setOnClickListener(this);
        bt_add.setOnClickListener(this);
        bt_sub.setOnClickListener(this);
        bt_mul.setOnClickListener(this);
        bt_div.setOnClickListener(this);

    }

    private void initData() {
        expression = "";
        bt_1 = findViewById(R.id.bt_1);
        bt_2 = findViewById(R.id.bt_2);
        bt_3 = findViewById(R.id.bt_3);
        bt_4 = findViewById(R.id.bt_4);
        bt_5 = findViewById(R.id.bt_5);
        bt_6 = findViewById(R.id.bt_6);
        bt_7 = findViewById(R.id.bt_7);
        bt_8 = findViewById(R.id.bt_8);
        bt_9 = findViewById(R.id.bt_9);
        ed_input = findViewById(R.id.et_input);
        ed_output = findViewById(R.id.et_output);
        bt_l = findViewById(R.id.bt_l);
        bt_r = findViewById(R.id.bt_r);
        bt_clr = findViewById(R.id.bt_clr);
        bt_add = findViewById(R.id.bt_add);
        bt_sub = findViewById(R.id.bt_sub);
        bt_mul = findViewById(R.id.bt_mul);
        bt_div = findViewById(R.id.bt_div);
        bt_equal = findViewById(R.id.bt_equal);
        bt_del = findViewById(R.id.bt_del);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_equal){
            double result = calculate(expression);
            ed_output.setText(String.valueOf(result));
        }else if (v.getId() == R.id.bt_clr){
            expression = "";
            ed_input.setText(expression);
            ed_output.setText(expression);
        }else if(v.getId() == R.id.bt_del){
            int length = expression.length();
            if(length >1){
                expression = expression.substring(0, length-1);
            }else{
                expression = "";
            }
            ed_input.setText(expression);
        }else{
            expression = expression + ((Button)v).getText();
            ed_input.setText(expression);
        }


    }

    private Double calculate(String expression) {
        //标志结束
        expression = expression + '#';
        Log.i(TAG, expression);
        //定义符号栈和数字栈
        Stack<Character> op = new Stack<>();
        Stack<Double> num = new Stack<>();

        //计算符号开始标志
        op.push('#');

        //处理表达式
        for (int i = 0; i < expression.length(); ++i){

            //存储一个字符串里面的一个数字
            String num_string;
            Double num_double;

            //遇到数就压入数字栈里面
            if (expression.charAt(i) >='0'&&expression.charAt(i) <='9'
                    ||expression.charAt(i)=='.'){

                int j = i+1;
                while(expression.charAt(j) >='0'&&expression.charAt(j) <='9'
                        ||expression.charAt(j)=='.'){
                    j++;
                }
                num_string = expression.substring(i, j);

                num_double = Double.valueOf(num_string);

                num.push(num_double);

                i = j - 1;
            }else{

                //处理操作符
                int current_priority, stack_priority;
                current_priority = op2priority(expression.charAt(i));
                stack_priority = op2priority(op.peek());

                // ')'遇到'('则将二者全都出栈
                if (current_priority == -1 && stack_priority == -1){
                    return num.peek();
                } else if (current_priority == 0 && stack_priority == 3){
                    op.pop();
                }else {
                    if (current_priority > stack_priority || expression.charAt(i)=='('||op.peek()=='('){
                        op.push(expression.charAt(i));
                    }else{
                        if (num.size() >=2){
                            double two  = num.peek();
                            num.pop();
                            double one = num.peek();
                            num.pop();
                            double three = calculate_temp(op.peek(),one, two);
                            op.pop();
                            num.push(three);
                            i--;
                        }else {
                            return -1.11111;
                        }
                    }
                }

            }
        }
        return  -1.11111;
    }

    private int op2priority(char c){
        int priority;
        switch (c){
            case '+':
                priority = 1;
                break;
            case '-':
                priority = 1;
                break;
            case '×':
                priority = 2;
                break;
            case '÷':
                priority = 2;
                break;
            case '(':
                priority = 3;
                break;
            case ')':
                priority = 0;
                break;
            default:
                priority = -1;
                break;
        }
        return priority;
    }

    private double calculate_temp(char c, double one, double two){
        double result;
        switch (c){
            case '+':
                result = one + two;
                break;
            case '-':
                result = one - two;
                break;
            case '×':
                result = one * two;
                break;
            case '÷':
                result = (one*1.0) / two;
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }
}
