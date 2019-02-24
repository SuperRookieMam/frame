package com.yhl.yhlsecuritycommon.componet.provider;


import com.yhl.yhlsecuritycommon.componet.access.RequestAuthorityAttribute;

import java.util.List;

/**
 * 自定义的服务提供暴露接口
 * */
public interface RequestAuthoritiesService {
    public List<RequestAuthorityAttribute> listAllAttributes();
}
