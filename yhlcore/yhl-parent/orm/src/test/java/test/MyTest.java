package test;

import com.yhl.orm.DemoApplication;
import com.yhl.orm.dao.jpaDao.JpaBaseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)

public class MyTest {

    @Autowired
    JpaBaseDao jpaBaseDao;


    @Test
    public void test1() throws Exception {
        System.out.println("<<<<<<<<<<<<<<<<<<<<开始>>>>>>>>>>>>>>>>>>");
        System.out.println(jpaBaseDao.updateBysql("UPDATE  bill_info b SET b.checkinName ='这是测试' WHERE b.id ='000152B773A42C2BA444866EEFF10B1C'"));
        System.out.println("<<<<<<<<<<<<<<<<<<<<结束>>>>>>>>>>>>>>>>>>");
    }
}
