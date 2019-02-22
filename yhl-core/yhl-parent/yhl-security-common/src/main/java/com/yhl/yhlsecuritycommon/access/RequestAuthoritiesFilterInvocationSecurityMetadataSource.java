package com.yhl.yhlsecuritycommon.access;

import com.yhl.yhlsecuritycommon.provider.RequestAuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestAuthoritiesFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RequestAuthoritiesService requestAuthoritiesService;
    private Map<RequestInfo, RequestMatcher> requestMatchMap = new ConcurrentHashMap<RequestInfo, RequestMatcher>();

    public RequestAuthoritiesFilterInvocationSecurityMetadataSource() {
        super();
    }
    public RequestAuthoritiesFilterInvocationSecurityMetadataSource(
            RequestAuthoritiesService requestAuthoritiesService) {
        super();
        this.requestAuthoritiesService = requestAuthoritiesService;
    }

    @Autowired
    public void setRequestAuthoritiesService(RequestAuthoritiesService requestAuthoritiesService) {
        this.requestAuthoritiesService = requestAuthoritiesService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        return null;
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
class RequestInfo {
    private String pattern;
    private HttpMethod method;
    private RequestAuthorityAttribute.MatchType matchType;

    public RequestInfo(String pattern, HttpMethod method, RequestAuthorityAttribute.MatchType matchType) {
        super();
        this.pattern = pattern;
        this.method = method;
        this.matchType = matchType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((matchType == null) ? 0 : matchType.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RequestInfo other = (RequestInfo) obj;
        if (matchType != other.matchType)
            return false;
        if (method != other.method)
            return false;
        if (pattern == null) {
            if (other.pattern != null)
                return false;
        } else if (!pattern.equals(other.pattern))
            return false;
        return true;
    }

}
