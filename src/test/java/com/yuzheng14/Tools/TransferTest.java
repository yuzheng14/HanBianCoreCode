package com.yuzheng14.Tools;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import static com.yuzheng14.Tools.Transfer.*;
public class TransferTest {
    @Test
    public void numbersToHangulTest(){
//        println(Transfer.numbersToHangul("123456"));
    }

    public void println(String s){
        System.out.println(s);
    }

    @Test
    public void storeOtherSymbolTest() throws NoSuchMethodException, InvocationTargetException,IllegalAccessException,NoSuchFieldException {
        Method method = Transfer.class.getDeclaredMethod("storeOtherSymbol", String.class);
        method.setAccessible(true);
        String s = (String) method.invoke(Transfer.class,"1중2汉字국 어");
        Assert.assertEquals("중국 어",s);
        method=Transfer.class.getDeclaredMethod("getSymbolList");
        method.setAccessible(true);
//        ArrayList<SymbolEntry> symbolList = (ArrayList<SymbolEntry>) method.invoke(Transfer.class);
//        SymbolEntry s1=symbolList.get(0);
//        SymbolEntry s2=symbolList.get(1);
//        SymbolEntry s3=symbolList.get(2);
//        SymbolEntry s4=symbolList.get(3);
//        Assert.assertEquals('1',s1.getSymbol());
//        Assert.assertEquals('2',s2.getSymbol());
//        Assert.assertEquals('汉',s3.getSymbol());
//        Assert.assertEquals('字',s4.getSymbol());
    }
    @Test
    public void jamosToPronunciationTest() throws Exception{
        Method method=Transfer.class.getDeclaredMethod("jamosToPronunciation", String.class);
        method.setAccessible(true);
        Assert.assertEquals("ㅎㅏㄴㄱㅜㄱㅓ",(String)method.invoke(Transfer.class,"ㅎㅏㄴㄱㅜㄱㅇㅓ"));
    }

    @Test
    public void addOtherSymbolTest() throws Exception{
        Method storeOtherSymbol = Transfer.class.getDeclaredMethod("storeOtherSymbol", String.class);
        Method addOtherSymbol = Transfer.class.getDeclaredMethod("addOtherSymbol", String.class);
        storeOtherSymbol.setAccessible(true);
        addOtherSymbol.setAccessible(true);
        Assert.assertEquals("1중2汉字국 어",(String)addOtherSymbol.invoke(Transfer.class,(String)storeOtherSymbol.invoke(Transfer.class,"1중2汉字국 어")));
    }

    @Test
    public void HangulToPronunciationTest(){
        Assert.assertEquals("절때로 고개를 떨구지 말라",hangulTransfer("절대로 고개를 떨구지 말라"));
        println("[测试成功]: "+"절대로 고개를 떨구지 말라");
        Assert.assertEquals("고개를 치켜들고 세상을 똑빠로 바라보라",hangulTransfer("고개를 치켜들고 세상을 똑바로 바라보라"));
        println("[测试成功]: "+"고개를 치켜들고 세상을 똑바로 바라보라");
        Assert.assertEquals("자시네 능녀글 실뢰하라",hangulTransfer("자신의 능력을 신뢰하라"));
        println("[测试成功]: "+"자신의 능력을 신뢰하라");
        Assert.assertEquals("겸소나지만 함니저긴 자신가멉씨는 성공할 수도 행보칼 수도 업따",hangulTransfer("겸손하지만 합리적인 자신감없이는 성공할 수도 행복할 수도 없다"));
        println("[测试成功]: "+"겸손하지만 합리적인 자신감없이는 성공할 수도 행복할 수도 없다");
    }

    @Test
    public void someOtherTest1(){
        Assert.assertEquals("일기",hangulTransfer("읽기"));
        Assert.assertEquals("박따",hangulTransfer("밝다"));
    }
    @Test
    public void HangulToPronunciationTest2(){
        Assert.assertEquals("나는 혼자서 미뢰에 기를 나섣슴미다",hangulTransfer("나는 혼자서  밀회의 길을 나섰습니다"));
        println("[测试成功]: "+"절대로 고개를 떨구지 말라");
        Assert.assertEquals("고개를 치켜들고 세상을 똑빠로 바라보라",hangulTransfer("고개를 치켜들고 세상을 똑바로 바라보라"));
        println("[测试成功]: "+"고개를 치켜들고 세상을 똑바로 바라보라");
        Assert.assertEquals("자시네 능녀글 실뢰하라",hangulTransfer("자신의 능력을 신뢰하라"));
        println("[测试成功]: "+"자신의 능력을 신뢰하라");
        Assert.assertEquals("겸소나지만 함니저긴 자신가멉씨는 성공할 수도 행보칼 수도 업따",hangulTransfer("겸손하지만 합리적인 자신감없이는 성공할 수도 행복할 수도 없다"));
        println("[测试成功]: "+"겸손하지만 합리적인 자신감없이는 성공할 수도 행복할 수도 없다");
    }
}
