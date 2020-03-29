package com.skorczynski.aplikacja.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.faendir.rhino_android.RhinoAndroidHelper;
import com.skorczynski.aplikacja.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int duration = Toast.LENGTH_LONG;
    private Button btnExit;
    private TextView historyStateText;

    private String inputText = "";
    private String outputText = "";

    private ArrayList<Button> buttons;
    private ArrayList<Character> operations;

    private TextView tvOutput;
    private TextView tvInput;

    private final String PREFNAME = "PREFNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAllElements();

        operations = new ArrayList<Character>();
        operations.add('+');
        operations.add('-');
        operations.add('/');
        operations.add('*');
    }

    private void setAllElements() {
        buttons = new ArrayList<>();
        buttons.add((Button) findViewById(R.id.btn0));
        buttons.add((Button) findViewById(R.id.btn1));
        buttons.add((Button) findViewById(R.id.btn2));
        buttons.add((Button) findViewById(R.id.btn3));
        buttons.add((Button) findViewById(R.id.btn4));
        buttons.add((Button) findViewById(R.id.btn5));
        buttons.add((Button) findViewById(R.id.btn6));
        buttons.add((Button) findViewById(R.id.btn7));
        buttons.add((Button) findViewById(R.id.btn8));
        buttons.add((Button) findViewById(R.id.btn9));

        buttons.add((Button) findViewById(R.id.btnPlus));
        buttons.add((Button) findViewById(R.id.btnMinus));
        buttons.add((Button) findViewById(R.id.btnDivision));
        buttons.add((Button) findViewById(R.id.btnMultiply));
        buttons.add((Button) findViewById(R.id.btnEqual));
        buttons.add((Button) findViewById(R.id.btnClear));

        tvOutput = (TextView) findViewById(R.id.tvOutput);
        tvInput = (TextView) findViewById(R.id.tvInput);

        for(Button button : buttons) {
            button.setOnClickListener(this);
        }
    }

    @Override
    protected void onPause() {
        SaveMemory();
        super.onPause();
    }

    @Override
    protected void onResume() {
        LoadMemory();
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn0:
                setNewNumber(0);
                break;
            case R.id.btn1:
                setNewNumber(1);
                break;
            case R.id.btn2:
                setNewNumber(2);
                break;
            case R.id.btn3:
                setNewNumber(3);
                break;
            case R.id.btn4:
                setNewNumber(4);
                break;
            case R.id.btn5:
                setNewNumber(5);
                break;
            case R.id.btn6:
                setNewNumber(6);
                break;
            case R.id.btn7:
                setNewNumber(7);
                break;
            case R.id.btn8:
                setNewNumber(8);
                break;
            case R.id.btn9:
                setNewNumber(9);
                break;
            case R.id.btnPlus:
                setNewOperation("+");
                break;
            case R.id.btnDivision:
                setNewOperation("/");
                break;
            case R.id.btnMultiply:
                setNewOperation("*");
                break;
            case R.id.btnMinus:
                setNewOperation("-");
                break;
            case R.id.btnEqual:
                calculateScore();
                break;
            case R.id.btnClear:
                clearAll();
                break;
        }
    }

    private void setNewNumber(int number) {
        if(number != 0) {
            inputText += number;
        }
        else if(inputText != "" && !isOperation(inputText)) {
            inputText += number;
        }

        tvInput.setText(inputText);
    }

    private void setNewOperation(String operation) {
        if(inputText != "") {
            inputText += operation;
        }
        else if(inputText == "" && outputText != "") {
            inputText = outputText + operation;
        }

        tvInput.setText(inputText);
    }

    private void calculateScore() {
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);

        String result = "0";
        try {
            Scriptable scriptable = rhino.initStandardObjects();
            result = rhino.evaluateString(scriptable,inputText,"javascript",1,null).toString();
        }catch (Exception e){
            outputText="-1";
        }

        int res = (int)Float.parseFloat(result);
        outputText = String.valueOf(res);
        inputText = "";

        tvOutput.setText(outputText);
    }

    private void clearAll() {
        inputText = "";
        outputText = "";
        tvInput.setText(inputText);
        tvOutput.setText(outputText);
    }

    private Boolean isOperation(String text) {
        char lastChar = text.charAt(text.length() - 1);
        for(char operation : operations) {
            if(lastChar == operation)
                return  true;
        }
        return false;
    }

    public boolean SaveMemory()
    {
        SharedPreferences sp =
                getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putString("inputText", inputText);
        mEdit1.putString("outputText", outputText);

        return mEdit1.commit();
    }

    public void LoadMemory()
    {
        SharedPreferences sp =
                getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);

        inputText = sp.getString("inputText", "0");
        outputText = sp.getString("outputText", "0");
    }
}
