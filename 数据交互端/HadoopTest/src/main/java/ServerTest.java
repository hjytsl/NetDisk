import Server.ServerSocketConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerTest {
    Scanner sc = new Scanner ( System.in );

    public static void main ( String[] args ) throws Exception {
        ServerSocketConnection server = new ServerSocketConnection ( 9999 );//开启端口号9999的服务器
        server.waitForConnect ();//等待连接，在连接成功之前会阻塞程序
        while (true) {
            HdfsClientDemo client = new HdfsClientDemo ();
            String receive = server.receive ();//接收消息
            switch (receive) {
                case "upload": {
                    String pathName = server.receive ();
                    File file = new File ();

                    //System.out.println ( server.receive () );
                    String tmpStr;
                    List<Byte> bytes = new ArrayList<> ();
                    while (true) {
                        tmpStr = server.receive ();
                        if (tmpStr.equals ( "end" )) break;
                        else bytes.add ( Byte.valueOf ( tmpStr ) );
                        System.out.println ( tmpStr );
                    }
//                        System.out.println ("开始上传程序999");
                    file.setFileStream ( bytes );
                    int opt = client.upload ( file , pathName );
                    if (opt == 1) server.send ( "success" );
                    else if (opt == -1) server.send ( "already exist" );
                    else server.send ( "error" );
                    System.out.println ( "上传程序完成" );
//                        if ( == 1) {
//                            //成功接收给客户端发送success
//                            server.send("success");
//                        } else {
//                            //接收失败发送error
//                            server.send("error");
//                        }
                    break;
                }
                case "download": {
                    String pathName = server.receive ();
                    File file = client.download ( pathName );
                    server.send ( Tools.listToByte ( file.getFileStream () ) );
                    break;
                }
                case "newPath": {
                    String pathName = server.receive ();
                    client.newPath ( pathName );
                    server.send ( "success" );
                    break;
                }
                case "deleteFile":
                    String fileName = server.receive ();
                    if (client.deleteFile ( fileName ) == 1) {
                        //成功删除给客户端发送success
                        server.send ( "success" );
                    } else {
                        //删除失败发送error
                        server.send ( "error" );
                    }
                    break;
                case "getDirs":
                    String dir = server.receive ();
                    Folder folder = client.getDirs ( dir );
                    folder.tellServer ( server,client );
                    break;
                case "getInfo":
                    String fileDir = server.receive ();
                    client.listFiles(server,fileDir);
                    server.send ( "end" );
                    break;
                case "exit":
                    break;
            }
        }
    }
}
