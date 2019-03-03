package com.yhl.authoritycommom.controller;

import com.yhl.authoritycommom.entity.Organization;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organizations")
public class OrganizationController extends BaseController<Organization,String> {
}
