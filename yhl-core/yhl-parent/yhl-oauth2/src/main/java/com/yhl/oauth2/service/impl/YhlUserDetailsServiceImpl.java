package com.yhl.oauth2.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.oauth2.entity.YhlUserDetails;
import com.yhl.oauth2.service.YhlUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class YhlUserDetailsServiceImpl extends BaseServiceImpl<YhlUserDetails,String> implements YhlUserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
