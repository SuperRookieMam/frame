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
@Table(name = "organization")
public class Organization extends BaseEntity<String> {

    @Column(name = "organization_name")
    private  String organizationName;

    //证件照
    @Column(name = "identification_img")
    private  String identificationImg;

    //机构代表人
    @Column(name = "owner_id")
    private  String ownerId;

    //机构代码
    @Column(name = "unit_code")
    private  String unitCode;


}
