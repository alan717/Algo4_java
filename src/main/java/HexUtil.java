import java.lang.Exception;
import java.util.Base64;

public class HexUtil {
    private static final char[] digits =  new char[]{'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};
    public static final byte[] emptybytes =  new byte[0];

    public static String byte2HexStr(byte arg4) {
        char[] v0 = new char[2];
        v0[1] = HexUtil.digits[arg4 & 15];
        v0[0] = HexUtil.digits[((byte)(arg4 >>> 4)) & 15];
        return new String(v0);
    }

    // String Decryptor: 1 succeeded, 0 failed
    public static String bytes2HexStr(byte[] arg8) {
        if(arg8 != null && arg8.length != 0) {
            char[] v0 = new char[arg8.length * 2];
            int v2;
            for(v2 = 0; v2 < arg8.length; ++v2) {
                byte v3 = arg8[v2];
                int v4 = v2 * 2;
                v0[v4 + 1] = HexUtil.digits[v3 & 15];
                v0[v4] = HexUtil.digits[((byte)(v3 >>> 4)) & 15];
            }

            return new String(v0);
        }

        return null;
    }

    public static byte char2Byte(char arg2) {
        if(arg2 >= 0x30 && arg2 <= 57) {
            return (byte)(arg2 - 0x30);
        }

        if(arg2 >= 97 && arg2 <= 102) {
            return (byte)(arg2 - 87);
        }

        return arg2 >= 65 && arg2 <= 70 ? ((byte)(arg2 - 55)) : 0;
    }

    public static byte hexStr2Byte(String arg3) {
        return arg3 == null || arg3.length() != 1 ? 0 : HexUtil.char2Byte(((char)arg3.charAt(0)));
    }

    public static byte[] hexStr2Bytes(String arg5) {
        if(arg5 != null && !arg5.equals("")) {
            int v0 = arg5.length() / 2;
            byte[] v1 = new byte[v0];
            int v2;
            for(v2 = 0; v2 < v0; ++v2) {
                int v3 = v2 * 2;
                v1[v2] = (byte)(HexUtil.char2Byte(((char)arg5.charAt(v3))) * 16 + HexUtil.char2Byte(((char)arg5.charAt(v3 + 1))));
            }

            return v1;
        }

        return HexUtil.emptybytes;
    }




    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("输入qq xor结果:.exe xor_data");
                return;
            }

            String encodedString=args[0];
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);

            byte[] dres= a(decodedBytes,"JCPTZXEZ");
            String decodedString = new String(dres);
            System.out.println(decodedString);
        }
        catch(Exception v1) {
            v1.printStackTrace();
        }
    }
    public static byte[] a(byte[] arg6, String arg7) {
        int v1;
        if(arg6 != null) {
            try {
                char[] v2 = arg7.toCharArray();
                byte[] v0_1 = new byte[arg6.length];
                v1 = 0;
                while(true) {
                    label_5:
                    if(v1 >= arg6.length) {
                        return v0_1;
                    }

                    v0_1[v1] = (byte)(arg6[v1] ^ v2[v1 % v2.length]);
                    ++v1;
                }
            } catch(Throwable v0) {
                return arg6;
            }


        }

        return arg6;
    }




    public void parse_loginK(){


    }



    
}
