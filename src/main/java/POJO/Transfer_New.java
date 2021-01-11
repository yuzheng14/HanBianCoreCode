package POJO;

import java.util.ArrayList;
import java.util.HashMap;

public class Transfer_New {
    private static final HashMap<Character, char[]> consonantMap=new HashMap<>();

    private final String consonant = "ㅂㅈㄷㄱㅅㅁㄴㅇㄹㅎㅋㅌㅊㅍㅃㅉㄸㄲㅆ";
    private final String doubleLastSound = "ㄺㄳㄵㄶㄼㄽㄾㅀㄻㄿㅄ";
    private final String vowel = "ㅛㅕㅑㅐㅔㅒㅖㅗㅓㅏㅣㅜㅠㅡㅚㅙㅞㅢㅝㅟㅘ";

    private ArrayList<Integer> punctionList=new ArrayList<>();
    
    static{
        consonantMap.put('ㄱ', new char[]{'ㅋ', 'ㄲ', 'ㅇ', '1'});
        consonantMap.put('ㄷ', new char[]{'ㅌ', 'ㄸ', 'ㄴ', 'ㄹ'});
        consonantMap.put('ㅂ', new char[]{'ㅍ', 'ㅃ', 'ㅁ', '1'});
        consonantMap.put('ㅅ', new char[]{'1', 'ㅆ', '1', '1'});
        consonantMap.put('ㅈ', new char[]{'ㅊ', 'ㅉ', '1', '1'});
        consonantMap.put('ㅎ', new char[]{'1', '1', '1', '1'});
    }
    
    /**
     * 对外开放，直接将韩语文字转换成发音
     * @param request
     * @return
     */
    public static String HangulTransfer(String request){
        return "";
    }

    
}