package com.example.personalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int fNumber, sNumber;
    String groupChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText firstNumber = (EditText)findViewById(R.id.FirstNumberText);
        final EditText secondNumber = (EditText)findViewById(R.id.secondNumberText);
        final Spinner algebra = (Spinner)findViewById(R.id.spinner);
        Button calculate = (Button)findViewById(R.id.button);
        calculate.setOnClickListener(new View.OnClickListener() {
            final TextView result = ((TextView)findViewById(R.id.resultText));
            @Override
            public void onClick(View v) {
                fNumber = Integer.parseInt(firstNumber.getText().toString());
                sNumber = Integer.parseInt(secondNumber.getText().toString());
                groupChoice = algebra.getSelectedItem().toString();
                switch(groupChoice){
                    case "+": result.setText(Integer.toString(fNumber + sNumber));
                            break;
                    case "-": result.setText(Integer.toString(fNumber - sNumber));
                            break;
                    case "*": result.setText(Integer.toString(fNumber * sNumber));
                            break;
                    case "/": result.setText(Integer.toString(fNumber / sNumber));
                            break;
                }
            }
        });
    }
}