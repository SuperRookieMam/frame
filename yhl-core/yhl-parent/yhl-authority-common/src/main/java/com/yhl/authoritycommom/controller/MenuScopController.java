package com.yhl.authoritycommom.controller;

import com.yhl.authoritycommom.entity.MenuScop;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("menuScopes")
public class MenuScopController extends BaseController<MenuScop,String> {
}
