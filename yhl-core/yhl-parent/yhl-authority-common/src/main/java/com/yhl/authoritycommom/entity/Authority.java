package com.yhl.authoritycommom.entity;


import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "authority")
//  单一表策略就是副实体和子实体的数据都存放在一张表中，
////  同时指定一列用来区别这些实体。
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class Authority extends BaseEntity<String> {

    @ManyToOne
    @JoinColumn(name = "role_info")
    private  RoleInfo roleInfo;

    @ManyToOne
    @JoinColumn(name = "department")
    private  Department department;

    //联级刷新，如果词表校色发生变化，对应的list 夜发生变化懒加载
    @ManyToMany(cascade = CascadeType.REFRESH,fetch =FetchType.LAZY)
    @JoinTable(name = "menu_scop",joinColumns = {
            @JoinColumn(name ="role_id",referencedColumnName = "role_info"),
            @JoinColumn(name ="department_id",referencedColumnName = "department")})
    private List<MenuScop> menuScops;

    //联级刷新，如果词表校色发生变化，对应的list 夜发生变化懒加载
    @ManyToMany(cascade = CascadeType.REFRESH,fetch =FetchType.LAZY)
    @JoinTable(name = "resource_scope",joinColumns = {
            @JoinColumn(name ="role_id",referencedColumnName = "role_info"),
            @JoinColumn(name ="department_id",referencedColumnName = "department")})
    private  List<ResourceScope> resourceScopes;


    @Column(name = "organization_id")
    private String organizationId;

}
