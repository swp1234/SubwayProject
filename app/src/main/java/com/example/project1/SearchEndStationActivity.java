package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class SearchEndStationActivity extends AppCompatActivity {
    MyApplication myApp;

    boolean homeStationAdded=false;
    boolean workStationAdded=false;
    String searchText;
    SubwayAdapter adapter;
    String[][] station={{"장암","1007000709"},{"도봉산","1007000710"},{"수락산","1007000711"},{"마들","1007000712"},{"노원","1007000713"},{"중계(한국성서대)","1007000714"},{"하계(을지원 을지병원)","1007000715"},{"공릉(서울과학기술대)","1007000716"},
            {"태릉입구","1007000717"}, {"먹골","1007000718"},{"중화","1007000719"},{"상봉(시외버스터미널)","1007000720"},{"면목(서일대입구)","1007000721"},{"사가정(녹색병원)","1007000722"},{"용마산","1007000723"},{"중곡","1007000724"},{"군자(능동)","1007000725"},
            {"어린이대공원(세종대)","1007000726"},{"건대입구","1007000727"},{"뚝섬유원지","1007000728"},{"청담(한국금거래소)","1007000729"},{"강남구청","1007000730"},{"학동","1007000731"},{"논현","1007000732"},{"반포","1007000733"},{"고속터미널","1007000734"},{"내방","1007000735"},
            {"아수","1007000736"},{"남성","1007000737"},{"숭실대입구(살피재)","1007000738"},{"상도","1007000739"},{"장승배기","1007000740"},{"신대방삼거리","1007000741"},{"보라매","1007000742"},{"신풍","1007000743"},{"대림(구로구청)","1007000744"},{"남구로","1007000745"},
            {"가산디지털단지(마리오아울렛)","1007000746"},{"철산","1007000746"},{"광명사거리","1007000748"},{"천왕","1007000749"},{"온수(성공회대입구)","1007000750"},{"까치울","1007000751"},{"부천종합운동장","1007000752"},{"춘의","1007000753"},{"신중동","1007000754"},
            {"부천시청","1007000755"},{"상동","1007000756"},{"삼산체육관","1007000757"},{"굴포천","1007000758"},{"부평구청","1007000759"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_end_station);

        myApp=(MyApplication)getApplication();

        Intent passedIntent = getIntent();

        //Toast.makeText(getApplicationContext(),""+carNumber+" "+seatNumber+" "+trainNumber, Toast.LENGTH_LONG).show();

        final TextView textView  =(TextView)findViewById(R.id.searchtext);
        Button searchButton = (Button)findViewById(R.id.searchbutton);
        Button backButton = (Button)findViewById(R.id.backButton1);
        Button xButton = (Button)findViewById(R.id.xButton1);
        Button homeButton = (Button)findViewById(R.id.homeshortcut);
        Button workButton = (Button)findViewById(R.id.workshortcut);

        ListView listView = (ListView)findViewById(R.id.subwayListView);

        adapter = new SubwayAdapter();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                searchText  = textView.getText().toString();
                for(int i=0;i<station.length;i++){
                    if(station[i][0].contains(searchText)){

                        adapter.addItem(new SubwayItem(station[i][0],Integer.parseInt(station[i][1]),R.drawable.sevenline,station[i-1][0],1));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });



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
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<station.length;i++){
                    if(station[i][1].equals(myApp.getHomeStation())){

                        adapter.addItem(new SubwayItem(station[i][0],Integer.parseInt(station[i][1]),R.drawable.sevenline,station[i-1][0],1));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        workButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<station.length;i++){
                    if(station[i][1].equals(myApp.getWorkStation())){

                        adapter.addItem(new SubwayItem(station[i][0],Integer.parseInt(station[i][1]),R.drawable.sevenline,station[i-1][0],1));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        listView.setAdapter(adapter);

        //해당하는 도착 역 선택
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SubwayItem item = (SubwayItem) adapter.getItem(position);
                boolean rightPath;
                if(myApp.getRoute()==0){
                    rightPath= (item.getNum()<myApp.getStationNumber());
                }else{
                    rightPath= (item.getNum()>myApp.getStationNumber());
                }

                if(rightPath){
                    Toast.makeText(getApplicationContext(),item.getName()+"역 선택", Toast.LENGTH_LONG).show();
                    myApp.setEndStationNumber(item.getNum());
                    Intent intent1 = new Intent(getApplicationContext(), EndActivity.class);

                    JSONObject json = JsonWrapper.wrapSeatInfo(myApp.getLoggedId(),myApp.getTrainNumber(),myApp.getCarNumber(),item.getNum(),myApp.getSelectedCarLocation(),myApp.getSelectedSeat());
                    ServerConnectManager scMgr = new ServerConnectManager();
                    scMgr.requestQuery(json.toString());

                    startActivityForResult(intent1, 510);
                }else{
                    Toast.makeText(getApplicationContext(),"방향과 맞지 않는 역 선탁하셨습니다.", Toast.LENGTH_LONG).show();
                }


            }
        });

    }



    class SubwayAdapter extends BaseAdapter {
        ArrayList<SubwayItem> items = new ArrayList<SubwayItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SubwayItem item){
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
            SubwayItemView view = new SubwayItemView(getApplicationContext());

            SubwayItem item = items.get(position);
            view.setName(item.getName());
            view.setImage(item.getResId());
            //view.setRoute(item.getRoute());
            return view;
        }
        public void clear(){items.clear();}
    }
}