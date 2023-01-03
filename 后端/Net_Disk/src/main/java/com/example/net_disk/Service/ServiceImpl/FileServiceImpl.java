package com.example.net_disk.Service.ServiceImpl;

import com.example.net_disk.Entity.File;
import com.example.net_disk.Entity.User;
import com.example.net_disk.Repository.FileRepository;
import com.example.net_disk.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Override
    public void saveFile(File file) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        file.setLastmodify(df.format(new Date()));
        fileRepository.save(file);
    }

    @Override
    public boolean modify(File file) {
        return false;
    }

    @Override
    public File getFileById(Long id) {
        return fileRepository.getFileById(id);
    }

    @Override
    public List<File> getAllFileByUserId(Long userId) {
        return fileRepository.getAllByBelongs(userId);
    }

    @Override
    public List<File> getAllFileBySearch(String filename, Long userId) {
        return fileRepository.findAllByNameContainingAndBelongs(filename,userId);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        for(Long id : ids){
            fileRepository.deleteById(id);
        }
    }

    @Override
    public File updateNameField(Long fileID, String new_name) {
        File file = fileRepository.getFileById(fileID);
        file.setName(new_name);
        fileRepository.save(file);
        return file;
    }

    @Override
    public List<File> getAllFile() {
        return fileRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        fileRepository.deleteById(id);
    }

    @Override
    public void createfolder(String foldname, String path, Long belongs) {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        File file = new File();
        file.setName(foldname);
        file.setPath(path);
        file.setBelongs(belongs);
        file.setDownloadcount(0);
        file.setLastmodify(dateFormat.format(date));
        file.setSize(0);
        file.setType("folder");
        fileRepository.save(file);
    }

    @Override
    public List<File> getByPath(String path, Long belongs) {
        return fileRepository.findAllByPathAndAndBelongs(path,belongs);
    }

    @Override
    public List<File> getByType(String type, Long belongs) {
        switch (type){
            case "picture":
                List<File> picList = fileRepository.findAllByTypeAndBelongs("png", belongs);
                picList.addAll(fileRepository.findAllByTypeAndBelongs("jpg", belongs));
                return picList;
            case "video":
                return fileRepository.findAllByTypeAndBelongs("mp4",belongs);
            case "music":
                return fileRepository.findAllByTypeAndBelongs("mp3",belongs);
            case "document":
                List<File> docList = fileRepository.findAllByTypeAndBelongs("doc", belongs);
                docList.addAll(fileRepository.findAllByTypeAndBelongs("txt", belongs));
                docList.addAll(fileRepository.findAllByTypeAndBelongs("docx", belongs));
                return docList;
            default:
                return null;
        }
    }
}
