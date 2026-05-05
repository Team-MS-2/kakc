package me.desktop.KAKC;

public class CharType {
    public static boolean isAsciiString(String str) {
        int len = str.length();
        for (int i = 0; i < len; ++i) {
            char temp = str.charAt(i);
            if (' ' > temp || temp > '~') {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphaNum(String str) {
        int len = str.length();
        String str2 = str.toLowerCase();
        for (int i = 0; i < len; ++i) {
            char temp = str2.charAt(i);
            if (('a' > temp || temp > 'z') && ('0' > temp || temp > '9')) {
                return false;
            }
        }
        return true;
    }
}
