package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectTrainActivity extends AppCompatActivity {

    int stationArrayNum;
    SubwayTrainAdapter adapter;
    MyApplication myApp;
    int routeNum;

    String[][] station={{"장암","1007000709"},{"도봉산","1007000710"},{"수락산","1007000711"},{"마들","1007000712"},{"노원","1007000713"},{"중계(한국성서대)","1007000714"},{"하계(을지원 을지병원)","1007000715"},{"공릉(서울과학기술대)","1007000716"},
            {"태릉입구","1007000717"}, {"먹골","1007000718"},{"중화","1007000719"},{"상봉(시외버스터미널)","1007000720"},{"면목(서일대입구)","1007000721"},{"사가정(녹색병원)","1007000722"},{"용마산","1007000723"},{"중곡","1007000724"},{"군자(능동)","1007000725"},
            {"어린이대공원(세종대)","1007000726"},{"건대입구","1007000727"},{"뚝섬유원지","1007000728"},{"청담(한국금거래소)","1007000729"},{"강남구청","1007000730"},{"학동","1007000731"},{"논현","1007000732"},{"반포","1007000733"},{"고속터미널","1007000734"},{"내방","1007000735"},
            {"아수","1007000736"},{"남성","1007000737"},{"숭실대입구(살피재)","1007000738"},{"상도","1007000739"},{"장승배기","1007000740"},{"신대방삼거리","1007000741"},{"보라매","1007000742"},{"신풍","1007000743"},{"대림(구로구청)","1007000744"},{"남구로","1007000745"},
            {"가산디지털단지(마리오아울렛)","1007000746"},{"철산","1007000746"},{"광명사거리","1007000748"},{"천왕","1007000749"},{"온수(성공회대입구)","1007000750"},{"까치울","1007000751"},{"부천종합운동장","1007000752"},{"춘의","1007000753"},{"신중동","1007000754"},
            {"부천시청","1007000755"},{"상동","1007000756"},{"삼산체육관","1007000757"},{"굴포천","1007000758"},{"부평구청","1007000759"}};
    int[][] trainSituation= {{0,0},{2,0},{0,0},{2,0},{0,0},{2,0},{0,0},{2,0}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_train);

        myApp=(MyApplication)getApplication();
        routeNum=myApp.getRoute();

        Intent passedIntent = getIntent();


        for(int i=0;i<station.length;i++) {
            if(station[i][1].equals(Integer.toString(myApp.getStationNumber()))) {
                stationArrayNum=i;
                break;
            }
        }

        ServerConnectManager scMgr = new ServerConnectManager();
        JSONObject jsonObj  = JsonWrapper.wrapSubwayStationInfo(myApp.getStationNumber(),myApp.getRoute());
        scMgr.requestQuery(jsonObj.toString());
        while(scMgr.getQueryResult().equals(""));
        JSONArray json;
        try {
            json = new JSONArray(scMgr.getQueryResult());
            //json[0].get("subwayId");
            for (int i = 0; i< json.length();i++){
                JSONObject obj = (JSONObject) json.get(i);

                //String abc = (String)obj.get("statnId")+(String)obj.get("trainNo")+(String)obj.get("recptnDt");

                String route1 = (String)obj.get("updnLine");
                String statnId = (String)obj.get("statnId");
                String abc1 = (String)obj.get("trainNo");
                String abc2 = (String)obj.get("trainSttus");
                int trainSttus = Integer.parseInt(abc2);
                if(routeNum == Integer.parseInt(route1)) {
                    if (routeNum ==1) {
                        for (int j = 0; j < 4; j++) {
                            if (station[stationArrayNum - 4 + j][1].equals(statnId)) {
                                if (trainSttus == 0 || trainSttus == 1) {
                                    trainSituation[2 * j + 1][0] = 3;
                                    trainSituation[2 * j + 1][1] = Integer.parseInt(abc1);
                                } else if (j < 3) {
                                    trainSituation[2 * j ][0] = 1;
                                    trainSituation[2 * j ][1] = Integer.parseInt(abc1);
                                }
                            }
                        }

                    } else {
                        for (int j = 0; j < 4; j++) {
                            if (station[stationArrayNum + 4 - j][1].equals(statnId)) {
                                if (trainSttus == 0 || trainSttus == 1) {
                                    trainSituation[2 * j + 1][0] = 3;
                                    trainSituation[2 * j + 1][1] = Integer.parseInt(abc1);
                                } else {
                                    trainSituation[2 * j ][0] = 1;
                                    trainSituation[2 * j ][1] = Integer.parseInt(abc1);
                                }
                            }
                        }
                    }
                }
                //Toast.makeText(getApplicationContext(),statnId+"    "+abc2,Toast.LENGTH_LONG).show();
                //break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //json에서 추출

        //역 배열에서 몇번째인지 알아내기

        //Log.d("stationArrayNum",Integer.toString(stationArrayNum));
        Button button = (Button)findViewById(R.id.backButton1);
        Button button2 = (Button)findViewById(R.id.xButton1);
        ListView listView = (ListView)findViewById(R.id.trainListView);

        adapter = new SubwayTrainAdapter();



        if(routeNum==0) {
            for (int i = 0; i < 4; i++) {
                if (i+stationArrayNum-3 > station.length) {
                } else {
                    adapter.addItem(new SubwayTrainItem("", trainSituation[i * 2][0], trainSituation[i * 2][1]));
                    adapter.addItem(new SubwayTrainItem(station[stationArrayNum+3-i][0], trainSituation[i * 2 + 1][0], trainSituation[i * 2 + 1][1]));
                }
            }//열차가 있는가? 있다면 몇번이고 어디 위치에 있는가?
            //adapter.addItem(new SubwayTrainItem()); String subwayName, int trainSituation, int trainNum
            //int trainSituation[i][0] = 0->위치는 역 중간, 없다 / 1->위치는 역 중간, 있다 / 2->위치는 역, 없다 / 3->위치는 역, 있다.
        }else{
            //Log.d("routeNum",Integer.toString(routeNum));
            for (int i = 0; i < 4; i++) {
                if (stationArrayNum-3+i < 0) {
                } else {
                    adapter.addItem(new SubwayTrainItem("", trainSituation[i * 2][0], trainSituation[i * 2][1]));
                    adapter.addItem(new SubwayTrainItem(station[stationArrayNum-3+i][0], trainSituation[i * 2 + 1][0], trainSituation[i * 2 + 1][1]));
                }
            }
        }
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubwayTrainItem item = (SubwayTrainItem) adapter.getItem(position);
                if(item.getTrainNum()!=0) {
                    //Toast.makeText(getApplicationContext(),"선택 : "+item.getName(), Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getApplicationContext(), OverviewActivity.class);
                    myApp.setTrainNumber(item.getTrainNum());
                    myApp.setPayed(false);
                    myApp.setPayedText(false);

                    startActivityForResult(intent1, 120);
                }
            }
        });

        //뒤로가기 버튼들
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    class SubwayTrainAdapter extends BaseAdapter {
        ArrayList<SubwayTrainItem> items = new ArrayList<SubwayTrainItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SubwayTrainItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SubwayTrainItemView view = new SubwayTrainItemView(getApplicationContext());

            SubwayTrainItem item = items.get(position);
            view.setSubwayName(item.getSubwayName());
            view.setImage(item.getTrainSituation());
            view.setTrainNum(item.getTrainNum());

            return view;
        }
    }
}
