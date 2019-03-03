package com.yhl.authoritycommom.controller;

import com.yhl.authoritycommom.entity.RoleInfo;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roleInfos")
public class RoleInfoController extends BaseController<RoleInfo,String> {
}
