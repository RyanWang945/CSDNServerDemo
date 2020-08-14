import com.ryan.redis.RedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ryan
 * @date 2020/7/18 10:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  RedisApplication.class)
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    public void test(){
        redisTemplate.opsForValue().set("test","redis");
        System.out.println(redisTemplate.opsForValue().get("test"));
    }
}
