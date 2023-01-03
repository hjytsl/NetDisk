package com.example.net_disk.Socket;

import java.util.List;
import com.google.common.primitives.Bytes;


public class Tools {
    public static byte[] listToByte ( List<Byte> list ) {
        return Bytes.toArray ( list );
    }

    public static List<Byte> bytesToList ( byte[] bytes ) {
        return Bytes.asList ( bytes );
    }
}
