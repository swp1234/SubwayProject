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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchStationActivity extends AppCompatActivity {

    String searchWord;
    MyApplication myApp;
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
        setContentView(R.layout.activity_search_station);

        myApp = (MyApplication)getApplication();

        Intent passedIntent = getIntent();
        processIntent(passedIntent);
        searchWord = passedIntent.getExtras().getString("searchWord");

        Button button = (Button)findViewById(R.id.searchbackbutton);
        Button button2 = (Button)findViewById(R.id.xbutton);
        Button button3 = (Button)findViewById(R.id.homeshortcut);
        Button button4 = (Button)findViewById(R.id.workshortcut);
        ListView listView = (ListView)findViewById(R.id.subwayListView);
        TextView textView = (TextView)findViewById(R.id.searchtext);

        textView.setText(searchWord);



        adapter = new SubwayAdapter();
        for(int i=0;i<station.length;i++){
            if(station[i][0].contains(searchWord)&&!searchWord.equals("")){
                if(i!=0) adapter.addItem(new SubwayItem(station[i][0],Integer.parseInt(station[i][1]),R.drawable.sevenline,station[i-1][0],0));
                if(i!=station.length-1) adapter.addItem(new SubwayItem(station[i][0],Integer.parseInt(station[i][1]),R.drawable.sevenline,station[i+1][0],1));
            }
        }

        //단축기인 home이나 work 눌렀을 경우
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                    for (int i = 0; i < station.length; i++) {
                        if (station[i][1].equals(myApp.getHomeStation())) {
                            if (i != 0)
                                adapter.addItem(new SubwayItem(station[i][0], Integer.parseInt(station[i][1]), R.drawable.sevenline, station[i - 1][0], 0));
                            if (i != station.length - 1)
                                adapter.addItem(new SubwayItem(station[i][0], Integer.parseInt(station[i][1]), R.drawable.sevenline, station[i + 1][0], 1));
                            adapter.notifyDataSetChanged();
                        }
                    }

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                    for (int i = 0; i < station.length; i++) {
                        if (station[i][1].equals(myApp.getWorkStation())) {
                            if (i != 0)
                                adapter.addItem(new SubwayItem(station[i][0], Integer.parseInt(station[i][1]), R.drawable.sevenline, station[i - 1][0], 0));
                            if (i != station.length - 1)
                                adapter.addItem(new SubwayItem(station[i][0], Integer.parseInt(station[i][1]), R.drawable.sevenline, station[i + 1][0], 1));
                            adapter.notifyDataSetChanged();
                        }
                    }

            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubwayItem item = (SubwayItem) adapter.getItem(position);
                //Toast.makeText(getApplicationContext(),"선택 : "+item.getName(), Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getApplicationContext(), SelectTrainActivity.class);
                myApp.setRoute(item.getRouteNum());
                myApp.setStationNumber(item.getNum());
                startActivityForResult(intent1, 110);
            }
        });

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

    private void processIntent(Intent intent){
        if(intent !=null){
            searchWord = intent.getStringExtra("searchWord");
        }
    }

    class SubwayAdapter extends BaseAdapter{
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
            view.setRoute(item.getRoute());
            return view;
        }
        public void clear(){items.clear();}
    }

}