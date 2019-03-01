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
@Table(name = "department")
public class Department extends BaseEntity<String> {
    private static final long serialVersionUID = -2537471885858114859L;


    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "pid")
    private String pid;

    @Column(name = "organization_id")
    private String organizationId;
}
