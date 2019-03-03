package com.yhl.authoritycommom.controller;

import com.yhl.authoritycommom.entity.ResourceScope;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resourceScopes")
public class ResourceScopeController extends BaseController<ResourceScope,String> {
}
