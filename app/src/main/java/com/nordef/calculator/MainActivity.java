package com.nordef.calculator;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    String operation;
    Button div, mult, soust, addi;

    int combien = 0;
    float a;
    boolean signeClicked = false, checkegal = false, btnclicked = false, virguleClicked = false;

    Button buttons[] = new Button[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        div = findViewById(R.id.button16);
        mult = findViewById(R.id.button12);
        soust = findViewById(R.id.button8);
        addi = findViewById(R.id.button4);

        textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String flt = textView.getText().toString();
                    flt = flt.substring(0, flt.length() - 1);
                    textView.setText(flt);

                } catch (Exception e) {
                }
            }
        });

        updateUI();
    }

    private void updateUI() {

        //get screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        //calculate sizes that will be applied
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dpToPX = Math.round(5 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        int square = (width / 4) - (dpToPX * 2);
        int btn0Width = square * 2 + Math.round(10 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        //initialise all buttons
        for (int i = 1; i <= 19; i++) {
            String btnId = "button" + i;
            int resID = getResources().getIdentifier(btnId, "id", getPackageName());
            buttons[i] = findViewById(resID);

            //apply changes to buttons

            //check if its 0 button because he was large
            if (i == 3) {
                LinearLayout.LayoutParams params0Button = new LinearLayout.LayoutParams(btn0Width, square);
                params0Button.setMargins(dpToPX, dpToPX, dpToPX, dpToPX);

                buttons[i].setLayoutParams(params0Button);
                continue;
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(square, square);
            //add margin programmatically
            params.setMargins(dpToPX, dpToPX, dpToPX, dpToPX);

            buttons[i].setLayoutParams(params);
        }

    }

    public void AC(View view) {
        textView.setText("");
        combien = 0;
        a = 0;
        signeClicked = false;
        checkegal = false;
        virguleClicked = false;
        restaureColor();
    }

    public void PM(View view) {
        try {

            btnclicked = true;
            float num = Float.parseFloat(textView.getText().toString());
            if (num == (int) num)
                textView.setText("" + (-(int) num));
            else
                textView.setText("" + (-num));


        } catch (Exception e) {
            btnclicked = false;
        }
    }

    public void pourcentage(View view) {
        try {
            btnclicked = true;
            float num = Float.parseFloat(textView.getText().toString()) / 100;
            if (num == (int) num)
                textView.setText((int) num + "");
            else
                textView.setText(num + "");


        } catch (Exception e) {
            btnclicked = false;
        }
    }

    public void division(View view) {
        try {
            operation("/");
            restaureColor();
            div.setBackgroundResource(R.drawable.onopclicked);
            div.setTextColor(getResources().getColor(R.color.orange));
        } catch (Exception e) {
        }
    }

    public void multiplication(View view) {
        try {
            operation("*");
            restaureColor();
            mult.setBackgroundResource(R.drawable.onopclicked);
            mult.setTextColor(getResources().getColor(R.color.orange));
        } catch (Exception e) {
        }
    }

    public void soustarction(View view) {
        try {
            operation("-");
            restaureColor();
            soust.setBackgroundResource(R.drawable.onopclicked);
            soust.setTextColor(getResources().getColor(R.color.orange));
        } catch (Exception e) {
        }
    }

    public void addition(View view) {
        try {
            operation("+");
            restaureColor();
            addi.setBackgroundResource(R.drawable.onopclicked);
            addi.setTextColor(getResources().getColor(R.color.orange));
        } catch (Exception e) {
        }
    }

    public void virgule(View view) {
        if (!TextUtils.isEmpty(textView.getText().toString())) {
            if (!virguleClicked) {
                textView.setText(textView.getText().toString() + ".");

                virguleClicked = true;
            }
        }
    }

    public void egal(View view) {
        try {
            checkegal = true;
            typeop();
            restaureColor();
        } catch (Exception e) {
            checkegal = false;
        }
    }

    public void btn_0(View view) {
        num(0);
    }

    public void btn_1(View view) {
        num(1);
    }

    public void btn_2(View view) {
        num(2);
    }

    public void btn_3(View view) {
        num(3);
    }

    public void btn_4(View view) {
        num(4);
    }

    public void btn_5(View view) {
        num(5);
    }

    public void btn_6(View view) {
        num(6);
    }

    public void btn_7(View view) {
        num(7);
    }

    public void btn_8(View view) {
        num(8);
    }

    public void btn_9(View view) {
        num(9);
    }

    void num(int num) {
        if (signeClicked || btnclicked) {
            textView.setText("");
            signeClicked = false;
            btnclicked = false;
        }

        textView.setText(textView.getText().toString() + num);

    }

    void operation(String op) {
        combien += 1;
        signeClicked = true;
        virguleClicked = false;

        if (combien == 1) {
            a = Float.parseFloat(textView.getText().toString());
            operation = op;

            textView.setText("");
        }
        if (combien >= 2) {


            switch (op) {
                case "+":
                    a = typeop();
                    operation = "+";
                    break;

                case "-":
                    a = typeop();
                    operation = "-";
                    break;

                case "/":
                    a = typeop();
                    operation = "/";
                    break;

                case "*":
                    a = typeop();
                    operation = "*";
                    break;
            }

            if (a == (int) a)
                textView.setText("" + (int) a);
            else
                textView.setText("" + a);

        }
    }

    float typeop() {

        switch (operation) {
            case "+":
                a = a + Float.parseFloat(textView.getText().toString());
                break;

            case "-":
                a = a - Float.parseFloat(textView.getText().toString());
                break;

            case "/":
                a = a / Float.parseFloat(textView.getText().toString());
                break;

            case "*":
                a = a * Float.parseFloat(textView.getText().toString());
                break;
        }
        if (checkegal) {
            if (a == (int) a)
                textView.setText("" + (int) a);
            else
                textView.setText("" + a);


            checkegal = false;
            combien = 0;
            a = 0;
            signeClicked = false;
        }
        return a;
    }

    void restaureColor() {
        div.setBackgroundResource(R.drawable.orrange);
        div.setTextColor(Color.WHITE);

        mult.setBackgroundResource(R.drawable.orrange);
        mult.setTextColor(Color.WHITE);

        addi.setBackgroundResource(R.drawable.orrange);
        addi.setTextColor(Color.WHITE);

        soust.setBackgroundResource(R.drawable.orrange);
        soust.setTextColor(Color.WHITE);
    }

}
