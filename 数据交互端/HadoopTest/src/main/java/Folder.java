import Server.ServerSocketConnection;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Folder {
    String path;
    String name;
    List<String> fileList;
    List<Boolean> isFile;
    public Folder() {
        fileList = new ArrayList<> ();
        isFile = new ArrayList<> ();
    }
    public void add(String filename, boolean isFile2) {
        fileList.add ( filename );
        isFile.add ( isFile2 );
    }
    public void tellServer ( ServerSocketConnection server, HdfsClientDemo hdfs ) throws IOException {
        server.send ( String.valueOf ( fileList.size () ) );
        for(int i = 0;i < fileList.size ();i++) {
//            if(isFile.get ( i )) System.out.println ("File");
//            else System.out.println ("Dir");
//            System.out.println (fileList.get ( i ));
            if(isFile.get ( i )) server.send ("File");
            else server.send ("Dir");
            server.send (fileList.get ( i ));
//            if(isFile.get ( i )) hdfs.listFiles ( server, fileList.get ( i ));
        }
    }
}
