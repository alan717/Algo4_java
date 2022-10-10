import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

public class Hash {
    public static int calculateCRC32(byte[] data)
    {
        CRC32 crc = new CRC32();
        crc.update(data);
        return (int)(crc.getValue() & 0xFFFFFFFFFFFFFFFF);
    }

    public static byte[] calculateMD5(byte[] data)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(data);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static byte[] calculateSHA1(byte[] data)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return md.digest(data);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static byte[] calculateSHA256(byte[] data)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(data);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}
