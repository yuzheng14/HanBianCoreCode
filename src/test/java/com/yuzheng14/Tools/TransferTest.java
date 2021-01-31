package com.yuzheng14.Tools;

import org.junit.Assert;
import org.junit.Test;
import com.yuzheng14.Tools.Transfer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
        ArrayList<SymbolEntry> symbolList = (ArrayList<SymbolEntry>) method.invoke(Transfer.class);
        SymbolEntry s1=symbolList.get(0);
        SymbolEntry s2=symbolList.get(1);
        SymbolEntry s3=symbolList.get(2);
        SymbolEntry s4=symbolList.get(3);
        Assert.assertEquals('1',s1.getSymbol());
        Assert.assertEquals('2',s2.getSymbol());
        Assert.assertEquals('汉',s3.getSymbol());
        Assert.assertEquals('字',s4.getSymbol());
    }
    @Test
    public void jamosToPronunciationTest() throws Exception{
        Method method=Transfer.class.getDeclaredMethod("jamosToPronunciation", String.class);
        method.setAccessible(true);
        Assert.assertEquals("한구거",(String)method.invoke(Transfer.class,"한국어"));
    }
}
