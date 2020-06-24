package com.example.project1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String searchStation;
    TextView searchfor;
    MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myApp = (MyApplication)getApplication();

        searchfor =(TextView) findViewById(R.id.search);
        Button button = (Button)findViewById(R.id.search_button);
        Button logoutButton = (Button)findViewById(R.id.logoutbutton);


        //검색 버튼 클릭시 로그인으로 간다. 만약 로그인됬을경우(isLoggedIn=true), 적은 역이름을 가지고 검색화면으로 간다.
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                searchStation=searchfor.getText().toString();

                if(!myApp.isLoggedIn()) {
                    Intent intent1 = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivityForResult(intent1, 101);
                } else {

                    Intent intent2 = new Intent(getApplicationContext(),SearchStationActivity.class);

                    intent2.putExtra("searchWord",searchStation);
                    startActivityForResult(intent2, 102);
                }
            }
        });
        //로그아웃 버튼
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApp.setLoggedIn(false);
                myApp.setLoggedId("");
                myApp.setEndStationNumber(0);
                myApp.setSelectedCarLocation(0);
                myApp.setSelectedSeat(0);
                myApp.setCarNumber(0);
                myApp.setPayed(false);
                myApp.setTrainNumber(0);
                myApp.setStationNumber(0);
                myApp.setRoute(0);

                Toast.makeText(getApplicationContext(),"로그아웃 성공하셨습니다.",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){//login성공적으로 되서 다시 돌아왔을경우 isloggedin=true로 바꾸어 진행이 가능하게 한다.
            if(myApp.isLoggedIn()){
                Toast.makeText(getApplicationContext(),"로그인 성공하셨습니다. ID: "+myApp.getLoggedId(),Toast.LENGTH_LONG).show();



            }
        }

    }
}
