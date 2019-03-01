package com.yhl.authoritycommom.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "resource_scope")
public class ResourceScope extends BaseEntity<String> {

    @ManyToOne
    @JoinColumn(name = "resource")
    private Resource resource;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "department_id")
    private String departmentId;

    @Embedded
    private  ResourceOperation resourceOperation;

    @Column(name = "organization_id")
    private String organizationId;

}
