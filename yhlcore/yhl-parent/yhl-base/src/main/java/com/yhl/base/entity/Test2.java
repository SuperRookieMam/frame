package com.yhl.base.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="Test2")
/*@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)*/
/*@DiscriminatorValue(value = "test2")*/
public class Test2 extends BaseEntity implements Serializable{
    @Column(name = "name")
    private  String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
