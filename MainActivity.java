package com.example.marknolledo.orderingtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.text.DateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String currentDate = DateFormat.getDateInstance().format(new Date());
        TextView dateToday = (TextView)findViewById(R.id.dateToday);
        dateToday.setText(currentDate);


    }

    public void viewItems(View view) {
        Intent intent = new Intent(this, itemView.class);
        startActivity(intent);
    }
}
