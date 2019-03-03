package com.yhl.authoritycommom.controller;

import com.yhl.authoritycommom.entity.Resource;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resources")
public class ResourceController extends BaseController<Resource,String> {
}
