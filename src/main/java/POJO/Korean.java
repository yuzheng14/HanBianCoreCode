package POJO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Korean {

    private static final char HANGUL_OFFSET = '가';
    private static final char HANGUL_OUTSET = '힣';
    private static final char[] leadSet = new char[] { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ',
            'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };
    private static final char[] vowelSet = new char[] { 'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ',
            'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ' };
    private static final char[] tailSet = new char[] { '\0', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ',
            'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

    private static final HashMap<Character, Integer> leadMap = new HashMap<>();
    private static final HashMap<Character, Integer> vowelMap = new HashMap<>();
    private static final HashMap<Character, Integer> tailMap = new HashMap<>();

    static {
        leadMap.put('ㄱ', 0);
        leadMap.put('ㄲ', 1);
        leadMap.put('ㄴ', 2);
        leadMap.put('ㄷ', 3);
        leadMap.put('ㄸ', 4);
        leadMap.put('ㄹ', 5);
        leadMap.put('ㅁ', 6);
        leadMap.put('ㅂ', 7);
        leadMap.put('ㅃ', 8);
        leadMap.put('ㅅ', 9);
        leadMap.put('ㅆ', 10);
        leadMap.put('ㅇ', 11);
        leadMap.put('ㅈ', 12);
        leadMap.put('ㅉ', 13);
        leadMap.put('ㅊ', 14);
        leadMap.put('ㅋ', 15);
        leadMap.put('ㅌ', 16);
        leadMap.put('ㅍ', 17);
        leadMap.put('ㅎ', 18);
        vowelMap.put('ㅏ', 0);
        vowelMap.put('ㅐ', 1);
        vowelMap.put('ㅑ', 2);
        vowelMap.put('ㅒ', 3);
        vowelMap.put('ㅓ', 4);
        vowelMap.put('ㅔ', 5);
        vowelMap.put('ㅕ', 6);
        vowelMap.put('ㅖ', 7);
        vowelMap.put('ㅗ', 8);
        vowelMap.put('ㅘ', 9);
        vowelMap.put('ㅙ', 10);
        vowelMap.put('ㅚ', 11);
        vowelMap.put('ㅛ', 12);
        vowelMap.put('ㅜ', 13);
        vowelMap.put('ㅝ', 14);
        vowelMap.put('ㅞ', 15);
        vowelMap.put('ㅟ', 16);
        vowelMap.put('ㅠ', 17);
        vowelMap.put('ㅡ', 18);
        vowelMap.put('ㅢ', 19);
        vowelMap.put('ㅣ', 20);
        tailMap.put('\0', 0);
        tailMap.put('ㄱ', 1);
        tailMap.put('ㄲ', 2);
        tailMap.put('ㄳ', 3);
        tailMap.put('ㄴ', 4);
        tailMap.put('ㄵ', 5);
        tailMap.put('ㄶ', 6);
        tailMap.put('ㄷ', 7);
        tailMap.put('ㄹ', 8);
        tailMap.put('ㄺ', 9);
        tailMap.put('ㄻ', 10);
        tailMap.put('ㄼ', 11);
        tailMap.put('ㄽ', 12);
        tailMap.put('ㄾ', 13);
        tailMap.put('ㄿ', 14);
        tailMap.put('ㅀ', 15);
        tailMap.put('ㅁ', 16);
        tailMap.put('ㅂ', 17);
        tailMap.put('ㅄ', 18);
        tailMap.put('ㅅ', 19);
        tailMap.put('ㅆ', 20);
        tailMap.put('ㅇ', 21);
        tailMap.put('ㅈ', 22);
        tailMap.put('ㅊ', 23);
        tailMap.put('ㅋ', 24);
        tailMap.put('ㅌ', 25);
        tailMap.put('ㅍ', 26);
        tailMap.put('ㅎ', 27);
    }

    /**
     * 封装返回字符码点以提高可读性
     * 
     * @param c
     * @return
     */
    private static int getCodePoint(char c) {
        return (int) c;
    }

    /**
     * 将韩字转换成字母
     * 
     * @param originHangul
     * @return
     */
    public static String hangulToJamo(String originHangul) {
        StringBuffer stringBuffer = new StringBuffer();
        char[] originHanguls = originHangul.toCharArray();
        for (char c : originHanguls) {
            if (isHangul(c)) {

                int codePoint = getCodePoint(c);
                codePoint -= HANGUL_OFFSET;

                int tail = (codePoint % 28);
                int vowel = (((codePoint - tail) % 588) / 28);
                int lead = (codePoint / 588);

                stringBuffer.append(leadSet[lead]);
                stringBuffer.append(vowelSet[vowel]);
                stringBuffer.append(tailSet[tail]);

            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 判断是否为韩字
     * 
     * @param c
     * @return
     */
    private static boolean isHangul(char c) {
        return (c >= HANGUL_OFFSET) && (c <= HANGUL_OUTSET);
    }

    /**
     * 将字母合成为韩字
     * 
     * @param jamos
     * @return
     */
    public static String jamoToHangul(String jamos) {
        StringBuffer resultBuffer = new StringBuffer();
        int offset = 0;
        ArrayList<String> jamoList = new ArrayList<>();
        for (int i = 2; i < jamos.length(); i++) {
            if (i == jamos.length() - 1) {
                jamoList.add(jamos.substring(offset, jamos.length()));
                continue;
            }
            if (!isVowel(jamos.charAt(i)) && !isVowel(jamos.charAt(i - 1))) {
                jamoList.add(jamos.substring(offset, i));
                offset = i;
            }
        }
        for (String string : jamoList) {
            char c;
            if (string.length()==2) {
                int i =leadMap.get(string.charAt(0))*588+vowelMap.get(string.charAt(1)*28)+44032;
                c=i+'0';
            } else {
                
            }
            resultBuffer.append(c);
        }
        return "";
    }

    private static boolean isConsonantOrTail(char c) {
        HashSet<Character> consonantAndTailSet = new HashSet<>();
        for (char ch : leadSet) {
            consonantAndTailSet.add(ch);
        }
        for (char ch : tailSet) {
            consonantAndTailSet.add(ch);
        }
        StringBuffer consonantAndTailBuffer = new StringBuffer();
        for (Character character : consonantAndTailSet) {
            consonantAndTailBuffer.append(character);
        }
        String consonantAndTail = consonantAndTailBuffer.toString();
        return consonantAndTail.contains("" + c);
    }

    private static boolean isVowel(char c) {
        return String.valueOf(vowelSet).contains("" + c);
    }
}