package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class LogInActivity extends AppCompatActivity {

    String currentID;
    String currentPW;
    TextView loginID;
    TextView loginPW;
    MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        myApp = (MyApplication)getApplication();

        loginID =(TextView) findViewById(R.id.idText);
        loginPW =(TextView) findViewById(R.id.passwordText);
        Button join_button = (Button)findViewById(R.id.joinin_button);
        Button login_button = (Button)findViewById(R.id.login_button);
        Button back_button1 = (Button)findViewById(R.id.backButton1);
        Button x_button1 = (Button)findViewById(R.id.xButton1);

        //회원가입 버튼 누를경우 회원가입창으로 넘어간다.
        join_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    Intent intent = new Intent(getApplicationContext(),JoinInActivity.class);
                    startActivityForResult(intent, 103);
            }
        });

        //로그인 버튼누를경우. 서버에서 확인후(아직 미구현) 로그인 된 상태로 첫화면으로 진행한다.
        login_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                currentID = loginID.getText().toString();
                currentPW = loginPW.getText().toString();
                Log.d("test",currentID);
                //서버에서 확인과정 거치고
                JSONObject jsonObj  = JsonWrapper.wrapSignInAccount(currentID,currentPW);
                ServerConnectManager scMgr = new ServerConnectManager();
                scMgr.requestQuery(jsonObj.toString());
                while(scMgr.getQueryResult().equals(""));
                boolean isLogInSuccess = (!scMgr.getQueryResult().equals("LOGIN FAIL"));
                if(isLogInSuccess) {
                    //로그인 성공시
                    //집 , 직장으로 등록된 역 가져오기;
                    JSONObject obj;
                    Vector<String> favorites = new Vector<String>();
                    try {
                        obj = new JSONObject(scMgr.getQueryResult());
                        favorites.add((String)obj.get("FAVORITE1"));
                        favorites.add((String)obj.get("FAVORITE2"));
                        //myApp.setHomeStation((String)obj.get("FAVORITE1"));
                       //myApp.setWorkStation((String)obj.get("FAVORITE2"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    myApp.setLoggedIn(true);
                    myApp.setLoggedId(currentID);
                    myApp.setHomeStation(favorites.get(0));
                    myApp.setWorkStation(favorites.get(1));

                    finish();
                }else{
                    //로그인 실패시
                    Toast.makeText(getApplicationContext(), "로그인 실패. 아이디와 비밀번호 확인해주세요 ", Toast.LENGTH_LONG).show();
                }
            }
        });



        //뒤로가기와 x버튼. 둘다 누르면 창이 사라지면서 이전 창(처음화면)으로 간다.
        back_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        x_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
