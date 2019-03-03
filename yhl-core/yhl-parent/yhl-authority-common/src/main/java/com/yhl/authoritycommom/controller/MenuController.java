package com.yhl.authoritycommom.controller;

import com.yhl.authoritycommom.entity.Menu;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("menus")
public class MenuController extends BaseController<Menu,String> {
}
