package com.example.project1;

public class SubwayItem {

    String name;
    String route;
    int num;
    int routeNum;//0: 정방향 , 1: 역방향
    int resId;


    public SubwayItem(String name, int num, int resId,String route,int routeNum) {
        this.name = name;
        this.num = num;
        this.resId=resId;
        this.route=route;
        this.routeNum=routeNum;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getRouteNum() {
        return routeNum;
    }

    public void setRouteNum(int routeNum) {
        this.routeNum = routeNum;
    }
}