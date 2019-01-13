package com.yhl.security.service.Impl;

import com.yhl.security.service.MyUserDetailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailServiceImpl implements MyUserDetailService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
