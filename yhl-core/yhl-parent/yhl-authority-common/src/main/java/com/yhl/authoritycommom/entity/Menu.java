package com.yhl.authoritycommom.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "menu")
public class Menu extends BaseEntity<String> implements Serializable {
    private static final long serialVersionUID = -1211204901800035432L;

    /**
     * 菜单类型，指定一个菜单的链接类型，可以是链接到业务组件，也可以是页面URL
     */
    public enum MenuType {
        /**
         * 链接到业务组件
         */
        COMPONENT,
        /**
         * 链接到超链接
         */
        HYPERLINK
    }
    /**
     * 菜单名称
     */
    @Column(name = "name_", length = 50, nullable = false, unique = true)
    @NotNull
    private String name;
    /**
     * 菜单标题
     */
    @Column(name = "title_", length = 50)
    private String title;

    /**
     * 状态 启用true,禁用false
     */
    @Column(name = "enabled_")
    private boolean enabled = true;


    /**
     * 菜单表示的地址
     */
    @Column(name = "url_")
    private String url;


    /**
     * 菜单排序
     */
    @Column(name = "order_")
    private Long order;


    /**
     * 菜单图标
     */
    @Column(name = "icon_", length = 100)
    private String icon;

    /**
     * 备注信息
     */
    @Column(name = "description_", length = 1000)
    private String description;


    @ManyToOne
    @JoinColumn(name = "parent_")
    private Menu parent;


    @Column(name = "type_")
    @Enumerated(EnumType.STRING)
    private MenuType type = MenuType.COMPONENT;

    @Column(name = "organization_id")
    private String organizationId;

}
