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
        Assert.assertEquals("나는 혼자서 미뢰에 기를 나섣씀니다",hangulTransfer("나는 혼자서 밀회의 길을 나섰습니다"));
        println("[测试成功]: "+"나는 혼자서  밀회의 길을 나섰습니다");
        Assert.assertEquals("그러나 이 은밀 한 어둠 소게 나를 뒤쫀는 사라믄 누굼니까",hangulTransfer("그러나 이 은밀 한 어둠 속에 나를 뒤쫓는 사람은 누굽니까"));
        println("[测试成功]: "+"그러나 이 은밀 한 어둠 속에 나를 뒤쫓는 사람은 누굽니까");
        Assert.assertEquals("나는 그를 살피려고 여프로 비키지만 그에게서 다라날 도리가 업슴니다",hangulTransfer("나는 그를 살피려고 옆으로 비키지만  그에게서 달아날 도리가 없습니다"));
        println("[测试成功]: "+"나는 그를 살피려고 옆으로 비키지만  그에게서 달아날 도리가 없습니다");
        Assert.assertEquals("그는 그에 오여난 거르므로 땅에 먼지를 일게 하고 내가 마라는 말 끋마다 그에 음성을 보탬미다",hangulTransfer("그는 그의 오연한 걸음으로  땅에 먼지를 일게 하고  내가 말하는 말끝마다 그의 음성을 보탭니다"));
        println("[测试成功]: "+"그는 그의 오연한 걸음으로  땅에 먼지를 일게 하고  내가 말하는 말끝마다 그의 음성을 보탭니다");
        Assert.assertEquals("그는 내 조그만 자아 인 거심미다",hangulTransfer("그는 내 조그만 자아 인 것입니다"));
        println("[测试成功]: "+"그는 내 조그만 자아 인 것입니다");
        Assert.assertEquals("주여 그는 수치를 모름미다 그러나 나는 그와 더부러 나메 문저네 가기는 창피해요",hangulTransfer("주여!  그는 수치 를 모릅니다. 그러나  나는 그와 더불어 남의 문전에 가기는 창피해요"));
        println("[测试成功]: "+"주여 그는 수치 를 모릅니다. 그러나  나는 그와 더불어 남의 문전에 가기는 창피해요");
        Assert.assertEquals("공화당 지도자드른 그를 당에 수치라고 불랃따",hangulTransfer("공화당 지도자들은 그를 당의 수치라고 불렀다"));
        println("[测试成功]: "+"공화당 지도자들은 그를 당의 수치라고 불렀다");
        Assert.assertEquals("대나지얻슬때 그드른 내 지베 도라와서 마라기를 우리는 이고세서 가장 자근 방을 차지 힐 뿌님미다",hangulTransfer("대낮 이었을 때 그들은 내 집에 들어와서  말하기를 우리는 이곳에서 가장 작은 방을 차지 할 뿐입니다"));
        println("[测试成功]: "+"대낮 이었을 때 그들은 내 집에 들어와서  말하기를 우리는 이곳에서 가장 작은 방을 차지 할 뿐입니다");
        Assert.assertEquals("그드른 마라기를 우리는 당시네 예배를 돕겓스며 시네 은총을 우리들 몽마늘 겸소니 바드면 되지요",hangulTransfer("그들은 말하기를, 우리는 당신의 신에의 예배 를 돕겠으며 신의 은총을 우리들 몫만을 겸손히 받으면 되지요"));
        println("[测试成功]: "+"그들은 말하기를, 우리는 당신의 신에의 예배 를 돕겠으며 신의 은총을 우리들 몫만을 겸손히 받으면 되지요");
        Assert.assertEquals("그리고 그드른 두서게 자리잡고 조용히 온수나게 안잗슴미다",hangulTransfer("그리고 그들은 구석에 자리잡고 조용히 온순하게 앉았습니다"));
        println("[测试成功]: "+"그리고 그들은 구석에 자리잡고 조용히 온순하게 앉았습니다");
        Assert.assertEquals("하지만 바메 어두믈 타서 나는 그드리 매우 난포카게 내 신청한 사당으로 쳐드러가 부정한 타묘그로 시네 제다네서 제무를 강타래가는 거슬 봄미다",hangulTransfer("하지만 밤의 어둠을 타서 나는 그들이 매우 난폭하게 내 신성한 사당으로 쳐들어가 부정한 탐욕으로 신의 제단에서 제물을 강탈해가는 것을 봅니다"));
        println("[测试成功]: "+"하지만 밤의 어둠을 타서 나는 그들이 매우 난폭하게 내 신성한 사당으로 쳐들어가 부정한 탐욕으로 신의 제단에서 제물을 강탈해가는 것을 봅니다");
    }
}
