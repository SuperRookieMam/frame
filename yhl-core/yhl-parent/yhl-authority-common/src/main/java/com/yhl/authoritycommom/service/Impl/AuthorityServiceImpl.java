package com.yhl.authoritycommom.service.Impl;

import com.yhl.authoritycommom.dao.*;
import com.yhl.authoritycommom.entity.Authority;
import com.yhl.authoritycommom.service.AuthorityService;
import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.baseorm.component.constant.WhereCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl extends BaseServiceImpl<Authority,String> implements AuthorityService {
    @Autowired
    private AuthorityDao authorityDao;

    public Authority getAuthorityByRoleAndDepartment (String roleId,String departMentId){

        WhereCondition whereCondition = new WhereCondition();
        whereCondition.and().addEq("roleInfo.id",roleId).addEq("department.id",departMentId);
        findByParams(whereCondition);

        return  null;
    }


}
