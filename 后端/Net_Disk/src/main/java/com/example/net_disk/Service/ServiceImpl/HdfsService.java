package com.example.net_disk.Service.ServiceImpl;

import com.example.net_disk.Entity.File;
import com.example.net_disk.Config.SocketConfig;
import com.example.net_disk.Repository.FileRepository;
import com.example.net_disk.Service.FileService;
import com.example.net_disk.Socket.SocketConnection;
import com.example.net_disk.Socket.Tools;
import com.google.common.primitives.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HdfsService {
    private static SocketConnection connection;

    @Autowired
    private FileRepository fileRepository;

    static {
        try {
            connection = new SocketConnection ( SocketConfig.socketIp, SocketConfig.port );
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public int uploadFile(Long belongs, String path, MultipartFile file) throws IOException {
        //存入hdfs
        connection.send ( "upload" );
        connection.send ( "/"+belongs+""+path+"/"+file.getOriginalFilename());
        byte[] fileBytes = file.getBytes();
        connection.send ( fileBytes );
        String res = connection.receive ();

        //存入数据库
        switch (res) {
            case "already exist":
                return 2;
            case "error":
                return 0;
        }
        File recordFile = new File();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int begin = file.getOriginalFilename().indexOf(".");
        int last = file.getOriginalFilename().length();
        String type = file.getOriginalFilename().substring(begin+1, last);
        recordFile.setType(type);
        recordFile.setDownloadcount(0);
        recordFile.setName(file.getOriginalFilename());
        recordFile.setPath(path);
        recordFile.setBelongs(belongs);
        recordFile.setSize(file.getSize());
        recordFile.setLastmodify(df.format(new Date()));
        fileRepository.save(recordFile);
        return 1;
    }

    public boolean deleteFile(Long id) throws IOException {
        File file = fileRepository.getFileById(id);
        connection.send ( "deleteFile" );
        connection.send ( "/"+file.getBelongs()+""+file.getPath ()+"/"+file.getName () );
        fileRepository.deleteById(id);
        return connection.receive ().equals ( "success" );
    }

    public byte[] downloadFile(Long fileId) throws IOException {
        File file = fileRepository.getFileById(fileId);
        int count = file.getDownloadcount();
        file.setDownloadcount(count+1);
        fileRepository.save(file);
        System.out.println("/"+file.getBelongs ()+""+file.getPath ()+"/"+file.getName ());
        connection.send ( "download" );
        connection.send ( "/"+file.getBelongs ()+""+file.getPath ()+"/"+file.getName () );
        List<Byte> byteList = new ArrayList<> ();
        String tmpStr;
        while (true) {
            tmpStr = connection.receive();
            if (tmpStr.equals("end")) break;
            else byteList.add(Byte.valueOf(tmpStr));
        }
        byte[] bytes = Bytes.toArray(byteList);
        return bytes;
    }

    public boolean newFolder(String folder_name, String path, Long belongs) throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        File file = new File();
        file.setName(folder_name);
        file.setPath(path);
        file.setBelongs(belongs);
        file.setDownloadcount(0);
        file.setLastmodify(dateFormat.format(date));
        file.setSize(0);
        file.setType("folder");
        fileRepository.save(file);
        connection.send ( "newPath" );
        connection.send ( "/"+belongs+""+path );
        return connection.receive ().equals ( "success" );
    }

    public int updateFile(Long fileId,String new_name) throws IOException {
        //获取数据库记录
        File file = fileRepository.getFileById(fileId);
        //hdfs拉取文件
        System.out.println("/"+file.getBelongs ()+""+file.getPath ()+"/"+file.getName ());
        connection.send ( "download" );
        connection.send ( "/"+file.getBelongs ()+""+file.getPath ()+"/"+file.getName () );
        List<Byte> byteList = new ArrayList<> ();
        String tmpStr;
        while (true) {
            tmpStr = connection.receive();
            if (tmpStr.equals("end")) break;
            else byteList.add(Byte.valueOf(tmpStr));
        }
        byte[] bytes = Bytes.toArray(byteList);
        //删除原文件
        connection.send ( "deleteFile" );
        connection.send ( "/"+file.getBelongs()+""+file.getPath ()+"/"+file.getName () );
//        return connection.receive ().equals ( "success" );
        //更改文件名
        file.setName(new_name);
        fileRepository.save(file);
        //以新的名称上传文件
        //存入hdfs
        connection.send ( "upload" );
        connection.send ( "/"+file.getBelongs()+""+file.getPath()+"/"+file.getName());
        connection.send ( bytes );
        String res = connection.receive ();
        switch (res) {
            case "already exist":
                return 2;
            case "error":
                return 0;
        }
        return 1;
    }
}
