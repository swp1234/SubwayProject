package src;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class SubwayAPIManager {
	final String KEY = "556542515268626b39365865426561";
	final String APIURL = "http://swopenAPI.seoul.go.kr/api/subway/";
	final String SERVICENAME = "realtimePosition";
	final int START_INDEX = 0;
	final int END_INDEX = 100;
	final int SEARCH_RANGE = 4;
	public SubwayAPIManager() {
		
	}

	private JSONObject getSubwayLocationInfoFromAPI(String type, String subwayLine) {
		BufferedInputStream reader = null;
		String dataLine = "";
		StringBuilder result = new StringBuilder();
		JSONObject obj = null;
		try {
			String fullURL = APIURL + KEY + "/" + type + "/" + SERVICENAME + "/" + START_INDEX + "/" + END_INDEX + "/";
			String utfSubwayName = URLEncoder.encode(subwayLine, "utf-8");
			fullURL += utfSubwayName;
			URL url = new URL(fullURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((dataLine = br.readLine()) != null) {
					result.append(dataLine);
				}
				br.close();
				JSONParser parser = new JSONParser();
				obj = (JSONObject) parser.parse(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public JSONArray getSubwayTrainsLocation() {
		JSONObject location = null;
		while(location==null) location = getSubwayLocationInfoFromAPI("json", "7호선");
		JSONArray subwayLocations =(JSONArray) location.get("realtimePositionList");
		return subwayLocations;
	}
	
	public String getSubwayLocation(int stationID, int route) {
		int beginStation = stationID -SEARCH_RANGE;
		int endStation = stationID+SEARCH_RANGE;
		
		JSONArray subwayList = new JSONArray();
		JSONObject location = null;
		while(location == null) location = getSubwayLocationInfoFromAPI("json", "7호선");
		JSONArray subwayLocations =(JSONArray) location.get("realtimePositionList");
		for(int i = 0; i<subwayLocations.size();i++) {
			JSONObject obj = (JSONObject) subwayLocations.get(i);
			int routeNum = Integer.parseInt(obj.get("updnLine").toString());
			int statnID = Integer.parseInt(obj.get("statnId").toString());
			if(routeNum == route && statnID >=beginStation && statnID <= endStation) {
				subwayList.add(obj);
			}
		}
		return subwayList.toJSONString();
	}
	
	public int GetTrainCurrentLocation(int trainNo){
		int currentLocation = 0;
		JSONArray jsonArray = getSubwayTrainsLocation();
		for (int j = 0; j < jsonArray.size(); j++) {
			JSONObject train = (JSONObject) jsonArray.get(j);
			if (Integer.parseInt(train.get("trainNo").toString()) == trainNo) {
				if (Integer.parseInt(train.get("trainSttus").toString()) == 0) {
					currentLocation = Integer.parseInt(train.get("statnId").toString()) - 1;
					break;
				} else {
					currentLocation = Integer.parseInt(train.get("statnId").toString());
					break;
				}
			}
		}
		return currentLocation;
	}

	public int GetTrainUpdnLine(int trainNo) {
		int updnLine = 0;
		JSONArray jsonArray = getSubwayTrainsLocation();
		for (int j = 0; j < jsonArray.size(); j++) {
			JSONObject train = (JSONObject) jsonArray.get(j);
			if (Integer.parseInt(train.get("trainNo").toString()) == trainNo) {
				updnLine = Integer.parseInt(train.get("updnLine").toString());
			}
		}
		updnLine = (updnLine==0)?-1:1; 
		return updnLine;
	}


}
