import java.util.ArrayList;
import java.util.HashSet;

import POJO.Korean;

public class test {
    public static void main(String[] args) {
        // System.out.println(Korean.hangulToJamo("나랑 결혼해 줄래?"));
        System.out.println("나랑 결혼해 줄래?");
        System.out.println(Korean.hangulToJamo("나랑 결혼해 줄래?"));
        System.out.println(Korean.jamoToHangul(Korean.hangulToJamo("나랑 결혼해 줄래?")));
    }
}
