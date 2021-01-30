package POJO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Transfer_Old {
    //TODO 添加文件时间戳以及异常处理以防请求过多
    private final HashMap<Character, char[]> consonantMap;
    private final String consonant = "ㅂㅈㄷㄱㅅㅁㄴㅇㄹㅎㅋㅌㅊㅍㅃㅉㄸㄲㅆ";
    private final String doubleLastSound = "ㄺㄳㄵㄶㄼㄽㄾㅀㄻㄿㅄ";
    private final String vowel = "ㅛㅕㅑㅐㅔㅒㅖㅗㅓㅏㅣㅜㅠㅡㅚㅙㅞㅢㅝㅟㅘ";

    private final ArrayList<Integer> spaceList = new ArrayList<>();

    private final String filePath;

    /**
     * 构造方法初始化子音Map
     */
    public Transfer_Old() {
        consonantMap = new HashMap<>();
        consonantMap.put('ㄱ', new char[]{'ㅋ', 'ㄲ', 'ㅇ', '1'});
        consonantMap.put('ㄷ', new char[]{'ㅌ', 'ㄸ', 'ㄴ', 'ㄹ'});
        consonantMap.put('ㅂ', new char[]{'ㅍ', 'ㅃ', 'ㅁ', '1'});
        consonantMap.put('ㅅ', new char[]{'1', 'ㅆ', '1', '1'});
        consonantMap.put('ㅈ', new char[]{'ㅊ', 'ㅉ', '1', '1'});
        consonantMap.put('ㅎ', new char[]{'1', '1', '1', '1'});
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            filePath = "F:/Programming/acode/HanBianCoreCode/lib";
        } else {
            filePath = "/home/wwwroot/ftptest/WEBAPP/web/WEB-INF/lib";
        }
    }

    public String HangulTransfer(String request) {
        request = deletePunctuation(request);
        addSpaceList(request);
        char[] jamo = stringToCharArray(request);
        return synthesis(transferJamos(jamo, mark(jamo)));
    }

    /**
     * 将韩语转换成字母并拆分成char数组
     *
     * @param request
     * @return char[]
     */
    private char[] stringToCharArray(String request) {
        request = splitString(request);
        return request.toCharArray();
    }

    /**
     * 通过cmd调用python拆分韩语句子成字母，并存储到一个txt文件
     *
     * @param request
     * @return String
     */
    //TODO 增加多线程或者文件命名方式以防止同时访问时出现错误
    //TODO 의的音变
    private String splitString(String request) {
        Process process;
        String result = null;
        try {
            String query = "python " + filePath + "/splitString.py " + request;
            process = Runtime.getRuntime().exec(query);
            process.getInputStream();
            process.getInputStream().close();
            process.getOutputStream().close();
            process.getErrorStream().close();
            process.waitFor();
            process.destroy();
            BufferedReader bf = new BufferedReader(new FileReader(new File(filePath + "/result.txt")));
            result = bf.readLine().trim();
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过一个数组来标定对应字符为子音还是母音还是空格
     * 0为子音，1为母音，2为空格
     *
     * @param jamos
     * @return int[]
     */
    private int[] mark(char[] jamos) {
        int[] mark = new int[jamos.length];
        for (int i = 0; i < jamos.length; i++) {
            if (consonant.contains("" + jamos[i]) || doubleLastSound.contains("" + jamos[i])) {
                mark[i] = 0;
            } else if (vowel.contains("" + jamos[i])) {
                mark[i] = 1;
            } else {
                mark[i] = 2;
            }
        }
        return mark;
    }

    /**
     * 将字母字符组转换成音变后的字母
     *
     * @param jamos
     * @param mark
     * @return
     */
    private String transferJamos(char[] jamos, int[] mark) {
        int transfer = 1;
        int count = 0;
        while (transfer != 0) {
            transfer = 0;
            jamos = String.valueOf(jamos).replaceAll("1", "").toCharArray();
            mark = mark(jamos);
            for (int i = 0; i < jamos.length; i++) {
                //判断当前是否为子音
                if (mark[i] == 0) {
                    //判断是否为最后一个
                    if (jamos.length - 1 > i) {
                        //判断下一个是否为子音
                        if (mark[i + 1] == 0) {
                            //两个字母是子音的情况
                            if (jamos[i] == 'ㅇ' || jamos[i + 1] == 'ㅎ') {
                                continue;
                            } else if (jamos[i + 1] == 'ㅇ') {
                                //连音化现象
                                jamos[i + 1] = '1';
                                if (jamos[i] == 'ㄷ' && jamos[i + 2] == 'ㅣ') {
                                    jamos[i] = 'ㅈ';
                                } else if (jamos[i] == 'ㅌ' && jamos[i + 2] == 'ㅣ') {
                                    jamos[i] = 'ㅊ';
                                } else if (doubleLastSound.contains("" + jamos[i])) {
                                    char[] current = splitDoubleLastSound(jamos[i]);
                                    jamos[i] = current[0];
                                    jamos[i + 1] = current[1];
                                }
                                transfer++;
                            } else if (jamos[i + 1] == 'ㅎ') {
                                //连音化且送气音话

                                if (doubleLastSound.contains("" + jamos[i])) {
                                    char[] current = splitDoubleLastSound(jamos[i]);
                                    jamos[i] = current[0];
                                    jamos[i + 1] = toAspiratedSound(current[1]);
                                } else {
                                    jamos[i] = toAspiratedSound(jamos[i]);
                                    jamos[i + 1] = '1';
                                }
                                if (jamos[i + 2] == 'ㅣ') {
                                    jamos[i] = 'ㅊ';
                                }

                                transfer++;
                            } else if ((jamos[i] == 'ㄱ' || jamos[i] == 'ㅂ' || jamos[i] == 'ㄷ') && (jamos[i + 1] == 'ㄴ' || jamos[i + 1] == 'ㅁ')) {
                                jamos[i] = toNasal(jamos[i]);
                                transfer++;
                            } else if ((jamos[i] == 'ㅁ' || jamos[i] == 'ㅇ') && jamos[i + 1] == 'ㄹ') {
                                jamos[i + 1] = 'ㄴ';
                                transfer++;
                            } else if ((jamos[i] == 'ㄴ' && jamos[i + 1] == 'ㄹ') || (jamos[i] == 'ㄹ' && jamos[i + 1] == 'ㄴ')) {
                                jamos[i] = 'ㄹ';
                                jamos[i + 1] = 'ㄹ';
                                transfer++;
                            } else if (((jamos[i] == 'ㄱ' || jamos[i] == 'ㅂ' || jamos[i] == 'ㄷ') && (jamos[i + 1] == 'ㅂ' || jamos[i + 1] == 'ㅈ' || jamos[i + 1] == 'ㄷ' || jamos[i + 1] == 'ㄱ' || jamos[i + 1] == 'ㅅ')) || ((jamos[i] == 'ㄹ') && (jamos[i + 1] == 'ㄷ' || jamos[i + 1] == 'ㅅ' || jamos[i + 1] == 'ㅈ'))) {
                                //网上查一下紧音化具体的规则
                                jamos[i + 1] = toTightSound(jamos[i + 1]);
                                transfer++;
                            } else if ((jamos[i] == 'ㅂ' || jamos[i] == 'ㄱ') && (jamos[i + 1] == 'ㄹ')) {
                                jamos[i + 1] = 'ㄴ';
                            } else if (jamos[i] == 'ㅎ') {
                                jamos[i] = '1';
                                jamos[i + 1] = toAspiratedSound(jamos[i + 1]);
                            }
                            i++;
                        }
                    } else {
                        //最后一个字母是子音
                        jamos[i] = lastSoundToDelegateSound(jamos[i]);
//                        transfer++;
                    }
                }
            }
        }
        return String.valueOf(jamos);
    }

    /**
     * 转换成送气音
     *
     * @param origin
     * @return
     */
    private char toAspiratedSound(char origin) {
        try {
            return consonantMap.get(origin)[0] == 1 ? origin : consonantMap.get(origin)[0];
        } catch (NullPointerException e) {
            return origin;
        }
    }

    /**
     * 转换成紧音
     *
     * @param origin
     * @return
     */
    private char toTightSound(char origin) {
        return consonantMap.get(origin)[1];
    }

    /**
     * 转换成鼻音
     *
     * @param origin
     * @return
     */
    private char toNasal(char origin) {
        return consonantMap.get(origin)[2];
    }

    /**
     * 将传入的字符转换成其代表因
     *
     * @param request
     * @return
     */
    private char lastSoundToDelegateSound(char request) {
        if (request == 'ㅋ' || request == 'ㄲ' || request == 'ㄱ' || request == 'ㄺ' || request == 'ㄳ') {
            return 'ㄱ';
        } else if (request == 'ㅌ' || request == 'ㅅ' || request == 'ㅈ' || request == 'ㅆ' || request == 'ㄷ' || request == 'ㅊ') {
            return 'ㄷ';
        } else if (request == 'ㅍ' || request == 'ㄿ' || request == 'ㅂ' || request == 'ㅄ') {
            return 'ㅂ';
        } else if (request == 'ㄴ' || request == 'ㄵ' || request == 'ㄶ') {
            return 'ㄴ';
        } else if (request == 'ㄹ' || request == 'ㄼ' || request == 'ㄽ' || request == 'ㄾ' || request == 'ㅀ') {
            return 'ㄹ';
        } else if (request == 'ㅁ' || request == 'ㄻ') {
            return 'ㅁ';
        } else if (request == 'ㅇ') {
            return 'ㅇ';
        } else if (request == 'ㅎ') {
            return '1';
        }
        return '1';
    }

    /**
     * 将散装字母组装成韩字
     *
     * @param request
     * @return
     */
    private String synthesis(String request) {
        Process process;
        String result = null;
        String cmd = beforeSynthesis(request);
//        String[] args = new String[]{"python", "F:/Programming/acode/HanBianCoreCode/lib/synthesisString.py", cmd};
        String query = "python " + filePath + "/synthesisString.py " + cmd;

        try {
            process = Runtime.getRuntime().exec(query);
            process.getInputStream();
            process.getInputStream().close();
            process.getOutputStream().close();
            process.getErrorStream().close();
            process.waitFor();
            process.destroy();
            BufferedReader bf = new BufferedReader(new FileReader(new File(filePath + "/synthesisResult.txt")));
            result = bf.readLine();
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //判断是否为“输入格式有误，无法解析”
        if ("输入格式有误，无法解析".equals(result)) {
            return request;
        } else {
            return addSpace(result);
        }
    }

    //TODO 改为使用HashMap
    /**
     * 将双收音转换成两个子音
     *
     * @param request
     * @return
     */
    private char[] splitDoubleLastSound(char request) {
        if (request == 'ㄺ') {
            return new char[]{'ㄹ', 'ㄱ'};
        } else if (request == 'ㄳ') {
            return new char[]{'ㄱ', 'ㅅ'};
        } else if (request == 'ㄵ') {
            return new char[]{'ㄴ', 'ㅈ'};
        } else if (request == 'ㄶ') {
            return new char[]{'ㄴ', 'ㅎ'};
        } else if (request == 'ㄼ') {
            return new char[]{'ㄹ', 'ㅂ'};
        } else if (request == 'ㄽ') {
            return new char[]{'ㄹ', 'ㅅ'};
        } else if (request == 'ㄾ') {
            return new char[]{'ㄹ', 'ㅌ'};
        } else if (request == 'ㅀ') {
            return new char[]{'ㄹ', 'ㅎ'};
        } else if (request == 'ㄻ') {
            return new char[]{'ㄹ', 'ㅁ'};
        } else if (request == 'ㄿ') {
            return new char[]{'ㄹ', 'ㅍ'};
        } else if (request == 'ㅄ') {
            return new char[]{'ㅂ', 'ㅅ'};
        }
        return new char[]{'1', '1'};
    }

    private String beforeSynthesis(String request) {
        StringBuffer result = new StringBuffer();

        //将request以空格为界分割成String字符串
        String[] stringArray = request.trim().split(" ");
        //对stringArray中的每一个字符串遍历
        for (int i = 0; i < stringArray.length; i++) {

            //指针current指定当前合成到哪个字符
            int current = 0;
            //用temp替换表示stringArray[i]
            String temp = stringArray[i];
            int[] mark = mark(temp.toCharArray());
            //循环遍历temp中的每一个字符
            for (int j = 2; j < temp.length(); j++) {
                if (mark[j] == 1) {
                    result.append(temp.substring(current, j - 1)).append(" ");
                    current = j - 1;

                }

            }
            result.append(temp.substring(current)).append(" ");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 给spaceList添加上数字
     *
     * @param request
     * @return
     */
    private void addSpaceList(String request) {
        for (int i = 0; i < request.length(); i++) {
            if (request.charAt(i) == ' ') {
                spaceList.add(i);
            }
        }
    }

    private String addSpace(String request) {
        StringBuffer stringBuffer = new StringBuffer(request.replaceAll(" ", ""));
        for (int i : spaceList) {
            stringBuffer.insert(i, " ");
        }
        return stringBuffer.toString();
    }

    private String deletePunctuation(String request) {
        String punctuation = "，。/《》？；‘：“”’【】{}！（）,./<>?;':\"[]";
        StringBuffer temp = new StringBuffer(request);
        for (int i = 0; i < temp.length(); i++) {
            if (punctuation.contains("" + temp.charAt(i))) {
                temp.deleteCharAt(i);
                i--;
            }
        }
        return temp.toString();
    }

    public String uiTransfer(String request) {
        for (int i = 0; i < request.length(); i++) {
            if (request.charAt(i) == '의' && ((i + 1) < request.length() && request.charAt(i + 1) == ' ') && (i - 1 > 0 && (request.charAt(i - 1) != '회' || request.charAt(i - 1) != '의'))) {
                request = new StringBuffer(request).replace(i, i + 1, "" + '에').toString();
            }
            if (i>0&&request.charAt(i)=='의'&&request.charAt(i-1)!=' '){
                request=new StringBuffer(request).replace(i,i+1,""+'이').toString();
            }
        }
//        String uiWithConsonant = "븨즤듸긔싀믜늬릐희킈틔츼픠쁴쯰띄끠씌";
//        String uiTransfered = "비지디기시미니리히키티치피삐찌띠끼씨";
        char[] uiWithConconantChar = new char[]{'븨', '즤', '듸', '긔', '싀', '믜', '늬', '릐', '희', '킈', '틔', '츼', '픠', '쁴', '쯰', '띄', '끠', '씌'};
        char[] uiTransferedChar = new char[]{'비', '지', '디', '기', '시', '미', '니', '리', '히', '키', '티', '치', '피', '삐', '찌', '띠', '끼', '씨'};
        for (int i = 0; i < uiWithConconantChar.length; i++) {
            if (request.contains("" + uiWithConconantChar[i])) {
                request = request.replaceAll(""+uiWithConconantChar[i],""+uiTransferedChar[i]);
            }
        }
        return request;
    }
}