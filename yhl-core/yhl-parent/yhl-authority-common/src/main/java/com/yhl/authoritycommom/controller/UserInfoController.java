package com.yhl.authoritycommom.controller;

import com.yhl.authoritycommom.entity.UserInfo;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userInfos")
public class UserInfoController extends BaseController<UserInfo,String> {
}
