package com.yhl.authoritycommom.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * 除了这四个有没有别的使用情况，尤其是一个实体类要在多个不同的实体类中进行使用，
 * 而本身又不需要独立生成一个数据库表，这就是需要@Embedded、@Embeddable的时候了，
 * 下面分成4类来说明在一个实体类中引用另外的实体类的情况，具体的数据库环境是MySQL 5.7。
 * */
@Embeddable//嵌入引用表中，此类不创建表
@Getter
@Setter
public class ResourceOperation extends BaseEntity<String> {
    @ManyToOne
    private Resource resource;

    @Column(name = "read_able")
    private Boolean readable;

    @Column(name = "save_able")
    private Boolean saveable;

    @Column(name = "update_able")
    private Boolean updateable;

    @Column(name = "delete_able")
    private Boolean deleteable;
}
