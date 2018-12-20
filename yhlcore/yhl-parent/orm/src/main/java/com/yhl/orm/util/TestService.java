package com.yhl.orm.util;

import com.yhl.orm.dao.jpaDao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
   @Autowired
   TestDao testDao;

    public void  test(){
        System.out.println("<<<<<<<<<<<<<<<<<<<<开始>>>>>>>>>>>>>>>>>>");
        System.out.println(testDao.updateBysql("UPDATE  bill_info b SET b.checkinName ='这是测试' WHERE b.id ='000152B773A42C2BA444866EEFF10B1C'"));
        System.out.println("<<<<<<<<<<<<<<<<<<<<结束>>>>>>>>>>>>>>>>>>");
    }
}
