import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class transfer {
    //TODO 部署前更改读取文件夹路径及py文件中的文件路径
    public HashMap<Character, char[]> consonantMap;
    String consonant = "ㅂㅈㄷㄱㅅㅁㄴㅇㄹㅎㅋㅌㅊㅍㅃㅉㄸㄲㅆ";
    String doubleLastSound = "ㄺㄳㄵㄶㄼㄽㄾㅀㄻㄿㅄ";
    String vowel = "ㅛㅕㅑㅐㅔㅒㅖㅗㅓㅏㅣㅜㅠㅡㅚㅙㅞㅢㅝㅟㅘ";

    /**
     * 构造方法初始化子音Map
     */
    public transfer() {
        consonantMap = new HashMap<>();
        consonantMap.put('ㄱ', new char[]{'ㅋ', 'ㄲ', 'ㅇ', '1'});
        consonantMap.put('ㄷ', new char[]{'ㅌ', 'ㄸ', 'ㄴ', 'ㄹ'});
        consonantMap.put('ㅂ', new char[]{'ㅍ', 'ㅃ', 'ㅁ', '1'});
        consonantMap.put('ㅅ', new char[]{'1', 'ㅆ', '1', '1'});
        consonantMap.put('ㅈ', new char[]{'ㅊ', 'ㅉ', '1', '1'});
        consonantMap.put('ㅎ', new char[]{'1', '1', '1', '1'});
    }

    public String HangulTransfer(String request){
        return synthesis(transferJamos(stringToCharArray(request),mark(stringToCharArray(request))));
    }

    /**
     * 将韩语转换成字母并拆分成char数组
     *
     * @param request
     * @return char[]
     */
    public char[] stringToCharArray(String request) {
        request = splitString(request);
//        System.out.println(request);
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
    public String splitString(String request) {
        Process process;
        String result = null;
        try {
            String[] args = new String[]{"python", "F:/Programming/acode/HanBianCoreCode/lib/splitString.py", request};
            process = Runtime.getRuntime().exec(args);
            process.getInputStream();
            process.getInputStream().close();
            process.getOutputStream().close();
            process.getErrorStream().close();
            process.waitFor();
//            Thread.sleep(1000);
            process.destroy();
            BufferedReader bf = new BufferedReader(new FileReader(new File("./lib/result.txt")));
            result = bf.readLine();
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(result);
        return result;
    }

    /**
     * 通过一个数组来标定对应字符为子音还是母音还是空格
     * 0为子音，1为母音，2为空格
     *
     * @param jamos
     * @return int[]
     */
    public int[] mark(char[] jamos) {
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
//        System.out.println(Arrays.toString(mark));
        return mark;
    }

    //TODO 增加IndexOutOfRange异常处理

    /**
     * 将字母字符组转换成音变后的字母
     *
     * @param jamos
     * @param mark
     * @return
     */
    public String transferJamos(char[] jamos, int[] mark) {
        int transfer = 1;
        char[] point;
        int count = 0;
        while (transfer != 0) {
//            System.out.println("循环第" + ++count + "次");
            transfer = 0;
            jamos = String.valueOf(jamos).replaceAll("1", "").toCharArray();
            mark = mark(jamos);
            //TODO 大改，没有三个辅音的情况
            for (int i = 0; i < jamos.length; i++) {
                //判断当前是否为子音
                if (mark[i] == 0) {
                    //判断是否为最后一个
                    if (jamos.length - 1 > i) {
                        //判断下一个是否为子音
                        if (mark[i + 1] == 0) {
                            //判断是否存在第三位
                            if (jamos.length - 2 > i) {
                                //判断下下个是否为子音
                                if (mark[i + 2] == 0) {
                                    //如果第三个子音为ㅇ，则删去ㅇ
                                    //如果第三个收音为ㅎ，则删去ㅎ并将前一个收音送气音化
                                    if (jamos[i + 2] == 'ㅇ') {
                                        jamos[i + 2] = '1';
                                        transfer++;
                                    } else if (jamos[i + 2] == 'ㅎ') {
                                        jamos[i + 1] = toAspiratedSound(jamos[i + 1]);
                                        jamos[i + 2] = '1';
                                        transfer++;
                                    } else {
//                                        jamos[i]=lastSoundToDelegateSound(new char[]{jamos[i-2],jamos[i-1],jamos[i],jamos[i+1]});
                                        jamos[i + 1] = '1';
                                        transfer++;
                                    }
                                    i = i + 2;
                                } else {
                                    //两个字母是子音的情况
                                    //TODO 各种变化情况
                                    //TODO 判断是否为代表因，根据结果决定是否变为代表因
                                    if (jamos[i + 1] == 'ㅇ') {
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
                                    }
                                    i++;
                                }
                            } else {
                                //最后两个字母是子音
//                                jamos[i]=lastSoundToDelegateSound(new char[]{jamos[i-2],jamos[i-1],jamos[i],jamos[i+1]});
                                jamos[i + 1] = '1';
                                transfer++;
                                i++;
                            }
                        }
                    } else {
                        //最后一个字母是子音
                        jamos[i] = lastSoundToDelegateSound(jamos[i]);
                        transfer++;
                    }
                }
            }
        }
//        System.out.println(String.valueOf(jamos));
        return String.valueOf(jamos);
    }

    /**
     * 转换成送气音
     *
     * @param origin
     * @return
     */
    public char toAspiratedSound(char origin) {
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
    public char toTightSound(char origin) {
        return consonantMap.get(origin)[1];
    }

    /**
     * 转换成鼻音
     *
     * @param origin
     * @return
     */
    public char toNasal(char origin) {
        return consonantMap.get(origin)[2];
    }

    //TODO 如果是ㅎ的话就将返回‘1’

    /**
     * 将传入的字符转换成其代表因
     *
     * @param request
     * @return
     */
    public char lastSoundToDelegateSound(char request) {
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
    public String synthesis(String request) {
        Process process;
        String result=null;
        String cmd = beforeSynthesis(request);
//        String[] args = new String[]{"python", "F:/Programming/acode/HanBianCoreCode/lib/synthesisString.py", cmd};
        String query="python F:/Programming/acode/HanBianCoreCode/lib/synthesisString.py "+cmd;

        try {
            process = Runtime.getRuntime().exec(query);
            process.getInputStream();
            process.getInputStream().close();
            process.getOutputStream().close();
            process.getErrorStream().close();
            process.waitFor();
//            Thread.sleep(1000);
            process.destroy();
            BufferedReader bf = new BufferedReader(new FileReader(new File("./lib/synthesisResult.txt")));
//            Thread.sleep(1000);
            result = bf.readLine();
            bf.close();
//            process.getOutputStream().close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
//        System.out.println(result);
        return result;
    }

    //TODO

    /**
     * 将双收音转换成两个子音
     *
     * @param request
     * @return
     */
    public char[] splitDoubleLastSound(char request) {
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

    public String beforeSynthesis(String request) {
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
//            result.delete(result.length()-1,result.length());
            result.append("\" \"").append(" ");
        }
        result.delete(result.length() - 5, result.length());
//        System.out.println(result.toString());
        return result.toString();
    }
}