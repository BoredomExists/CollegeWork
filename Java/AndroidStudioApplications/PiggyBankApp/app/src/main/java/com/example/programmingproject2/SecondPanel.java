package com.example.programmingproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class SecondPanel extends AppCompatActivity {
    double costQuarters, costDimes, costNickels, costPennies;
    int numOfQuarters, numOfDimes, numOfNickels, numOfPennies;
    String groupChoice;
    double totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_panel);
        final EditText quarters = (EditText)findViewById(R.id.inquarters);
        final EditText dimes = (EditText)findViewById(R.id.inDimes);
        final EditText nickels = (EditText)findViewById(R.id.inNickels);
        final EditText pennies = (EditText)findViewById(R.id.inPennies);
        final Spinner group = (Spinner)findViewById(R.id.spinnerctrl);
        final TextView result = (TextView)findViewById(R.id.result);

        Button button = (Button) findViewById(R.id.secondpanelbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numOfQuarters = Integer.parseInt(quarters.getText().toString());
                numOfDimes = Integer.parseInt(dimes.getText().toString());
                numOfNickels = Integer.parseInt(nickels.getText().toString());
                numOfPennies = Integer.parseInt(pennies.getText().toString());
                costQuarters = numOfQuarters * .25;
                costDimes = numOfDimes * .10;
                costNickels = numOfNickels * .05;
                costPennies = numOfPennies * .01;
                totalAmount = costQuarters + costDimes + costNickels + costPennies;
                DecimalFormat curreny = new DecimalFormat("$###.##");
                groupChoice = group.getSelectedItem().toString();
                result.setText("You " + groupChoice + " " +  curreny.format(totalAmount));


            }
        });
    }
}