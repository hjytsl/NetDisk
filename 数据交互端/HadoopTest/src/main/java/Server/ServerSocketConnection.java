package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerSocketConnection {
    private ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int port;
    Socket server;

    public ServerSocketConnection(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }
    public void waitForConnect() throws IOException {
        System.out.println ("等待连接...");
        this.server = serverSocket.accept ();
        this.out = new PrintWriter(server.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        System.out.println ("连接成功");
    }
    public void closeConnection() throws IOException {
        if(server != null){
            server.close();
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
    public void send(byte[] bytes) {
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
    public String getClientIP() {
        return server.getInetAddress().getHostAddress();
    }
    public int getClientPort() {
        return server.getPort ();
    }

}