package com.yuzheng14.Tools;

import java.util.ArrayList;
import static com.yuzheng14.Tools.Korean.*;

public class Transfer {

    private static ArrayList<SymbolEntry> symbolList=new ArrayList<>();
    
    /**
     * 对外开放，直接将韩语文字转换成发音
     * @param request
     * @return
     */
    public static String HangulTransfer(String request){
        request=numbersToHangul(request);

        return "";
    }

    /**
     * 存储除韩字，空格以外的其他字符
     * @param s
     * @return
     */
    private static String storeOtherSymbol(String s){
        if (!symbolList.isEmpty()){
            symbolList=new ArrayList<>();
        }
        StringBuilder stringBuilder=new StringBuilder(s);
        for (int i = 0; i < stringBuilder.length(); i++) {
            char c = stringBuilder.charAt(i);
            if (!isHangul(c)&&c!=' '){
                symbolList.add(new SymbolEntry(i,c));
                stringBuilder.deleteCharAt(i);
                i--;
            }
        }
        return stringBuilder.toString();
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
     * 将symbolList中的内容添加回字符串
     * @param s
     * @return
     */
    private static String addOtherSymbol(String s){
        return "";
    }

    /**
     * 用于测试，不对外开放
     * @return
     */
    private static ArrayList<SymbolEntry> getSymbolList() {
        return symbolList;
    }
}