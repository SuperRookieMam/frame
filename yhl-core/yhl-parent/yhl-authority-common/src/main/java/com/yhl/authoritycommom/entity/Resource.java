package com.yhl.authoritycommom.entity;

import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.yhlsecuritycommon.componet.access.RequestAuthorityAttribute;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "resource")
public class Resource extends BaseEntity<String> {
    private static final long serialVersionUID = 7288402738373662931L;

    /**
     * 资源名称
     */
    @Column(name = "name_", length = 100, unique = true)
    private String name;

    /**
     * 资源匹配路径
     */
    @Column(name = "matcher_", length = 400, unique = true)
    private String matcher;
    /**
     * 路径匹配类型
     */
    @Column(name = "match_type_")
    @Enumerated(EnumType.STRING)
    private RequestAuthorityAttribute.MatchType matchType = RequestAuthorityAttribute.MatchType.ANT_PATH;

    /**
     * 资源所属范围
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "scope_")
    @CollectionTable(name = "resource_scope_", joinColumns = {
            @JoinColumn(name = "resource")
    })
    private Set<String> scope;

    /**
     * 资源描述
     */
    @Column(name = "description_", length = 800)
    private String description;

}
