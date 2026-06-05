package com.example.chapter8project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView delivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        delivery = (TextView) findViewById(R.id.txtReservation);
        Button button = (Button) findViewById(R.id.btnTime);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(MainActivity.this, t, c.get(Calendar.HOUR), c.get(Calendar.PM), false).show();
            }
        });
    }
    Calendar c = Calendar.getInstance();
    DateFormat fmtTime = DateFormat.getTimeInstance();
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int pm) {
            if (hour >= 5 && hour <= 11) {
                c.set(Calendar.HOUR, hour);
                c.set(Calendar.PM, pm);
                delivery.setText("Your delivery order will arrive at " + fmtTime.format(c.getTime()));
            }
            else
            {
                Toast.makeText(MainActivity.this, "Scheduled hours can only be between 5 pm and 11 pm.", Toast.LENGTH_LONG).show();
            }
        }
    };
}