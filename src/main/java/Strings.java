

public class Strings
{
    public static String ff(String format, Object... args)
    {
        return String.format(format, args);
    }

    public static String ltrim(String s, char c)
    {
        int beg = 0;
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) != c) {
                break;
            }
            beg++;
        }
        return s.substring(beg);
    }

    public static String[] splitLines(String s, boolean doNotReturnFinalEmptyLine)
    {
        return s.split("(\r\n|\r|\n)", doNotReturnFinalEmptyLine ? 0 : -1);
    }

    public static String[] splitLines(String s)
    {
        return splitLines(s, false);
    }
}
