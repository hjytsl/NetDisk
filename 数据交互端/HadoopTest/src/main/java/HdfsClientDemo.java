import Server.ServerSocketConnection;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HdfsClientDemo {
    private FileSystem fs = null;

    //构造函数中进行初始化
    public HdfsClientDemo ( ) throws Exception {
        Configuration conf = new Configuration ();
        conf.set ( "fs.defaultFS" , "hdfs://47.113.195.217:9000" );
        conf.set ( "dfs.client.use.datanode.hostname" , "true" );
        conf.set ( "dfs.replication" , "1" );
        this.fs = FileSystem.get ( conf );
    }

    //停止hadoop
    public void stop ( ) throws IOException {
        this.fs.close ();
    }

    //上传文件1
    public int upload ( String localDir , String hdfsDir ) throws URISyntaxException, IOException {
        Path localPath = new Path ( localDir );
        Path hdfsPath = new Path ( hdfsDir );
        if (this.fs.exists ( hdfsPath )) {
            System.out.println ( "文件已经存在，创建失败" );
            return -1;
        }
        fs.copyFromLocalFile ( localPath , hdfsPath );
        System.out.println ( "文件上传成功" );
        return 1;
    }

    //上传文件2
    public int upload ( File file , String hdfsDir ) throws URISyntaxException, IOException {
        Path hdfsPath = new Path ( hdfsDir );
        if (this.fs.exists ( hdfsPath )) {
            System.out.println ( "文件已经存在，创建失败" );
            return -1;
        }
        FSDataOutputStream outputStream = this.fs.create ( new Path ( hdfsDir ) );
        byte[] buff = Tools.listToByte ( file.getFileStream () );
        outputStream.write ( buff , 0 , buff.length );
        outputStream.close ();
        System.out.println ( "文件创建成功，并且已经写入数据" );
        outputStream.close ();
        return 1;
    }

    //新建文件夹
    public void newPath ( String dirPath ) throws IOException {
        Path hdfsPath = new Path ( dirPath );
        if (fs.mkdirs ( hdfsPath )) {
            System.out.println ( "Directory " + dirPath + " has been created successfully!" );
        }
    }

    //删除文件 -1失败 1成功
    public int deleteFile ( String filename ) throws IOException {
        if (!fs.exists ( new Path ( filename ) )) {
            System.out.println ( "文件不存在，删除失败" );
            return -1;
        }
        boolean result = fs.delete ( new Path ( filename ) , true );
        if (result) {
            return 1;
        } else {
            return -1;
        }
    }

    //获取文件
    //从hadoop上下载文件到本地
    public File download ( String hdfsPath ) throws IOException {

        //获取本地文件路径
        String[] filenames = hdfsPath.split ( "/" );
        String filename = filenames[filenames.length - 1];

        File file = new File ();
        file.setName ( filename );
        FSDataInputStream inputStream = fs.open ( new Path ( hdfsPath ) );
        List<Byte> bytes = new ArrayList<> ();
        byte[] buff = new byte[128];
        int len = 0;
        while (( len = inputStream.read ( buff , 0 , 128 ) ) != -1) {
            bytes.addAll ( Tools.bytesToList ( buff ) );
        }
        file.setFileStream ( bytes );
        return file;
    }

    //获取目录
    public Folder getDirs ( String dir ) throws IOException {
        Folder retFolder = new Folder ();
        retFolder.path = dir;
        String[] s = dir.split ( "/" );
        if (s.length == 0) retFolder.name = "home";
        else retFolder.name = s[s.length - 1];
        try {
            FileStatus[] status = fs.listStatus ( new Path ( dir ) );
            for (FileStatus fileStatus : status) {
                if (fileStatus.isDirectory ()) {
                    retFolder.add ( fileStatus.getPath ().toString () , false );
//                    System.out.println ( "d :" + fileStatus.getPath ().toString () );
                } else {
                    retFolder.add ( fileStatus.getPath ().toString () , true );
//                    System.out.println ( "f :" + fileStatus.getPath ().toString () );
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println ( "文件未找到！" );
        }
        return retFolder;

    }

    /**
     * 查看文件的详情
     * 思路：
     * 1、配置信息Configuration
     * 2、FileSystem
     * 3、拿到文件，调用fileSystem提供的获取文件信息的方法listFiles
     * 4、关闭系统资源
     */
    public void listFiles( ServerSocketConnection server, String path ) throws IOException {
        //3、调用方法查看文件的详情
        RemoteIterator<LocatedFileStatus> iterator =
                fs.listFiles(new Path(path), true);

        while (iterator.hasNext()){
            LocatedFileStatus locatedFileStatus = iterator.next();
//            System.out.println("blockSize:" + locatedFileStatus.getBlockSize());
//            System.out.println("owner:" + locatedFileStatus.getOwner());
//            System.out.println("group:" + locatedFileStatus.getGroup());
//            System.out.println("更新时间:" + locatedFileStatus.getModificationTime());
//            System.out.println("副本数:" + locatedFileStatus.getReplication());
//            BlockLocation[] blockLocations = locatedFileStatus.getBlockLocations();
//            for (BlockLocation block: blockLocations) {
//                System.out.println("len : " + block.getLength());
//                String[] blockNames = block.getNames();
//                for (String name: blockNames) {
//                    System.out.println("block name : " + name);
//                }
//            }
//            System.out.println("name:" + locatedFileStatus.getPath ());
//            System.out.println("blockSize:" + locatedFileStatus.getBlockSize());
//            System.out.println("更新时间:" + locatedFileStatus.getModificationTime());
//            System.out.println("副本数:" + locatedFileStatus.getReplication());
//            server.send ( String.valueOf ( locatedFileStatus.getBlockSize() ) );//块大小
            server.send ( String.valueOf ( locatedFileStatus.getBlockSize() ) );//块大小
            server.send ( String.valueOf ( locatedFileStatus.getModificationTime() ));//更新时间
            server.send ( String.valueOf ( locatedFileStatus.getReplication() ) );//副本数
        }

    }

}
