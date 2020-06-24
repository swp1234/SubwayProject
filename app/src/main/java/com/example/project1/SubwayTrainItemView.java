package com.example.project1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SubwayTrainItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    ImageView imageView;

    public SubwayTrainItemView(Context context) {
        super(context);
        init(context);
    }

    public SubwayTrainItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.subway_train_item,this,true);

        textView = findViewById(R.id.trainNum);
        textView2 = findViewById(R.id.stationName);
        imageView = findViewById(R.id.trainSituationView);
    }

    public void setTrainNum(int num){
        textView.setText(Integer.toString(num));
        if(num==0) {//그 구간에 열차가 없을경우 열차 번호 및 말풍선을 투명처리.
            textView.setAlpha(0.0f);
            textView.setText("");
        }
    }
    public void setSubwayName(String subwayName){textView2.setText(subwayName);}
    public void setImage(int resId){
        imageView.setImageResource(resId);
    }

}