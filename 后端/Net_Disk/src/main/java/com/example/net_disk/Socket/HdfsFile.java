package com.example.net_disk.Socket;

import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class HdfsFile {
    private List<Byte> fileStream;
    private String name;
    public HdfsFile() {
        fileStream = new ArrayList<Byte>();
        name = null;
    }
    public void readFileByAddress(String address) throws IOException {
        FileInputStream fis = new FileInputStream(address);
        DataInputStream dis = new DataInputStream ( fis );
        dis.read (Tools.listToByte (this.fileStream) );
        dis.close();
        fis.close ();
    }
    public void writeFileByAddress(String address) throws IOException {
        FileOutputStream fos = new FileOutputStream(address);
        DataOutputStream dos = new DataOutputStream ( fos );
        dos.write (Tools.listToByte (this.fileStream));
        dos.close();
        fos.close ();
    }
}
