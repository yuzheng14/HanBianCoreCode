import POJO.Transfer_Old;
import org.junit.Test;

public class TransferOldTest {
    Transfer_Old transferOld = new Transfer_Old();

    @Test
    public void wholeTest() {
        System.out.println(transferOld.HangulTransfer("마우스증후군"));
    }

    @Test
    public void exceptionTest() {
        System.out.println(transferOld.HangulTransfer("롣져 ㅇㄷ"));
    }

    @Test
    public void errorTest() {
        System.out.println(transferOld.HangulTransfer("한국말을 잘했어"));
    }

    @Test
    public void StringOutOfBoundsTest() {
        System.out.println(transferOld.HangulTransfer("제 친구들도 이제 거의 다 흰머리가 났던데요."));
    }
    @Test
    public void uiTransferTest(){
        System.out.println(transferOld.uiTransfer("의논하다"));
    }
}
