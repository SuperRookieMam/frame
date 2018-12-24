package com.yhl.base.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 添加一些数据库都有的公共字段或者做一些
 * **/

@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "ID",columnDefinition = "varchar(35) comment '编码'")
    private String id;


    @Column(name = "create_time",updatable = false,columnDefinition ="datetime comment '创建时间'")
    private String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    @Column(name = "modify_time",columnDefinition ="datetime comment '修改时间'")
    private String modifyTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


    @Column(name = "create_user",updatable = false,columnDefinition ="varchar(255) comment '创建者'")
    private String createUser;

}
