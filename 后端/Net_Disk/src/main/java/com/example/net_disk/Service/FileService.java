package com.example.net_disk.Service;

import com.example.net_disk.Entity.File;

import java.util.List;

public interface FileService {
    void saveFile(File file);
    boolean modify(File file);
    File getFileById(Long id);
    List<File> getAllFileByUserId(Long userId);
    List<File> getAllFileBySearch(String filename, Long userId);
    void deleteByIds(List<Long> ids);
    File updateNameField(Long fileID, String new_name);
    List<File> getAllFile();
    void deleteById(Long id);
    void createfolder(String foldname, String path, Long belongs);
    List<File> getByPath(String path, Long belongs);
    List<File> getByType(String type, Long belongs);
}
