package com.yuzheng14.Tools;

import java.util.ArrayList;

import static com.yuzheng14.Tools.Korean.*;

/**
 * @author yuzheng14
 */
public class Transfer {
    private static ArrayList<SymbolEntry> symbolList = new ArrayList<>();

    /**
     * 对外开放，直接将韩语文字转换成发音
     *
     * @param request
     * @return
     */
    public static String hangulTransfer(String request) {
        return addOtherSymbol(jamoToHangul(jamosToPronunciation(hangulToJamo(uiTransfer(storeOtherSymbol(numbersToHangul(request)))))));
    }

    /**
     * 存储除韩字，空格以外的其他字符
     *
     * @param s
     * @return
     */
    private static String storeOtherSymbol(String s) {
        if (!symbolList.isEmpty()) {
            symbolList = new ArrayList<>();
        }
        StringBuilder stringBuilder = new StringBuilder(s);
        for (int i = 0; i < stringBuilder.length(); i++) {
            char c = stringBuilder.charAt(i);
            if (!isHangul(c) && c != ' ') {
                symbolList.add(new SymbolEntry(i, c));
                stringBuilder.deleteCharAt(i);
                i--;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将字母转换为发音
     *
     * @param s
     * @return
     */
    private static String jamosToPronunciation(String s) {
        int transfer = 1;
        int count = 0;
        StringBuilder builder = new StringBuilder(s);
        while (transfer != 0) {
            transfer = 0;
            for (int i = 0; i < builder.length(); i++) {
                //判断当前是否为子音
                if (builder.charAt(i) == ' ') {
                    continue;
                }
                if (!isVowel(builder.charAt(i))) {
                    //判断是否为最后一个
                    if (builder.length() - 1 > i) {
                        //判断下一个是否为子音
                        if (!isVowel(builder.charAt(i + 1))) {
                            //两个字母是子音的情况
                            if ((builder.charAt(i) == 'ㅇ' && builder.charAt(i + 1) == 'ㅎ') || (builder.charAt(i) == 'ㅎ' && builder.charAt(i + 1) == 'ㅇ') || (builder.charAt(i) == 'ㅇ' && builder.charAt(i + 1) == 'ㅇ') || (builder.charAt(i) == 'ㅎ' && builder.charAt(i + 1) == 'ㅎ')) {
                                continue;
                            } else if (builder.charAt(i + 1) == 'ㅇ') {
                                //连音化现象
                                builder.deleteCharAt(i + 1);
                                if (builder.charAt(i) == 'ㄷ' && builder.charAt(i + 1) == 'ㅣ') {
                                    builder.replace(i, i + 1, "ㅈ");
                                } else if (builder.charAt(i) == 'ㅌ' && builder.charAt(i + 1) == 'ㅣ') {
                                    builder.replace(i, i + 1, "ㅊ");
                                } else if (isDoubleFinalConsonant(builder.charAt(i))) {
                                    builder.replace(i, i + 1, splitDoubleFinalConsonant(builder.charAt(i)));
                                }
                                transfer++;
                            } else if (builder.charAt(i + 1) == 'ㅎ') {
                                //连音化且送气音话

                                if (isDoubleFinalConsonant(builder.charAt(i))) {
                                    builder.replace(i, i + 2, splitDoubleFinalConsonant(builder.charAt(i)));
                                    builder.replace(i + 1, i + 2, "" + toAspirated(builder.charAt(i + 1)));
                                }else if(builder.charAt(i)=='ㅅ'){
                                    builder.replace(i,i+2,"ㅌ");
                                } else {
                                    builder.replace(i, i + 2, "" + toAspirated(builder.charAt(i)));
                                }
                                if ((builder.charAt(i) == 'ㄷ' || builder.charAt(i) == 'ㅌ') && (builder.charAt(i + 1) == 'ㅣ' || builder.charAt(i + 2) == 'ㅣ')) {
                                    builder.replace(i, i + 1, "ㅊ");
                                }
                                transfer++;
                            } else if ((builder.charAt(i) == 'ㄱ' || builder.charAt(i) == 'ㅂ' || builder.charAt(i) == 'ㄷ') && (builder.charAt(i + 1) == 'ㄴ' || builder.charAt(i + 1) == 'ㅁ')) {
                                //鼻音化
                                builder.replace(i, i + 1, "" + toNasal(builder.charAt(i)));
                                transfer++;
                            } else if ((builder.charAt(i) == 'ㅂ' || builder.charAt(i) == 'ㄱ' || builder.charAt(i) == 'ㅁ' || builder.charAt(i) == 'ㅇ') && builder.charAt(i + 1) == 'ㄹ') {
                                //鼻音化
                                builder.replace(i + 1, i + 2, "ㄴ");
                                transfer++;
                            } else if ((builder.charAt(i) == 'ㄴ' && builder.charAt(i + 1) == 'ㄹ') || (builder.charAt(i) == 'ㄹ' && builder.charAt(i + 1) == 'ㄴ')) {
                                //流音化
                                builder.replace(i, i + 2, "ㄹㄹ");
                                transfer++;
                            } else if (((builder.charAt(i) == 'ㄱ' || builder.charAt(i) == 'ㅂ' || builder.charAt(i) == 'ㄷ') && (builder.charAt(i + 1) == 'ㅂ' || builder.charAt(i + 1) == 'ㅈ' || builder.charAt(i + 1) == 'ㄷ' || builder.charAt(i + 1) == 'ㄱ' || builder.charAt(i + 1) == 'ㅅ')) || ((builder.charAt(i) == 'ㄹ') && (builder.charAt(i + 1) == 'ㄷ' || builder.charAt(i + 1) == 'ㅅ' || builder.charAt(i + 1) == 'ㅈ'))) {
                                //网上查一下紧音化具体的规则
                                builder.replace(i + 1, i + 2, "" + toFortis(builder.charAt(i + 1)));
                                transfer++;
                            } else if (builder.charAt(i) == 'ㅎ') {
                                builder.replace(i, i + 2, "" + toAspirated(builder.charAt(i + 1)));
                                transfer++;
                            } else {
                                char c = finalConsonantToDelegateConsonant(builder.charAt(i));
                                if (c != builder.charAt(i)) {
                                    if (builder.charAt(i) == 'ㄺ' && builder.charAt(i + 1) == 'ㄱ') {
                                        builder.replace(i, i + 1, "" + 'ㄹ');
                                    } else {
                                        builder.replace(i, i + 1, "" + c);
                                    }
                                    transfer++;
                                }
                            }
                            i++;
                        }
                    } else {
                        //最后一个字母是子音
                        builder.replace(i, i + 1, "" + finalConsonantToDelegateConsonant(builder.charAt(i)));
//                        transfer++;
                    }
                }
            }
        }
//        System.out.println("[调用jamoToHangul()前]: "+builder.toString());
        return builder.toString();
    }

    /**
     * 의的音变
     *
     * @param s
     * @return
     */
    private static String uiTransfer(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '의' && ((i + 1) < s.length() && s.charAt(i + 1) == ' ') && (i - 1 > 0 && (s.charAt(i - 1) != '회' || s.charAt(i - 1) != '의'))) {
                s = new StringBuffer(s).replace(i, i + 1, "" + '에').toString();
            }
            if (i > 0 && s.charAt(i) == '의' && s.charAt(i - 1) != ' ') {
                s = new StringBuffer(s).replace(i, i + 1, "" + '이').toString();
            }
        }
        char[] uiWithConsonantChar = new char[]{'븨', '즤', '듸', '긔', '싀', '믜', '늬', '릐', '희', '킈', '틔', '츼', '픠', '쁴', '쯰', '띄', '끠', '씌'};
        char[] uiTransferredChar = new char[]{'비', '지', '디', '기', '시', '미', '니', '리', '히', '키', '티', '치', '피', '삐', '찌', '띠', '끼', '씨'};
        for (int i = 0; i < uiWithConsonantChar.length; i++) {
            if (s.contains("" + uiWithConsonantChar[i])) {
                s = s.replaceAll("" + uiWithConsonantChar[i], "" + uiTransferredChar[i]);
            }
        }
        return s;
    }

    /**
     * 将symbolList中的内容添加回字符串
     *
     * @param s
     * @return
     */
    private static String addOtherSymbol(String s) {
        StringBuilder builder = new StringBuilder(s);
        for (int i = symbolList.size() - 1; i >= 0; i--) {
            SymbolEntry entry = symbolList.get(i);
            builder.insert(entry.getOrder(), entry.getSymbol());
        }
        return builder.toString();
    }

    /**
     * 用于测试，不对外开放
     *
     * @return
     */
    private static ArrayList<SymbolEntry> getSymbolList() {
        return symbolList;
    }

    private static class SymbolEntry {
        private final int order;
        private final char symbol;

        public SymbolEntry(int order, char symbol) {
            this.order = order;
            this.symbol = symbol;
        }

        public int getOrder() {
            return order;
        }

        public char getSymbol() {
            return symbol;
        }
    }
}

