package com.example.programmingproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    double euroConversion = 0.996949;
    double pesoConversion = 19.9218;
    double canadianConversion = 1.32494;
    double USAmount;
    double convertedAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText usDollar = (EditText) findViewById(R.id.USinput);
        final RadioButton UStoEuro = (RadioButton) findViewById(R.id.RBeuro);
        final RadioButton UStoPeso = (RadioButton) findViewById(R.id.RBpeso);
        final RadioButton UStoCanadian = (RadioButton) findViewById(R.id.RBcanadian);
        final TextView result = (TextView) findViewById(R.id.txtResult);
        Button button = (Button) findViewById(R.id.btnConvert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                USAmount = Double.parseDouble(usDollar.getText().toString());
                DecimalFormat tenth = new DecimalFormat("$###,###.##");
                if (UStoEuro.isChecked()) {
                    if (USAmount <= 100000) {
                        convertedAmount = USAmount * euroConversion;
                        result.setText(tenth.format(USAmount) + " US Dollars = " + tenth.format(convertedAmount) + " Euros");
                    } else {
                        Toast.makeText(MainActivity.this, " US Dollar Amount must be less than 100,00", Toast.LENGTH_LONG).show();
                    }
                }
                if (UStoPeso.isChecked()) {
                    if (USAmount <= 100000) {
                        convertedAmount = USAmount * pesoConversion;
                        result.setText(tenth.format(USAmount) + " US Dollars = " + tenth.format(convertedAmount) + " Pesos");
                    } else {
                        Toast.makeText(MainActivity.this, "US Dollar Amount must be less than 100,00", Toast.LENGTH_LONG).show();
                    }
                }
                if (UStoCanadian.isChecked()) {
                    if (USAmount <= 100000) {
                        convertedAmount = USAmount * canadianConversion;
                        result.setText(tenth.format(USAmount) + " US Dollars = " + tenth.format(convertedAmount) + " Canadian Dollars");
                    } else {
                        Toast.makeText(MainActivity.this, "US Dollar Amount must be less than 100,00", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
