package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeatSelectionActivity extends AppCompatActivity {
    int carNumber;
    int trainNumber;
    boolean payed;
    MyApplication myApp;

    int[][] seatSituation = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0}};// 임의의 자리 상황. 0=빈 공간/1=초록/2=노랑/3=빨강

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        myApp=(MyApplication)getApplication();

        Intent passedIntent = getIntent();
        carNumber = myApp.getCarNumber();
        trainNumber = myApp.getTrainNumber();
        payed = myApp.isPayed();



        Button backButton = findViewById(R.id.backButton4);
        Button oldSeat1 = findViewById(R.id.oldButton1);
        Button oldSeat2 = findViewById(R.id.oldButton1);
        Button oldSeat3 = findViewById(R.id.oldButton1);
        Button oldSeat4 = findViewById(R.id.oldButton1);

        TextView textView = findViewById(R.id.trainDataTextView);
        TextView textView1= findViewById(R.id.exitTextView1st);
        TextView textView12= findViewById(R.id.exitTextView12st);
        TextView textView2= findViewById(R.id.exitTextView2st);
        TextView textView22= findViewById(R.id.exitTextView22st);
        TextView textView3= findViewById(R.id.exitTextView3st);
        TextView textView32= findViewById(R.id.exitTextView32st);

        //자리를 arraylist로 만들어 왼쪽/중간/오른쪽 칸으로 분류하여 배분.
        ArrayList<Button> seatTop= new ArrayList<Button>();
        seatTop.add((Button)findViewById(R.id.seatTopTop1));
        seatTop.add((Button)findViewById(R.id.seatTopTop2));
        seatTop.add((Button)findViewById(R.id.seatTopTop3));
        seatTop.add((Button)findViewById(R.id.seatTopTop4));
        seatTop.add((Button)findViewById(R.id.seatTopTop5));
        seatTop.add((Button)findViewById(R.id.seatTopTop6));
        seatTop.add((Button)findViewById(R.id.seatTopTop7));
        seatTop.add((Button)findViewById(R.id.seatTopBot1));
        seatTop.add((Button)findViewById(R.id.seatTopBot2));
        seatTop.add((Button)findViewById(R.id.seatTopBot3));
        seatTop.add((Button)findViewById(R.id.seatTopBot4));
        seatTop.add((Button)findViewById(R.id.seatTopBot5));
        seatTop.add((Button)findViewById(R.id.seatTopBot6));
        seatTop.add((Button)findViewById(R.id.seatTopBot7));

        ArrayList<Button> seatMid= new ArrayList<Button>();
        seatMid.add((Button)findViewById(R.id.seatMidTop1));
        seatMid.add((Button)findViewById(R.id.seatMidTop2));
        seatMid.add((Button)findViewById(R.id.seatMidTop3));
        seatMid.add((Button)findViewById(R.id.seatMidTop4));
        seatMid.add((Button)findViewById(R.id.seatMidTop5));
        seatMid.add((Button)findViewById(R.id.seatMidTop6));
        seatMid.add((Button)findViewById(R.id.seatMidTop7));
        seatMid.add((Button)findViewById(R.id.seatMidBot1));
        seatMid.add((Button)findViewById(R.id.seatMidBot2));
        seatMid.add((Button)findViewById(R.id.seatMidBot3));
        seatMid.add((Button)findViewById(R.id.seatMidBot4));
        seatMid.add((Button)findViewById(R.id.seatMidBot5));
        seatMid.add((Button)findViewById(R.id.seatMidBot6));
        seatMid.add((Button)findViewById(R.id.seatMidBot7));

        ArrayList<Button> seatBot= new ArrayList<Button>();
        seatBot.add((Button)findViewById(R.id.seatBotTop1));
        seatBot.add((Button)findViewById(R.id.seatBotTop2));
        seatBot.add((Button)findViewById(R.id.seatBotTop3));
        seatBot.add((Button)findViewById(R.id.seatBotTop4));
        seatBot.add((Button)findViewById(R.id.seatBotTop5));
        seatBot.add((Button)findViewById(R.id.seatBotTop6));
        seatBot.add((Button)findViewById(R.id.seatBotTop7));
        seatBot.add((Button)findViewById(R.id.seatBotBot1));
        seatBot.add((Button)findViewById(R.id.seatBotBot2));
        seatBot.add((Button)findViewById(R.id.seatBotBot3));
        seatBot.add((Button)findViewById(R.id.seatBotBot4));
        seatBot.add((Button)findViewById(R.id.seatBotBot5));
        seatBot.add((Button)findViewById(R.id.seatBotBot6));
        seatBot.add((Button)findViewById(R.id.seatBotBot7));



        //자리 색깔 코딩. 포인트 낸 경우만 색 보여주고 아닌 경우에는 안보여주기.
        if(payed) {
            ServerConnectManager scMgr = new ServerConnectManager();
            JSONObject jsonObj  = JsonWrapper.wrapSubwayTrainSeatInfo(trainNumber,carNumber);
            scMgr.requestQuery(jsonObj.toString());
            while(scMgr.getQueryResult().equals(""));
            JSONArray jsonArray = null;
            try {
                //등록된 좌석정보 들어있음 키 : LOCATION - 좌석 위치(처음 중간 끝), SEATNO - 좌석 번호, DESTINATION - 123 컬러값
                jsonArray = new JSONArray(scMgr.getQueryResult());
                for (int i = 0; i< jsonArray.length();i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);

                    int seatNo = (Integer) obj.get("SEATNO");
                    int location = (Integer) obj.get("LOCATION");
                    int destination = (Integer) obj.get("DESTINATION");

                    seatSituation[location][seatNo]=destination;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < 7; j++) {
                if (seatSituation[0][j] == 0)
                    seatTop.get(j).setBackgroundResource(R.drawable.person1);
                else if (seatSituation[0][j] == 1)
                    seatTop.get(j).setBackgroundResource(R.drawable.personred);
                else if (seatSituation[0][j] == 2)
                    seatTop.get(j).setBackgroundResource(R.drawable.personorange);
                else if (seatSituation[0][j] == 3)
                    seatTop.get(j).setBackgroundResource(R.drawable.persongreen);
            }
            for (int j = 7; j < 14; j++) {
                if (seatSituation[0][j] == 0)
                    seatTop.get(j).setBackgroundResource(R.drawable.personbottom1);
                else if (seatSituation[0][j] == 1)
                    seatTop.get(j).setBackgroundResource(R.drawable.personbottomred1);
                else if (seatSituation[0][j] == 2)
                    seatTop.get(j).setBackgroundResource(R.drawable.personbottomorange1);
                else if (seatSituation[0][j] == 3)
                    seatTop.get(j).setBackgroundResource(R.drawable.personbottomgreen1);
            }
            for (int j = 0; j < 7; j++) {
                if (seatSituation[1][j] == 0)
                    seatMid.get(j).setBackgroundResource(R.drawable.person1);
                else if (seatSituation[1][j] == 1)
                    seatMid.get(j).setBackgroundResource(R.drawable.personred);
                else if (seatSituation[1][j] == 2)
                    seatMid.get(j).setBackgroundResource(R.drawable.personorange);
                else if (seatSituation[1][j] == 3)
                    seatTop.get(j).setBackgroundResource(R.drawable.persongreen);
            }
            for (int j = 7; j < 14; j++) {
                if (seatSituation[1][j] == 0)
                    seatMid.get(j).setBackgroundResource(R.drawable.personbottom1);
                else if (seatSituation[1][j] == 1)
                    seatMid.get(j).setBackgroundResource(R.drawable.personbottomred1);
                else if (seatSituation[1][j] == 2)
                    seatMid.get(j).setBackgroundResource(R.drawable.personbottomorange1);
                else if (seatSituation[1][j] == 3)
                    seatMid.get(j).setBackgroundResource(R.drawable.personbottomgreen1);
            }
            for (int j = 0; j < 7; j++) {
                if (seatSituation[2][j] == 0)
                    seatBot.get(j).setBackgroundResource(R.drawable.person1);
                else if (seatSituation[2][j] == 1)
                    seatBot.get(j).setBackgroundResource(R.drawable.personred);
                else if (seatSituation[2][j] == 2)
                    seatBot.get(j).setBackgroundResource(R.drawable.personorange);
                else if (seatSituation[2][j] == 3)
                    seatBot.get(j).setBackgroundResource(R.drawable.persongreen);
            }
            for (int j = 7; j < 14; j++) {
                if (seatSituation[2][j] == 0)
                    seatBot.get(j).setBackgroundResource(R.drawable.personbottom1);
                else if (seatSituation[2][j] == 1)
                    seatBot.get(j).setBackgroundResource(R.drawable.personbottomred1);
                else if (seatSituation[2][j] == 2)
                    seatBot.get(j).setBackgroundResource(R.drawable.personbottomorange1);
                else if (seatSituation[2][j] == 3)
                    seatBot.get(j).setBackgroundResource(R.drawable.personbottomgreen1);
            }
        }else {
            Toast.makeText(getApplicationContext(),"자리 검색이 아닌 지정만 가능합니다.", Toast.LENGTH_LONG).show();
        }


        textView.setText("7호선 "+trainNumber+"열차 "+carNumber+"번 차");
        textView1.setText(carNumber+"-1");
        textView12.setText(carNumber+"-2");
        textView2.setText(carNumber+"-2");
        textView22.setText(carNumber+"-3");
        textView3.setText(carNumber+"-3");
        textView32.setText(carNumber+"-4");


        //자리 버튼 클릭시(왼쪽칸)
        final Intent intent = new Intent(getApplicationContext(), SearchEndStationActivity.class);


        oldSeat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"노약자석은 지정할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        });
        oldSeat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"노약자석은 지정할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        });
        oldSeat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"노약자석은 지정할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        });
        oldSeat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"노약자석은 지정할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        });



        seatTop.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][0] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(0);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][1] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(1);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][2] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(2);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][3] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(3);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][4] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(4);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][5] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(5);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][6] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(6);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][7] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(7);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][8] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(8);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][9] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(9);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][10] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(10);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][11] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(11);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][12] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(12);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatTop.get(13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[0][13] ==0) {
                    myApp.setSelectedCarLocation(0);
                    myApp.setSelectedSeat(13);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        //중앙 칸 자리 버튼 누를시
        seatMid.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][0] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(0);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][1] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(1);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][2] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(2);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][3] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(3);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][4] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(4);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][5] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(5);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][6] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(6);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][7] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(7);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][8] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(8);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][9] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(9);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][10] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(10);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][11] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(11);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][12] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(12);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatMid.get(13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[1][13] ==0) {
                    myApp.setSelectedCarLocation(1);
                    myApp.setSelectedSeat(13);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //밑칸 자리 버튼 누를시
        seatBot.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][0] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(0);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][1] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(1);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][2] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(2);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][3] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(3);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][4] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(4);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][5] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(5);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][6] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(6);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][7] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(7);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][8] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(8);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][9] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(9);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][10] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(10);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][11] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(11);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][12] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(12);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        seatBot.get(13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatSituation[2][13] ==0) {
                    myApp.setSelectedCarLocation(2);
                    myApp.setSelectedSeat(13);
                    startActivityForResult(intent, 130);
                } else{
                    Toast.makeText(getApplicationContext(),"빈 자리만 지정할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });





        //뒤로 가기 버튼. 누르면 다시 열차칸 선택으로 돌아간다.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
