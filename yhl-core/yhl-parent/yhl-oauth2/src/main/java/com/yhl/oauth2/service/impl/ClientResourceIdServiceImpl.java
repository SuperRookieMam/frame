package com.yhl.oauth2.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.oauth2.entity.AuthorizedGrantType;
import com.yhl.oauth2.entity.ClientResourceId;
import com.yhl.oauth2.service.AuthorizedGrantTypeService;
import com.yhl.oauth2.service.ClientResourceIdService;
import org.springframework.stereotype.Service;

@Service
public class ClientResourceIdServiceImpl extends BaseServiceImpl<ClientResourceId,String> implements ClientResourceIdService {
}
