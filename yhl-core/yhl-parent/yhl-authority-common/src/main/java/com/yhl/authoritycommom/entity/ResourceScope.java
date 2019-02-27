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

    @Column(name = "role")
    private String role;

    @Column(name = "resource_operation")
    private  ResourceOperation resourceOperation;


}
