package com.yhl.oauth2.provider.endPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RevokeTokenEndpoint  {

    @Autowired
    private ConsumerTokenServices tokenService;


}
