package com.yhl.authoritycommom.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "role_info")
public class RoleInfo extends BaseEntity<String> {

    @Column(name = "roleName")
    private  String roleName;



}
