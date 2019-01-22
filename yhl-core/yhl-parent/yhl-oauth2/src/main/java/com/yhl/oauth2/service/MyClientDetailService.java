package com.yhl.oauth2.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauth2.entity.MyClientDetail;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface MyClientDetailService extends BaseService<MyClientDetail,String>,ClientDetailsService {

}
