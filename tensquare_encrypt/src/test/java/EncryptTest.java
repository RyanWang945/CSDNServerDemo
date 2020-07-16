import com.ryan.encrypt.EncryptApplication;
import com.ryan.encrypt.rsa.RsaKeys;
import com.ryan.encrypt.service.RsaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EncryptApplication.class)
public class EncryptTest {

    @Autowired
    private RsaService rsaService;

    @Before
    public void before() throws Exception{
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void genEncryptDataByPubKey() {
        String data = "{\"title\":\"java\"}";

        try {

            String encData = rsaService.RSAEncryptDataPEM(data, RsaKeys.getServerPubKey());

            System.out.println("data: " + data);
            System.out.println("encData: " + encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test() throws Exception {
        String requestData="S2MjpH6/MXxPpDPlsUtslo3ArrNScpTOHsCLKJCN/c3wFi6hmInkqyURhBo8V6hA7TmMplk0u4HcXtp+SOal9lxKIMi6E/ZsHZiFBY66/ixKpnA2NCIbtHufo977zdS5FQwRZ9rOCuurY/XSwAv9BLoMhYnPDDvMgfTupESD2367KafnJZfDAqYI+FV/WSqP52h56q1E+jEDzQZOVdBR1zYYYF/IcgSeuBi4I1bjyZX5RbqjrNf9Rvjlg0Yxkcj1yVWd4yE9GyvtNnk3Vt71XezlUkjW9w6qpm8wSTUd4/V8ob9ukL/z+jjuAdSttkAiiYCwT0U9HEsWeDX/uN29BQ==\n";
        String s = rsaService.RSADecryptDataPEM(requestData, RsaKeys.getServerPrvKeyPkcs8());
        System.out.println(s);
    }
}
