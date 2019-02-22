package com.yhl.yhlsecuritycommon.provider;

import com.yhl.yhlsecuritycommon.access.RequestAuthorityAttribute;

import java.util.List;

/**
 * 自定义的服务提供暴露接口
 * */
public interface RequestAuthoritiesService {
    public List<RequestAuthorityAttribute> listAllAttributes();
}
