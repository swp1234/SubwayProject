package com.example.project1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SubwayItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    ImageView imageView;

    public SubwayItemView(Context context) {

        super(context);
        init(context);
    }

    public SubwayItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.subway_item,this,true);

        textView = findViewById(R.id.subwayName);
        textView2 = findViewById(R.id.subwayRoute);
        imageView = findViewById(R.id.subwayIcon);
    }

    public void setName(String name){
        textView.setText(name);
    }
    public void setImage(int resId){
        imageView.setImageResource(resId);
    }
    public void setRoute(String route){
        textView2.setText(route+"역 방향");
    }
}
