package com.yhl.yhloauthserver.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.yhloauthserver.entity.MyClientDetail;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface MyClientDetailService extends BaseService<MyClientDetail,String>,ClientDetailsService {


}
