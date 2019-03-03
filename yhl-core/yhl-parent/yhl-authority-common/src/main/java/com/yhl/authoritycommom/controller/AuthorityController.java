package com.yhl.authoritycommom.controller;

import com.yhl.authoritycommom.entity.Authority;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authoritys")
public class AuthorityController extends BaseController<Authority,String> {
}
