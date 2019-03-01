package com.yhl.authoritycommom.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "menu_scop")
public class MenuScop  extends BaseEntity<String> {
    private static final long serialVersionUID = 7196044310348692291L;

    @ManyToOne
    @JoinColumn(name = "menu")
    private Menu menu;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "organization_id")
    private String organizationId;

}
