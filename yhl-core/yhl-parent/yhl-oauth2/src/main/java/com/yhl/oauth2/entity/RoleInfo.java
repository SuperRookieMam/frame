package com.yhl.oauth2.entity;

import com.yhl.base.baseEntity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "role_info")
public class RoleInfo extends BaseEntity<String> implements Serializable{

    private static final long serialVersionUID = -1025123300854871813L;

    @Column(name = "role_name")
    private  String roleName;

    @Column(name = "depart_ment")
    private  String departMent;

    @Column(name = "pid")
    private  String pid;

    @Column(name = "organization")
    private  String organization;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDepartMent() {
        return departMent;
    }

    public void setDepartMent(String departMent) {
        this.departMent = departMent;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
