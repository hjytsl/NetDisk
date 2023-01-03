package com.example.net_disk.Entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Folder {
    String path;
    String name;

    public List<String> fileList;
    public List<Boolean> isFile;
    public Folder() {
        fileList = new ArrayList<>();
        isFile = new ArrayList<> ();
    }
    public void add(String filename, boolean isFile2) {
        fileList.add ( filename );
        isFile.add ( isFile2 );
    }

}
