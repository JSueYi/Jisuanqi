package com.example.a11398.jisuanqi;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;
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
    private Button bt_point;
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

        bt_point.setOnClickListener(this);
        bt_0.setOnClickListener(this);
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
        bt_point = findViewById(R.id.bt_point);
        bt_0 = findViewById(R.id.bt_0);
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
        bt_l = findViewById(R.id.bt_left);
        bt_r = findViewById(R.id.bt_right);
        bt_clr = findViewById(R.id.bt_clr);
        bt_add = findViewById(R.id.bt_add);
        bt_sub = findViewById(R.id.bt_sub);
        bt_mul = findViewById(R.id.bt_mul);
        bt_div = findViewById(R.id.bt_div);
        bt_equal = findViewById(R.id.bt_equal);
        bt_del = findViewById(R.id.bt_del);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_equal){
            if (!Objects.equals(expression, "")){
                try {
                    double result = calculate(expression, getNum(expression));
                    if(result == -2018.0117 ){
                        ed_output.setText("Error");
                    }else {
                        ed_output.setText(String.valueOf(result));
                    }
                }catch (Exception e){
                    ed_output.setText(e.getMessage());
                }
            }
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

    private Double calculate(String expression , String[] get_num){
        //标志结束
        expression = expression + '#';
        int index_num = 0;
        Log.i(TAG, expression);

        //定义操作符栈和操作数栈
        Stack<Character> Operator = new Stack<>();
        Stack<Double> Operands = new Stack<>();

        //计算符号开始标志
        Operator.push('#');

        //处理表达式
        for (int i = 0; i < expression.length(); ++i){

            //存储一个字符串里面的一个数字
            Double number;

            //遇到数就压入数字栈里面
            if (expression.charAt(i) >='0'&&expression.charAt(i) <='9'
                    ||expression.charAt(i)=='.'){
                if(index_num < get_num.length){
                    number = Double.valueOf(get_num[index_num]);
                    Operands.push(number);
                }
                i = i + get_num[index_num].length() - 1;
                index_num++;
            }else{//处理操作符

                //当前操作符的优先级
                int current_priority;

                //操作符栈的栈顶操作符的优先级
                int peek_priority;

                current_priority = getPriority(expression.charAt(i));
                peek_priority = getPriority(Operator.peek());

                // '#'遇到'#'则结束
                if (current_priority == -1 && peek_priority == -1){
                    return Operands.peek();
                } else if (current_priority == 0 && peek_priority == 3){  // ')'遇到'('则将二者全都出栈
                    Operator.pop();
                }else {
                    if (current_priority > peek_priority || expression.charAt(i)=='('||Operator.peek()=='('){
                        Operator.push(expression.charAt(i));
                    }else{
                        //防止错误表达式，引起操作数栈错误
                        if (Operands.size() >=2){
                            double two  = Operands.peek();
                            Operands.pop();
                            double one = Operands.peek();
                            Operands.pop();
                            double three = handle(Operator.peek(),one, two);
                            Operator.pop();
                            Operands.push(three);
                            i--;
                        }else {
                            return -2018.0117;
                        }
                    }
                }

            }
        }
        return  -2018.0117;
    }

    private int getPriority(char c){
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

    private double handle(char op, double x, double y){
        double z;
        switch (op){
            case '+':
                z = x + y;
                break;
            case '-':
                z = x - y;
                break;
            case '×':
                z = x * y;
                break;
            case '÷':
                z = (x*1.0) / y;
                break;
            default:
                z = -2018.0117;
                break;
        }
        return z;
    }

    private String[] getNum(String expression){
        //根据+，-，×，÷，（， ）提取数据
        String[] tem_s = expression.split("[+\\-×÷()]");
        int length=0;

        //统计操作数个数
        for (String s:tem_s) {
            if(!s.equals("")){
                length++;
            }
        }

        //创建操作数数组
        String [] num = new String[length];
        int i =0;
        for (String s:tem_s) {
            if(!s.equals("")){
                num[i] = s;
                i++;
            }
        }
        return num;
    }

}
