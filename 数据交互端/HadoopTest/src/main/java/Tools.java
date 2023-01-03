import com.google.common.primitives.Bytes;

import java.util.List;

public class Tools {
    public static byte[] listToByte ( List<Byte> list ) {
        return Bytes.toArray ( list );
    }

    public static List<Byte> bytesToList ( byte[] bytes ) {
        return Bytes.asList ( bytes );
    }
}
