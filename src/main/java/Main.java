public class Main {
    public static void main(String args[]){


        byte[] data=HEX("ABCD");
        System.out.println("Hello Java");
    }


    static public byte[] HEX(String s) {
        int v = s.length() / 2;
        byte[] arr_b = new byte[v];
        int v1;
        for(v1 = 0; v1 < v; ++v1) {
            int v2 = v1 * 2;
            arr_b[v1] = Integer.valueOf(s.substring(v2, v2 + 2), 16).byteValue();
        }

        return arr_b;
    }
}
