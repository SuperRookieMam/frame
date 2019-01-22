package com.yhl.oauth2.entity;

import com.yhl.base.baseEntity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "function_info")
public class FunctionInfo extends BaseEntity<String>  {

    @Column(name = "menu_name")
    private  String menuName;

    @Column(name = "menu_url")
    private  String menuUrl;

    @Column(name = "pid")
    private String pid;

    @Column(name = "is_use")
    private Integer isUse;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "is_menu")
    private Integer isMenu;


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

}
