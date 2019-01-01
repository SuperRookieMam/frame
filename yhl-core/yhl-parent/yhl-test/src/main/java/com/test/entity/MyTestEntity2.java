package com.test.entity;

import com.yhl.base.baseEntity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "my_test_entity2")
public class MyTestEntity2 extends BaseEntity<String>  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
