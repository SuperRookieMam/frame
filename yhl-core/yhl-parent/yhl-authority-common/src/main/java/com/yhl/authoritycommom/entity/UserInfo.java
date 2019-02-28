package com.yhl.authoritycommom.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity<String> {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "pass_word")
    private String passWord;

    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "organization_id")
    private String organizationId;

    @ManyToOne
    @JoinColumn(name ="role_info")
    private  RoleInfo roleInfo;
}
