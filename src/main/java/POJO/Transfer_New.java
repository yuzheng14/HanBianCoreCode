package POJO;

import java.util.ArrayList;
import java.util.HashMap;

public class Transfer_New {
    private static final HashMap<Character, char[]> consonantMap=new HashMap<>();

    private final String consonant = "ㅂㅈㄷㄱㅅㅁㄴㅇㄹㅎㅋㅌㅊㅍㅃㅉㄸㄲㅆ";
    private final String doubleLastSound = "ㄺㄳㄵㄶㄼㄽㄾㅀㄻㄿㅄ";
    private final String vowel = "ㅛㅕㅑㅐㅔㅒㅖㅗㅓㅏㅣㅜㅠㅡㅚㅙㅞㅢㅝㅟㅘ";

    private ArrayList<PunctionEntry> punctionList=new ArrayList<>();
    
    static{
        consonantMap.put('ㄱ', new char[]{'ㅋ', 'ㄲ', 'ㅇ', '1'});
        consonantMap.put('ㄷ', new char[]{'ㅌ', 'ㄸ', 'ㄴ', 'ㄹ'});
        consonantMap.put('ㅂ', new char[]{'ㅍ', 'ㅃ', 'ㅁ', '\0'});
        consonantMap.put('ㅅ', new char[]{'1', 'ㅆ', '\0', '\0'});
        consonantMap.put('ㅈ', new char[]{'ㅊ', 'ㅉ', '\0', '\0'});
        consonantMap.put('ㅎ', new char[]{'\0', '\0', '\0', '\0'});
    }
    
    /**
     * 对外开放，直接将韩语文字转换成发音
     * @param request
     * @return
     */
    public static String HangulTransfer(String request){
        return "";
    }

    /**
     * 将字符串中的数字转换为汉字数词
     * @param s
     * @return
     */
    private static String numberToHangul(String s){
        return "";
    }

    /**
     * 存储除韩字，空格以外的其他字符
     * @param s
     * @return
     */
    private static String storeOtherPunction(String s){
        return "";
    }

    /**
     * 将字母转换为发音
     * @param s
     * @return
     */
    private static String JamosToPronounciation(String s){
        return "";
    }


    public static String uiTransfer(String s){
        return null;
    }









    /**
     * 将punctionList中的内容添加回字符串
     * @param s
     * @return
     */
    private static String addOtherPunction(String s){
        return "";
    }

}