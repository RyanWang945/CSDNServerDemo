import com.ryan.article.utils.MD5;
import org.junit.Test;

/**
 * @author Ryan
 * @date 2020/8/14 9:15
 */
public class SmallTest {
    @Test
    public void test1(){
        String password= "123456";
        String MD5Password= MD5.next(password);
        System.out.println(MD5Password);
        if(MD5.next(password).equals(MD5Password)){
            System.out.println("密码正确");
        }
    }
}
