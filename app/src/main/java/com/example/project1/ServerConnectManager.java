package com.example.project1;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectManager
{
    private static final int PORT = 50000; //서버에서 설정한 PORT 번호
    private static final String IP = "211.209.140.135"; //서버 단말기의 IP주소..

    private Socket socket;
    private String result="";


    public void  requestQuery(final String obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(IP, PORT);
                    System.out.println(socket);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    oos.writeObject(obj);
                    oos.flush();
                    result = (String) ois.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public String getQueryResult(){
        return this.result;
    }
}



