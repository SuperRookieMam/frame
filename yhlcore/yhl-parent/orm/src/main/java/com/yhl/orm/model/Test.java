package com.yhl.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "Test")
public class Test implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID",columnDefinition = "varchar(32) comment '张三'")
    private  String id;


    @Column(name = "user_name")
    private  String userName;
}
