package com.example.project1;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonWrapper {

    public JsonWrapper(){
    }

    public static JSONObject wrapFunction(String funcName){
        JSONObject obj = new JSONObject();
        try{
            obj.put("FUNCTION",funcName);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return obj;
    }


    public static JSONObject wrapSignInID(String id){
        JSONObject obj = wrapFunction("CHECKIDDUPLICATES");
        obj = wrapID(obj,id);
        return obj;
    }

    public static JSONObject wrapSignUpAccount(String id, String pw,String favorite1, String favorite2){
        JSONObject obj = wrapFunction("SIGNUP");
        obj = wrapAccount(obj,id,pw,favorite1,favorite2);
        return obj;
    }

    public static JSONObject wrapSignInAccount(String id, String pw){
        JSONObject obj = wrapFunction("SIGNIN");
        obj = wrapAccount(obj,id,pw);
        return obj;
    }

    public static JSONObject wrapAccount(JSONObject obj, String id,String pw){
        try{
            JSONObject account = new JSONObject();
            account.put("ID",id);
            account.put("PW",pw);
            obj.put("ACCOUNT",account);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return obj;
    }
    public static JSONObject wrapAccount(JSONObject obj, String id,String pw,String favorite1, String favorite2){
        try{
            JSONObject account = new JSONObject();
            account = wrapID(account,id);
            account.put("PW",pw);
            account.put("FAVORITE1",favorite1);
            account.put("FAVORITE2",favorite2);
            obj.put("ACCOUNT",account);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return obj;
    }


    public static JSONObject wrapID(JSONObject obj, String id){
        try{
            obj.put("ID",id);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject wrapSubwayStationInfo(int stationNo, int routeNum) {
        JSONObject obj = wrapFunction("GETSUBWAYTRAINS");
        try {
            obj.put("STATIONID", stationNo);
            obj.put("ROUTE",routeNum);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject wrapSubwayTrainInfo(boolean payed, String id,int trainId) {
        JSONObject obj = wrapFunction("QUERYROOMS");
        try {
            obj.put("PAYED",payed);
            obj = wrapID(obj,id);
            obj.put("TRAINNO", trainId);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject wrapSubwayTrainSeatInfo(int trainNo,int roomNo) {
        JSONObject obj = wrapFunction("QUERYREGISTEREDSEATS");
        try {
            obj.put("TRAINNO", trainNo);
            obj.put("ROOMNO", roomNo);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject wrapGetPoint(String id){
        JSONObject obj  = wrapFunction("GETPOINT");
       obj = wrapID(obj,id);
        return obj;
    }


    public static JSONObject wrapSeatInfo(String loggedId,int trainNo, int roomNo, int stationNo, int seatLoc, int seatNo){
        JSONObject obj = wrapFunction("REGISTERSEATS");
        try{
            obj = wrapID(obj,loggedId);
            obj.put("TRAINNO",trainNo);
            obj.put("ROOMNO",roomNo);
            obj.put("STATIONNO",stationNo);
            obj.put("SEATLOCATION",seatLoc);
            obj.put("SEATNO",seatNo);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return  obj;
    }
}
