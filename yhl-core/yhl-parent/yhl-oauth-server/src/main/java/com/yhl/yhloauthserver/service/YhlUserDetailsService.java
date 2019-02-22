package com.yhl.yhloauthserver.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.yhloauthserver.entity.YhlUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface YhlUserDetailsService extends BaseService<YhlUserDetails,String>, UserDetailsService {
}
