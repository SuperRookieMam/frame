package com.yhl.oauth2.service.Impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.oauth2.entity.MyClientDetail;
import com.yhl.oauth2.service.MyClientDetailService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class MyClientDetailServiceImpl extends BaseServiceImpl<MyClientDetail,String> implements MyClientDetailService {

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return null;
    }
}
