package com.example.net_disk.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketConnection {
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private int port;
    private String ip;

    public SocketConnection(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        this.client = new Socket(ip,port);
        this.out = new PrintWriter(client.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println("连接成功");
    }
    public void closeConnection() throws IOException {
        if(client != null){
            client.close();
        }
        if(in != null){
            in.close();
        }
        if(out != null){
            out.close();
        }
    }
    public void send(String s) {
        this.out.println(s);
        this.out.flush();
    }
    public void send(byte[] bytes) {;
//        this.out.flush();
//        System.out.println(Arrays.toString(bytes));
        for (byte aByte : bytes) {
            this.out.println ( aByte );
        }
        this.out.println ( "end" );
        this.out.flush();
    }
    public String receive() throws IOException {
        return in.readLine ();
    }
}
