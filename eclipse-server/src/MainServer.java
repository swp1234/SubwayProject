package src;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MainServer implements Runnable {
	private final int RED = 3;
	private final int YELLOW = 6;
	private final int GREEN = 10;

	public static final int ServerPort = 50000;
	public static final String ServerIP = "127.0.0.1";
	static DBManager dbManager = new DBManager();
	static SubwayAPIManager subwayAPIManager = new SubwayAPIManager();
	Vector<Subway> subwayTrains = new Vector<Subway>();
	
	
	
	@Override
	public void run() {

		try {
			System.out.println("S: Connecting...");
			ServerSocket serverSocket = new ServerSocket(ServerPort);
			
			// TEST CODE //
			/*
			int trainnumber = 7176;
			Subway subway = new Subway(trainnumber);
			subwayTrains.add(subway);
			long seed = System.currentTimeMillis();
			Random rand = new Random(seed);
			for(int i = 0; i<100;i++) {
				int seatLoc = Math.abs(rand.nextInt())%3;
				int seatNo =  Math.abs(rand.nextInt())%14;
				int destination = 1007000727 - Math.abs(rand.nextInt()) % 10;
				int roomNumber = Math.abs(rand.nextInt()) % 9;
				String registeredID = Integer.toString(rand.nextInt()) + Integer.toString(rand.nextInt());
				Seat seat = new Seat(seatLoc, seatNo, destination, roomNumber, registeredID);
				if(!subwayTrains.get(0).seats.contains(seat)) {
					subwayTrains.get(0).seats.add(seat);
					System.out.println("객실번호:" + roomNumber + " 좌석위치: " + seatLoc + " 좌석번호: " + seatNo
							+ " 목적지: " + destination + " ID: " + registeredID + " 등록됨");
				}
			}
			*/
			
			while (true) {
				Socket client = serverSocket.accept();
				InetAddress clientInet = client.getInetAddress();
				int clientPort = client.getPort();
				String clientIp = clientInet.getHostAddress();
				System.out.println("IP : "+clientIp+" CONNECTION START");
				new DataExchangeThread(client).start();
			}
		} catch (Exception e) {
			System.out.println("S: Error");
			e.printStackTrace();
		} finally {

		}

	}

	public static void main(String[] args) {
		Thread connectThread = new Thread(new MainServer());
		connectThread.start();
	}

	class DataExchangeThread extends Thread {

		Socket clientSocket;

		DataExchangeThread(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			try {
				ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
				String receivedJSONObj = (String) ois.readObject();
				JSONParser jsonParser = new JSONParser();
				JSONObject obj = (JSONObject) jsonParser.parse(receivedJSONObj);
				String result = HandleUserRequest(obj);
				out.writeObject(result);
				out.flush();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public String HandleUserRequest(JSONObject obj) {
			String function = (String) obj.get("FUNCTION");
			String requestResult = "";
			switch (function) {
			case "CHECKIDDUPLICATES":
				requestResult = CheckIDDuplicates(obj) ? "Duplicate" : "NotDuplicate";
				break;
			case "SIGNUP":
				requestResult = CreateNewAccount(obj) ? "ACCOUNT CREATED" : "ACCOUNT NOT CREATED";
				break;
			case "SIGNIN":
				Vector<String> favorites = SignIn(obj);
				if (favorites.size() == 0)
					requestResult = "LOGIN FAIL";
				else {
					JSONObject favorite = new JSONObject();
					favorite.put("FAVORITE1", favorites.get(0));
					favorite.put("FAVORITE2", favorites.get(1));
					requestResult = favorite.toString();
				}
				break;
			case "GETSUBWAYTRAINS":
				requestResult = GetSubwayLocation(obj);
				break;
			case "REGISTERSEATS":
				RegisterSeats(obj);
				break;
			case "QUERYREGISTEREDSEATS":
				requestResult = QuerySeats(obj);
				break;
			case "GETPOINT":
				requestResult = String.valueOf(GetPoint(obj));
			case "QUERYROOMS":
				requestResult = QueryRooms(obj);
			}
			return requestResult;
		}

		public boolean CheckIDDuplicates(JSONObject obj) {
			return dbManager.checkIDDuplicates((String) obj.get("ID"));
		}

		public int GetPoint(JSONObject obj) {
			return dbManager.getPoint((String) obj.get("ID"));
		}

		public boolean CreateNewAccount(JSONObject obj) {
			JSONObject jsonObject = (JSONObject) obj.get("ACCOUNT");
			String id = (String) jsonObject.get("ID");
			String pw = (String) jsonObject.get("PW");
			String favorite1 = (String) jsonObject.get("FAVORITE1");
			String favorite2 = (String) jsonObject.get("FAVORITE2");
			Account newAccount = new Account(id, pw, favorite1, favorite2);
			return dbManager.createNewAccount(newAccount);
		}

		public Vector<String> SignIn(JSONObject obj) {
			JSONObject jsonObject = (JSONObject) obj.get("ACCOUNT");
			String id = (String) jsonObject.get("ID");
			String pw = (String) jsonObject.get("PW");
			Account account = new Account(id, pw);
			return dbManager.logIn(account);
		}

		public String GetSubwayLocation(JSONObject obj) {
			int stationID = ((Long) obj.get("STATIONID")).intValue();
			int route = ((Long) obj.get("ROUTE")).intValue();
			return subwayAPIManager.getSubwayLocation(stationID, route);
		}

		public void RegisterSeats(JSONObject obj) {
			String id = (String) obj.get("ID");
			boolean isPointAddable = RefreshSeats(id);
			int trainID = ((Long) obj.get("TRAINNO")).intValue();
			int roomID = ((Long) obj.get("ROOMNO")).intValue()-1;
			int seatLoc = ((Long) obj.get("SEATLOCATION")).intValue();
			int seatNo = ((Long) obj.get("SEATNO")).intValue();
			int destination = ((Long) obj.get("STATIONNO")).intValue();
			boolean isTrainExists = false;

			for (int i = 0; i < subwayTrains.size(); i++) {
				if (subwayTrains.get(i).getSubwayTrainNo() == trainID) {
					isTrainExists = true;
					Seat seat = new Seat(seatLoc, seatNo, destination, roomID, id);
					subwayTrains.get(i).seats.add(seat);
					break;
				}
			}

			if (!isTrainExists) {
				Subway subway = new Subway(trainID);
				Seat seat = new Seat(seatLoc, seatNo, destination, roomID, id);
				subway.seats.add(seat);
				subwayTrains.add(subway);
			}

			if (isPointAddable) {
				dbManager.addPoints(id);
			}
			System.out.println("열차번호: " + trainID + " 객실번호:" + roomID + " 좌석위치: " + seatLoc + " 좌석번호: " + seatNo
					+ " 목적지: " + destination + " ID: " + id + " 등록됨");
		}

		public boolean RefreshSeats(String id) {
			boolean isIdRegistered = true;
			for (int i = 0; i < subwayTrains.size(); i++) {
				int trainNo = subwayTrains.get(i).getSubwayTrainNo();
				int currentLoc = subwayAPIManager.GetTrainCurrentLocation(trainNo);
				int updnLine = subwayAPIManager.GetTrainUpdnLine(trainNo);

				Iterator<Seat> it = subwayTrains.get(i).seats.iterator();
				while (it.hasNext()) {
					Seat seat = it.next();
					if (seat.GetDestination() * updnLine <= currentLoc * updnLine) {
						it.remove();
					} else if (seat.registeredId.equals(id)) {
						it.remove();
						isIdRegistered = false;
					}
				}
			}
			return isIdRegistered;
		}

		public String QuerySeats(JSONObject obj) {
			RefreshSeats("");
			String result = "";
			int trainNo = ((Long) obj.get("TRAINNO")).intValue();
			int roomNo = ((Long) obj.get("ROOMNO")).intValue()-1;
			JSONArray arr = new JSONArray();
			int currentLoc = subwayAPIManager.GetTrainCurrentLocation(trainNo);
			for (int i = 0; i < subwayTrains.size(); i++) {
				if (subwayTrains.get(i).getSubwayTrainNo() == trainNo) {
					Iterator<Seat> it = subwayTrains.get(i).seats.iterator();
					while (it.hasNext()) {
						JSONObject seat = new JSONObject();
						Seat temp = (Seat) it.next();
						if (temp.GetRoomNumber() == roomNo) {
							int toDestination = ColorCodeSeat(Math.abs(currentLoc - temp.GetDestination()));
							seat.put("LOCATION", temp.GetSeatLoc());
							seat.put("SEATNO", temp.GetSeatNo());
							seat.put("DESTINATION", toDestination);
							arr.add(seat);
						}
					}
				}
			}
			result = arr.toJSONString();
			System.out.println(result + " 조회됨");
			return result;
		}

		public String QueryRooms(JSONObject obj) {

			String result = "";
			String id = (String) obj.get("ID");

			int currentLoc = 0;
			boolean payed = (boolean)obj.get("PAYED");
			int trainNo = ((Long) obj.get("TRAINNO")).intValue();
			boolean isQueryAvailable = dbManager.checkPoints(id);
			JSONArray jsonArray = new JSONArray();

			RefreshSeats("");
			if (isQueryAvailable || payed) {
				if(!payed) dbManager.subtractPoints(id);
				Room[] rooms = new Room[9];
				for (int i = 0; i < 9; i++) {
					Room room = new Room();
					rooms[i] = room;
				}

				currentLoc = subwayAPIManager.GetTrainCurrentLocation(trainNo);
				for (int i = 0; i < subwayTrains.size(); i++) {
					if (subwayTrains.get(i).getSubwayTrainNo() == trainNo) {
						Iterator<Seat> it = subwayTrains.get(i).seats.iterator();
						while (it.hasNext()) {
							Seat seat = (Seat) it.next();
							int toDestination = ColorCodeSeat(Math.abs(currentLoc - seat.GetDestination()));
							rooms[seat.GetRoomNumber()].AddColoredSeat(toDestination);
						}
					}
				}

				for (int i = 0; i < 9; i++) {
					Iterator<Integer> it = rooms[i].coloredSeats.iterator();
					int green = 0;
					int yellow = 0;
					int red = 0;
					while (it.hasNext()) {
						int color = it.next();
						if (color == 3)
							green++;
						else if (color == 2)
							yellow++;
						else
							red++;
					}
					JSONObject json = new JSONObject();
					json.put("ROOMNO",i);	
					int sendColor = 0;
					if(green ==0  && yellow ==0 && red ==0 ) sendColor = 0;
					else if(Math.max(Math.max(green, yellow), red) == green ) sendColor = 3;
					else if(Math.max(Math.max(green, yellow), red) == yellow) sendColor = 2;
					else if(Math.max(Math.max(green, yellow), red) == red) sendColor = 1;
					json.put("COLOR", sendColor);
					jsonArray.add(json);
				}
				JSONObject point = new JSONObject();
				point.put("POINT", dbManager.getPoint(id));
				jsonArray.add(point);
				result = jsonArray.toJSONString();
			} else {
				result = "POINTNOTENOUGH";
			}
			System.out.println(result + " 조회됨");
			return result;
		}
	}

	public int ColorCodeSeat(int toDestination) {
		if (toDestination <= RED) {
			return 1;
		} else if (toDestination <= YELLOW) {
			return 2;
		} else {
			return 3;
		}
	}

}
