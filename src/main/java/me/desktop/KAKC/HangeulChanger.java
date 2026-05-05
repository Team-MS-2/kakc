package me.desktop.KAKC;

public class HangeulChanger {
    public static String translate(String input) {
        int len = input.length();
        String buffer = "";
        String ret = "";

        for (int i = 0; i < len; ++i) {
            char tempChar = input.charAt(i);
            if ('A' <= tempChar && tempChar <= 'Z' && "QWERTOP".indexOf(tempChar) < 0) {
                tempChar = (char) (tempChar - 'A' + 'a');
            }

            buffer = buffer + tempChar;
            int temp = needSeperating(buffer);
            if (temp != 0) {
                ret = ret + assemble(buffer.substring(0, temp));
                buffer = buffer.substring(temp);
            }
        }

        ret = ret + assemble(buffer);
        return ret;
    }

    static boolean stringCompare(String cp1, String cp2) {
        if (cp1.length() != cp2.length()) {
            return false;
        }
        int len = cp1.length();
        int i;
        for (i = 0; i < len && cp1.charAt(i) == cp2.charAt(i); ++i) {
        }
        return i == len;
    }

    static boolean isJaum(char input) {
        String value = "rRseEfaqQtTdwWczxvg";
        int len = value.length();
        for (int i = 0; i < len; ++i) {
            if (value.charAt(i) == input) {
                return true;
            }
        }
        return false;
    }

    static boolean isMoum(char input) {
        String value = "koiOjpuPhynbml";
        int len = value.length();
        for (int i = 0; i < len; ++i) {
            if (value.charAt(i) == input) {
                return true;
            }
        }
        return false;
    }

    static boolean isAssemblableJaum(String input) {
        String ok = "rtswsgfrfafqftfxfvfgqt";
        for (int i = 0; i < 11; ++i) {
            if (stringCompare(input, ok.substring(i * 2, (i + 1) * 2))) {
                return true;
            }
        }
        return false;
    }

    static boolean isAssemblableMoum(String input) {
        String ok = "hkhlhonjnlnpml";
        for (int i = 0; i < 7; ++i) {
            if (stringCompare(input, ok.substring(i * 2, (i + 1) * 2))) {
                return true;
            }
        }
        return false;
    }

    static int needSeperating(String input) {
        int len = input.length();
        int last = -1;
        char[] arr = new char[]{'', '', '', '', '', '', ''};

        for (int i = 0; i < len; ++i) {
            if (isJaum(input.charAt(i))) {
                arr[i] = 0;
            } else {
                if (!isMoum(input.charAt(i))) {
                    if (i == 0) {
                        return 1;
                    }
                    return i;
                }
                arr[i] = 1;
            }
        }

        for (int i = 0; i < len; ++i) {
            if (last == -1) {
                last = arr[i];
            } else {
                if (last == 0 && arr[i] == 0) {
                    return i;
                }

                if (last == 0 && arr[i] == 1) {
                    arr[i] = 3;
                } else if (last == 1 && arr[i] == 0) {
                    return i;
                } else if (last == 1 && arr[i] == 1) {
                    if (!isAssemblableMoum(input.substring(i - 1, i + 1))) {
                        return i;
                    }
                } else if (last == 2 && arr[i] == 0) {
                    if (!isAssemblableJaum(input.substring(i - 1, i + 1))) {
                        return i;
                    }
                    arr[i] = 4;
                } else if (last == 2 && arr[i] == 1) {
                    return i - 1;
                } else if (last == 3 && arr[i] == 0) {
                    arr[i] = 2;
                } else if (last == 3 && arr[i] == 1) {
                    if (!isAssemblableMoum(input.substring(i - 1, i + 1))) {
                        return i;
                    }
                    arr[i] = 3;
                } else if (last == 4 && arr[i] == 0) {
                    return i;
                } else if (last == 4 && arr[i] == 1) {
                    return i - 1;
                }

                last = arr[i];
            }
        }

        return 0;
    }

    static int JaMoValue(String input, int value) {
        if (input.length() == 1) {
            String origin = "";
            if (value == 0) {
                origin = "rRseEfaqQtTdwWczxvg";
            } else if (value == 1) {
                origin = "koiOjpuPh///yn///bm/l";
            } else if (value == 2) {
                origin = "/rR/s//ef///////aq/tTdwczxvg";
            }

            int len = origin.length();
            for (int i = 0; i < len; ++i) {
                if (origin.charAt(i) == input.charAt(0)) {
                    return i;
                }
            }
        } else if (input.length() == 2) {
            String origin = "";
            if (value == 1) {
                origin = "//////////////////hkhohl////njnpnl////ml";
            } else if (value == 2) {
                origin = "//////rt//swsg////frfafqftfxfvfg////qt";
            }

            int len = origin.length();
            for (int i = 0; i < len; ++i) {
                if (stringCompare(origin.substring(i * 2, i * 2 + 2), input)) {
                    return i;
                }
            }
            return 0;
        }

        return -1;
    }

    static String assemble(String input) {
        if (input.length() == 1) {
            String origin = "rRseEfaqQtTdwWczxvgkijuhynbmloOpP";
            String change = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎㅏㅑㅓㅕㅗㅛㅜㅠㅡㅣㅐㅒㅔㅖ";
            int len = origin.length();
            for (int i = 0; i < len; ++i) {
                if (origin.charAt(i) == input.charAt(0)) {
                    return change.substring(i, i + 1);
                }
            }
            return input;
        } else if (input.length() == 2 && isAssemblableMoum(input)) {
            String origin = "hkhlhonjnlnpml";
            String change = "ㅘㅚㅙㅝㅟㅞㅢ";
            int len = origin.length() / 2;
            for (int i = 0; i < len; ++i) {
                if (stringCompare(input, origin.substring(i * 2, i * 2 + 2))) {
                    return change.substring(i, i + 1);
                }
            }
            return input + "_";
        } else {
            int cho = JaMoValue(input.substring(0, 1), 0);
            int unicode = 44032;
            String ret = "";
            String temp = input.substring(1);

            int i;
            for (i = 0; i < temp.length() && !isJaum(temp.charAt(i)); ++i) {
            }

            int jung = JaMoValue(temp.substring(0, i), 1);
            int jong;
            if (i == temp.length()) {
                jong = 0;
            } else {
                jong = JaMoValue(temp.substring(i), 2);
            }

            unicode += cho * 588 + jung * 28 + jong;
            ret = ret + (char) unicode;
            return ret;
        }
    }
}
