package com.yhl.oauth2.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauth2.entity.YhlUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface YhlUserDetailsService extends BaseService<YhlUserDetails,String> , UserDetailsService {
}
