package com.yhl.yhlsecuritycommon.componet.provider;


import com.yhl.yhlsecuritycommon.componet.access.RequestAuthorityAttribute;

import java.util.List;

/**
 * 自定义的服务提供暴露接口
 * 这个主要是你哪来反悔你想要的东西的
 * */
public interface RequestAuthoritiesService {
    public List<RequestAuthorityAttribute> listAllAttributes();
}
