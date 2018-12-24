package com.yhl.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name="Test2")
public class Test2 extends BaseEntity implements Serializable{
    @Column(name = "name",updatable = false,columnDefinition ="varchar(255) comment '名字'")
    private  String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
