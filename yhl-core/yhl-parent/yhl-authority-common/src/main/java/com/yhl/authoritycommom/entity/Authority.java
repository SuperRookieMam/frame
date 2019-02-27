package com.yhl.authoritycommom.entity;


import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name = "authority")
//  单一表策略就是副实体和子实体的数据都存放在一张表中，
//  同时指定一列用来区别这些实体。
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class Authority extends BaseEntity<String> {





}
