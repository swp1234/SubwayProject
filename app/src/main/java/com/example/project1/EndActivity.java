package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndActivity extends AppCompatActivity {
    String situation;
    MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        myApp=(MyApplication) getApplication();
        Intent passedIntent = getIntent();


        Button confirmButton = (Button)findViewById(R.id.confirmButton);
        Button researchStationButton = (Button)findViewById(R.id.researchStationButton);
        Button researchSeatButton = (Button)findViewById(R.id.researchSeatButton);
        Button backButton = (Button)findViewById(R.id.backButton1);
        Button xButton = (Button)findViewById(R.id.xButton1);

        TextView confirmText = (TextView)findViewById(R.id.confirmText);


        switch(myApp.getSelectedCarLocation()){
            case 0:
                situation = "좌측";
                break;
            case 1:
                situation = "중간";
                break;
            case 2:
                situation = "우측";
                break;
        }

        confirmText.setText("7호선 "+myApp.getTrainNumber()+"열차 "+myApp.getCarNumber()+"칸 "+situation+" "+myApp.getSelectedSeat()+"번");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent1, 310);
            }
        });
        researchStationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        researchSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), OverviewActivity.class);
                startActivityForResult(intent1, 310);
            }
        });

    }
}
