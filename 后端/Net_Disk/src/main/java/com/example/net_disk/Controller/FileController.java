package com.example.net_disk.Controller;

import com.example.net_disk.Entity.File;
import com.example.net_disk.Entity.Folder;
import com.example.net_disk.Entity.Result;
import com.example.net_disk.Service.FileService;
import com.example.net_disk.Service.ServiceImpl.HdfsService;
import com.example.net_disk.Socket.HdfsFile;
import com.example.net_disk.Socket.SocketConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    //private SocketConnection connection = new SocketConnection("139.155.250.85", 9999);//服务器ip和端口
    @Autowired
    private FileService fileService;

    @Autowired
    private HdfsService hdfsService;

    public FileController() throws IOException {
    }

    //按照路径获取全部文件
    @PostMapping("/getpath")
    public Result getPath(String path, Long belongs){
        System.out.println(path + "" + belongs);
        return new Result(true,fileService.getByPath(path, belongs));
    }

    //按照类型获取全部文件
    @PostMapping("/gettype")
    public Result getType(String type, Long belongs){
        System.out.println(type + "" + belongs);
        return new Result(true,fileService.getByType(type, belongs));
    }

    //用id获取文件
    @GetMapping("/{id}")
    public Result getFileById(@PathVariable Long id){
        return new Result(true,fileService.getFileById(id));
    }

//    //保存文件
//    @PostMapping
//    public Result updateFile(@RequestBody File file){
//        fileService.saveFile(file);
//        return new Result(true,"保存文件成功");
//    }

    //更新文件
    @PutMapping
    public Result update(Long fileID,String new_name) throws IOException {
        hdfsService.updateFile(fileID, new_name);
        return new Result(true,"更新文件成功");
    }

    //用id删除文件
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Long id) throws IOException {
        hdfsService.deleteFile(id);
        return new Result(true,"删除文件成功");
    }

    //创建目录
    @PostMapping("/newfolder")
    public Result newfolder(String folder_name, String path, Long belongs) throws IOException {
        hdfsService.newFolder(folder_name,path,belongs);
        return new Result(true,"新目录创建成功");
    }

    //搜索用户文件
    @GetMapping("/users/{id}")
    public Result getAllFillOfPerson(@PathVariable Long id){
        return new Result(true,fileService.getAllFileByUserId(id));
    }

    //条件搜索
    @PostMapping("/search")
    public Result SearchFile(String filename, Long userId){
        return new Result(true,fileService.getAllFileBySearch(filename,userId));
    }

//    //批量删除
//    @PostMapping("/deleteIds")
//    public Result deleteByIds(@RequestBody List<Long> ids){
//        fileService.deleteByIds(ids);
//        return new Result(true,"删除成功");
//    }

    //文件上传
    @PostMapping("/upload")
    public int upload(Long belongs, String path,@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(belongs + " " + path + " " + file.getOriginalFilename());
        return hdfsService.uploadFile(belongs, path, file);

//        if(file.getContentType().equals("image/png")){
//            connection.send("fakecode");
//            connection.send("/testDir/222.png");
//        }else{
//
//            byte[] send2;
//            connection.send("upload");
//            connection.send("/testDir/"+file.getOriginalFilename());
//            send2 = file.getBytes();
//
//            connection.send(send2);
//
//            System.out.println(connection.receive());
//        }
    }

    //文件下载
    @PostMapping("/download")
    public byte[] download(Long fileId) throws IOException {
        System.out.println(fileId);
        return hdfsService.downloadFile(fileId);
//        connection.send("download");
//        connection.send(file.getPath());
//        HdfsFile hdfsFile = new HdfsFile();
//        String tmpStr;
//        List<Byte> bytes = new ArrayList<>();
//        while (true) {
//            tmpStr = connection.receive();
//            if (tmpStr.equals("end")) break;
//            else bytes.add(Byte.valueOf(tmpStr));
//        }
//
//        hdfsFile.setFileStream(bytes);
//        hdfsFile.writeFileByAddress("C:\\Users\\1339289951\\Desktop\\" + file.getName());
    }

//    //获取文件目录
//    @PostMapping("/getDir")
//    public String getDir(@RequestBody File file) throws IOException {
////        Folder folder = new Folder();
////
////        connection.send("getDirs");
////        connection.send("/Floatiy");
////        Integer length = Integer.parseInt(connection.receive());
////        for (int i = 0; i < length; i++) {
////            String isFile = connection.receive();
////            if(isFile.equals("File")){
////                folder.isFile.add(true);
////            }
////            String path = connection.receive();
////            folder.fileList.add(path);
////        }
////
//        return "success";
//    }

//    //删除文件
//    @PostMapping("/path")
//    public String deleteFile(@RequestBody File file) throws IOException {
//        connection.send("deleteFile");
//        connection.send(file.getPath());
//        return connection.receive();
//    }
}
