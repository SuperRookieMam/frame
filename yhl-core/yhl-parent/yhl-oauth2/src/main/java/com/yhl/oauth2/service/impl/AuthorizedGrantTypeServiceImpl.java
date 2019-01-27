package com.yhl.oauth2.service.impl;

import com.yhl.base.baseService.BaseService;
import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.oauth2.entity.AuthorizedGrantType;
import com.yhl.oauth2.service.AuthorizedGrantTypeService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizedGrantTypeServiceImpl extends BaseServiceImpl<AuthorizedGrantType,String> implements AuthorizedGrantTypeService {
}
